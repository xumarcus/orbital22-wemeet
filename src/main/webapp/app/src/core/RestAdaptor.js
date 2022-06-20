import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies } from './util'
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
const urlWithQuery = (url, query) => {
  // console.log(query);
  return url;
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
  const { value } = JSON.parse(option.data)
  createRequest('POST', url, option, (xhr, request) => {
    if (xhr.status === StatusCodes.CREATED) {
      const data = JSON.parse(xhr.responseText)
      option.onSuccess(data, request)
    } else {
      option.onFailure(request)
    }
  }, value)
}

// For now, unbatch the update
const batchUpdate = (url, option) => {
  const { changed, added, deleted } = JSON.parse(option.data)
  if (added !== null) {
    const [{ Subject, Id, StartTime, EndTime, IsAllDay }] = added;
    // FIXME refactor
      const data = {
        startDateTime: StartTime,
        endDateTime: EndTime,
        capacity: 1, // TODO
        rosterPlan: '/api/rosterPlan/1',
      }
      createRequest('POST', url, option, (xhr, request) => {
        if (xhr.status === StatusCodes.CREATED) {
          const data = JSON.parse(xhr.responseText)
          option.onSuccess(data, request)
        } else {
          option.onFailure(request)
          throw new Error('Batch add fail');
        }
      }, data)
  }
}

class RestAdaptor extends CustomDataAdaptor {
  constructor ({ getUrl, map, setUrl }) {
    super({
      getData: (option) => {
        console.log(getUrl, map, option)
        getData(getUrl, map, option)
      },
      addRecord: (setUrl, option) => {
        console.log(option)
        addRecord(setUrl, option)
      },

      // Schedule uses batchUpdate
      batchUpdate: (option) => {
        console.log(option)
        batchUpdate(setUrl, option)
      }
    })
  }
}

export default RestAdaptor
