import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies } from './ajax'
import { StatusCodes } from 'http-status-codes'

const createRequest = (method, url, option, handleRequest, data) => {
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
    xhr.send(JSON.stringify(data))
  } else {
    xhr.send()
  }
}

// TODO
const join = (url, query) => {
  console.log(query)
  return url
}

const getData = (url, map, option) => {
  const query = JSON.parse(option.data)

  createRequest('GET', join(url, query), option, (xhr, request) => {
    if (xhr.status === StatusCodes.OK) {
      const resp = JSON.parse(xhr.responseText)
      option.onSuccess(map(resp), request)
    } else {
      option.onFailure(request)
    }
  })
}

const addRecordInternal = (crudUrl, option, data) => {
  createRequest('POST', crudUrl, option, (xhr, request) => {
    if (xhr.status === StatusCodes.CREATED) {
      option.onSuccess(JSON.parse(xhr.responseText), request)
    } else {
      option.onFailure(request)
    }
  }, data)
}

const addRecord = (crudUrl, crudMap, option) => {
  const { value: data } = JSON.parse(option.data)
  addRecordInternal(crudUrl, option, crudMap(data))
}

const batchUpdate = (crudUrl, crudMap, option) => {
  const { changed, added, deleted } = JSON.parse(option.data)
  if (added !== null) {
    // Tutorial says `added` is singleton
    const [data] = added
    addRecordInternal(crudUrl, option, crudMap(data))
  }
}

class RestAdaptor extends CustomDataAdaptor {
  constructor ({ url, map, crudUrl, crudMap }) {
    super({
      getData: (option) => getData(url, map, option),
      addRecord: (option) => addRecord(crudUrl, crudMap, option),
      batchUpdate: (option) => batchUpdate(crudUrl, crudMap, option)
    })
  }

  static extendCounts (result) {
    return {
      result,
      count: result.length
    }
  }
}

export default RestAdaptor
