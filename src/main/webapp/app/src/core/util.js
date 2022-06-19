// TODO handle fullyAuthenticated
const ajax = (method, data) => async (uri) => {
  // Spring POST /login accepts formData by default.
  // POST /logout does not take any parameters.
  const isFormData = uri.endsWith('login') || uri.endsWith('logout')

  const resp = await fetch(uri, {
    body: isFormData ? data : JSON.stringify(data),
    credentials: 'include', // Safe, since only localhost:3000 is allowed
    headers: {
      ...cookies(),
      ...(isFormData ? undefined : { 'Content-Type': 'application/json' }),
    },
    method,
    redirect: 'follow',
  })

  if (resp.ok) {
    const location = resp.headers.get('Location')
    if (location != null) {
      return ajax('GET')(location)
    }

    const contentType = resp.headers.get('Content-Type')
    if (contentType.endsWith('json')) {
      return resp.json()
    } else {
      return resp.text()
    }
  } else {
    // Throws if `resp` is not error response
    throw new Error(resp.statusText, { cause: await resp.json() })
  }
}

const cookies = () => {
  return document.cookie.match(/(^|(?<=, ))[^=;,]+=[^;]+/g).
    map(cookie => cookie.split('=').map(v => v.trim())).
    filter(([k, v]) => k.length && v.length).
    reduce((builder, [k, v]) => {
      builder[k.replace('XSRF-TOKEN', 'X-XSRF-TOKEN')] = v
      return builder
    }, {})
}

export default ajax
