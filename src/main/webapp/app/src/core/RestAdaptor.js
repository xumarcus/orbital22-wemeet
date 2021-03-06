import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies, parseOrError } from './ajax'
import { ERROR_MESSAGES } from './const'

const handle = (option, { httpRequest, promise }) => {
  return promise
    .then((data) => option.onSuccess(data, { ...option, httpRequest }))
    .catch(() => option.onFailure({ ...option, httpRequest }))
}

export const makeRequest = (method, url, data) => {
  const httpRequest = new window.XMLHttpRequest()
  const promise = new Promise((resolve, reject) => {
    httpRequest.open(method, url, true)
    httpRequest.onload = () => {
      if (httpRequest.status >= 200 && httpRequest.status <= 299) {
        resolve(parseOrError(httpRequest.responseText))
      } else {
        reject(parseOrError(httpRequest.responseText))
      }
    }
    httpRequest.onerror = (ev) => reject(new Error(ERROR_MESSAGES.DEFAULT_MESSAGE, { cause: ev }))

    httpRequest.withCredentials = true
    httpRequest.setRequestHeader('X-XSRF-TOKEN', cookies()['XSRF-TOKEN'])

    if (data) {
      httpRequest.setRequestHeader('Content-Type', 'application/json')
      try {
        const json = JSON.stringify(data)
        httpRequest.send(json)
      } catch (e) {
        throw new Error('RestAdaptor attempts to send non JSON data')
      }
    } else {
      httpRequest.send()
    }
  })
  return { httpRequest, promise }
}

const of = (option) => JSON.parse(option.data)

class RestAdaptor extends CustomDataAdaptor {
  constructor ({ GET, POST, PUT, DELETE }) {
    super({
      getData: (option) => handle(option, GET(of(option))),
      addRecord: (option) => handle(option, POST(of(option).value)),
      updateRecord: (option) => handle(option, PUT(of(option).value)),
      deleteRecord: (option) => handle(option, DELETE(of(option))),
      batchUpdate: (option) => {
        const { changed, added, deleted } = of(option)

        const [toChange] = changed
        if (toChange) handle(option, PUT(toChange))

        const [toAdd] = added
        if (toAdd) handle(option, POST(toAdd))

        const [toDelete] = deleted
        if (toDelete) handle(option, DELETE(toDelete))
      }
    })
  }

  // Convenience
  static extendCounts (result) {
    return {
      result,
      count: result.length
    }
  }

  // TODO Map data to query string
  static get (url, f = (resp) => resp) {
    return (req) => {
      const g = req?.requiresCounts
        ? x => RestAdaptor.extendCounts(f(x))
        : f

      const { httpRequest, promise } = makeRequest('GET', url)
      return { httpRequest, promise: promise.then(g) }
    }
  }

  static post (url, f) {
    return (req) => makeRequest('POST', url, f ? f(req) : req)
  }

  static put (url, f) {
    return (req) => makeRequest('PUT', `${url}/${req.id}`, f ? f(req) : req)
  }

  static delete (url, f) {
    return (req) => makeRequest('DELETE', `${url}/${f(req)}`)
  }
}

export default RestAdaptor
