const Qs = require('qs');

export default function request(url, options) {
    const defaultOptions = {
        credentials: 'include',
        mode: 'cors',
        redirect: 'follow',
    };
    const newOptions = { ...defaultOptions, ...options };
    if (
        newOptions.method === 'POST' ||
        newOptions.method === 'PUT' ||
        newOptions.method === 'DELETE'
    ) {
        if (!(newOptions.body instanceof FormData)) {
            newOptions.headers = {
                Accept: 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
                ...newOptions.headers,
            };
            newOptions.body = Qs.stringify(newOptions.body);
        } else {
            // newOptions.body is FormData
            newOptions.headers = {
                Accept: 'application/json',
                ...newOptions.headers,
            };
        }
    }

    return fetch(url, newOptions)
        .then(response => {
            return response.json();
        })
        .catch(e => {
            throw e
        });
}