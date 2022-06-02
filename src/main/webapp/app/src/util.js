export const ajax = (method, data /* optional parameter */) => async (uri) => {
    const resp = await fetch(uri, {
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": csrfToken()
        },
        method,
        redirect: "error",
    });
    if (resp.ok) {
        return resp.json();
    } else {
        throw new Error(resp.statusText, { cause: await resp.json() });
    }
};

// In development, resp.type === 'cors'
// In production, resp.type is per normal
export const login = async (formData) => {
    console.assert(formData !== undefined);

    // Success: 302 -> GET / -> 200
    // Failure: 401
    // Redirect path could be useful in the future
    const resp = await fetch("/login", {
        body: formData,
        headers: {
            "X-XSRF-TOKEN": csrfToken()
        },
        method: "POST",
        redirect: "follow",
    });

    // GET / -> html is ignored for now
    if (!resp.ok) {
        throw new Error(resp.statusText);
    }
};

const csrfToken = () => document.cookie.replace(/(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$|^.*$/, '$1');
