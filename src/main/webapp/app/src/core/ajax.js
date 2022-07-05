// Spring POST /login accepts formData by default.
// POST /logout does not take any parameters.
import _ from 'lodash'

export const isJson = (uri) => !uri.endsWith('login') && !uri.endsWith('logout')

// Handles application/hal+json as well
export const isContentTypeJson = (contentType) => /application\/([+\w]*)json/.exec(contentType) !== null

// Exposes cookies including XSRF_TOKEN
export const cookies = () => {
  return document.cookie
    ?.match(/(^|(?<=, ))[^=;,]+=[^;]+/g)
    ?.map(cookie => cookie.split('=').map(v => v.trim()))
    ?.filter(([k, v]) => k.length && v.length)
    ?.reduce((builder, [k, v]) => {
      builder[k] = v
      return builder
    }, {}) ?? {}
}

export const parseOrError = (str) => _.attempt(JSON.parse.bind(null, str))

const ajax = (method, data) => async (uri) => {
  const { 'XSRF-TOKEN': csrfToken, ...rest } = cookies()

  const resp = await fetch(uri, {
    body: isJson(uri) ? JSON.stringify(data) : data,
    credentials: 'include', // Safe, since only localhost:3000 is allowed
    headers: {
      'X-XSRF-TOKEN': csrfToken,
      ...rest,
      ...(isJson(uri) ? { 'Content-Type': 'application/json' } : undefined)
    },
    method,
    redirect: 'follow'
  })

  if (resp.ok) {
    if (!isJson(resp.url)) {
      throw new Error('Please sign in.')
    }

    const contentType = resp.headers.get('Content-Type')
    if (isContentTypeJson(contentType)) {
      return resp.json()
    } else {
      throw new Error('Response is not in JSON')
    }
  } else {
    // Throws if `resp` is not error response
    throw new Error(resp.statusText, { cause: await resp.json() })
  }
}

export default ajax
