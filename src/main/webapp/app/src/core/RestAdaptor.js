import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies } from './util'
import { StatusCodes } from 'http-status-codes'

const createRequest = (method, url, option, handleRequest, columnNamesToObjectKeys) => {
  const xhr = new XMLHttpRequest()
  xhr.onreadystatechange = function () {
    if (this.readyState === 4) {
      handleRequest(xhr, { ...option, httpRequest: xhr })
    }
  }
  xhr.open(method, url, true)
  xhr.withCredentials = true
  xhr.setRequestHeader('X-XSRF-TOKEN', cookies()['XSRF-TOKEN'])
  if (method !== 'GET') {
    xhr.setRequestHeader('Content-Type', 'application/json')
    const { value } = JSON.parse(option.data)
    xhr.send(JSON.stringify(columnNamesToObjectKeys(value)))
  } else {
    xhr.send()
  }
}

const getData = ({ url, respToList }, option) => {
  createRequest('GET', url, option, (xhr, request) => {
    if (xhr.status === StatusCodes.OK) {
      const resp = JSON.parse(xhr.responseText)
      const list = respToList(resp)
      const data = {
        result: list,
        count: resp.count ?? list.length
      }
      option.onSuccess(data, request)
    } else {
      option.onFailure(request)
    }
  })
}

const addRecord = ({ url, respItemToObject, columnNamesToObjectKeys }, option) => {
  createRequest('POST', url, option, (xhr, request) => {
    if (xhr.status === StatusCodes.CREATED) {
      const resp = JSON.parse(xhr.responseText)
      const data = respItemToObject(resp)
      option.onSuccess(data, request)
    } else {
      option.onFailure(request)
    }
  }, columnNamesToObjectKeys)
}

class RestAdaptor extends CustomDataAdaptor {
  constructor (get, set) {
    super({
      getData: (option) => {
        getData(get, option)
      },
      addRecord: (option) => {
        addRecord(set, option)
      }
    })
  }
}

export default RestAdaptor
