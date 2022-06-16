// In development, resp.type === 'cors'
// In production, resp.type is per normal
// TODO handle fullyAuthenticated

const ajax = (method, data) => async (uri) => {
    // Spring POST /login accepts formData by default.
    // POST /logout does not take any parameters.
    const isFormData = uri.endsWith("login");
    const resp = await fetch(uri, {
        body: isFormData ? data : JSON.stringify(data),
        headers: {
            "X-XSRF-TOKEN": csrfToken(),
            ...(isFormData ? undefined : {"Content-Type": "application/json"})
        },
        method,
        redirect: "follow",
    });
    if (resp.ok) {
        const location = resp.headers.get('Location');
        if (location != null) {
            // Fetch created resource
            return ajax('GET')(location);
        }
        return resp.json();
    } else {
        throw new Error(resp.statusText, { cause: await resp.json() });
    }
};

const csrfToken = () => document.cookie.replace(/(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$|^.*$/, '$1');

export default ajax;