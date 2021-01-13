import axios from '@/config/httpConfig'

export function fetchPermission() {
    return axios.get('/json/permission.json')
}

export function login() {
    let response = {};
    axios.post('/api/auth/login?username=admin1&password=admin1').then(response => {
        console.log("===="+response);
        this.response = response;
    }).catch(function (error) {
        console.log(error);
    });
    return response
}
