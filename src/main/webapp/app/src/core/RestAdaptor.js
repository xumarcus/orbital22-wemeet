import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies } from './util'
import { StatusCodes } from 'http-status-codes'

const createRequest = (method, url, option, handleRequest) => {
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
    xhr.send(JSON.stringify(value))
  } else {
    xhr.send()
  }
}

const urlWithQuery = (url, query) => {
  const filters = query?.where;
  if (filters !== null) {
    for (const filter of filters) {
      if (filter.field === 'id' && filter.operator === 'equal') {
        return `${url}/${filter.value}`;
      }
    }
  } else {
    return url;
  }
}

const getData = (url, map, option) => {
  const query = JSON.parse(option.data)

  createRequest('GET', urlWithQuery(url, query), option, (xhr, request) => {
    if (xhr.status === StatusCodes.OK) {
      const resp = JSON.parse(xhr.responseText)
      const list = map(resp)
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

const addRecord = (url, option) => {
  createRequest('POST', url, option, (xhr, request) => {
    if (xhr.status === StatusCodes.CREATED) {
      const data = JSON.parse(xhr.responseText)
      option.onSuccess(data, request)
    } else {
      option.onFailure(request)
    }
  })
}

class RestAdaptor extends CustomDataAdaptor {
  constructor ({ getUrl, map, setUrl }) {
    super({
      getData: (option) => {
        getData(getUrl, map, option)
      },
      addRecord: (option) => {
        addRecord(setUrl, option)
      }
    })
  }
}

export default RestAdaptor
