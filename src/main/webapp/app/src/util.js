const ajax = (method, data /* optional parameter */) => (uri) => {
    return fetch(uri, {
        method,
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'},
        redirect: 'error' }
    ).then(resp => resp.json());
}
export default ajax;