import axios from '@/config/httpConfig'

export function fetchPermission() {
    return axios.get('/json/permission.json')
}

export function login() {
    // return axios.get('/json/login.json');
    return axios.post('/api/auth/login?username=admin1&password=admin1');
}
