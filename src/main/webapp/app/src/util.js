const ajax = (method) => (uri) => {
    return fetch(uri, {
        method,
        headers: {'Content-Type': 'application/json'},
        redirect: 'error' }
    ).then(resp => resp.json());
}
export default ajax;