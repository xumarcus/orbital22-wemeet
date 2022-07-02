import { CustomDataAdaptor } from '@syncfusion/ej2-data'
import { cookies, parseOrError } from './ajax'

const handle = (option, promise) => {
  promise.then(({ httpRequest, data }) => option.onSuccess(data,
    { ...option, httpRequest })).
    catch(({ httpRequest }) => option.onFailure({ ...option, httpRequest }))
}

export const makeRequest = (method, url, data) => new Promise(
  (resolve, reject) => {
    const httpRequest = new XMLHttpRequest()
    httpRequest.open(method, url, true)
    httpRequest.onload = () => {
      if (httpRequest.status >= 200 && httpRequest.status <= 299) {
        resolve({
          httpRequest,
          data: parseOrError(httpRequest.responseText),
        })
      } else {
        reject({
          httpRequest,
          data: parseOrError(httpRequest.responseText),
        })
      }
    }
    httpRequest.onerror = () => reject({ httpRequest })

    httpRequest.withCredentials = true
    httpRequest.setRequestHeader('X-XSRF-TOKEN', cookies()['XSRF-TOKEN'])

    if (data) {
      httpRequest.setRequestHeader('Content-Type', 'application/json')
      httpRequest.send(JSON.stringify(data))
    } else {
      httpRequest.send()
    }
  })

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
      },
    })
  }

  // Convenience
  static extendCounts (result) {
    return {
      result,
      count: result.length,
    }
  }

  // TODO expose promise instead of just map

  // TODO Map data to query string
  static get (url, f = (resp) => resp) {
    return (req) => {
      const g = req?.requiresCounts
        ? x => RestAdaptor.extendCounts(f(x))
        : f;

      return makeRequest('GET', url)
        .then(({ httpRequest, data }) => ({ httpRequest, data: g(data) }))
    }
  }

  static post (url, f) {
    return (req) => makeRequest('POST', url, f ? f(req) : req)
  }

  static put (url, f) {
    return (req) => makeRequest('PUT', `${url}/${req.id}`, f ? f(req) : req)
  }

  static delete (url) {
    return ({ key }) => makeRequest('DELETE', `${url}/${key}`)
  }
}

export default RestAdaptor
