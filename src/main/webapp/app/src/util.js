export const ajax = (method, data = {}, isFormData = false) => async (uri) => {
    const resp = await fetch(uri, {
        body: isFormData ? data : JSON.stringify(data),
        headers: {
            "X-XSRF-TOKEN": csrfToken(),
            ...(isFormData ? undefined : { "Content-Type": "application/json" })
        },
        method,
        redirect: "follow",
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
    return ajax("POST", formData, true)("/login");
};

const csrfToken = () => document.cookie.replace(/(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$|^.*$/, '$1');
