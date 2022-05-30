const ajax = (method, data /* optional parameter */) => async (uri) => {
    const resp = await fetch(uri, {
        method,
        body: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'},
        redirect: 'error'
    });
    if (resp.ok) {
        return resp.json();
    } else {
        throw new Error(resp.statusText, { cause: await resp.json() });
    }
}
export default ajax;