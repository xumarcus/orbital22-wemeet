import { useEffect } from 'react';

export function ajax(method, uri) {
    return fetch(uri, { method, headers: {'Content-Type': 'application/json'} });
}

export function useAjax(method, uri, setState, callback) {
    useEffect(() => {
        const fetchData = async () => {
            const response = await ajax(method, uri);
            if (response.ok) {
                const json = await response.json();
                setState(json);
            } else {
                callback();
            }
        }
        fetchData().then();
    }, []);
}