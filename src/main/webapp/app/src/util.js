const ajax = (method) => (uri) => {
    const headers = {'Content-Type': 'application/json'};
    return fetch(uri, { method, headers })
        .then(resp => resp.json());
}
export default ajax;