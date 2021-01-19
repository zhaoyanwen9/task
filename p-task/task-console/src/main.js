import Vue from 'vue'
import App from '@/App'
import store from '@/store/index'
import router from '@/router/index'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/index.scss'

import "./css/base.css";

import axios from './config/httpConfig'
import * as globalFilter from './filters/filters'

Vue.prototype.$http = axios

// 全局配置cookie，组件调用方式：this.$cookies
import cookies from 'vue-cookies'
Vue.prototype.$cookies = cookies; 
// 配置cookies生命周期，单位不区分大小写
// this.$cookies.config('1d') //填的值1d为一天,1h为一小时,1min为一分钟,1s为1秒：如下表


Object.keys(globalFilter).forEach(key => {
    Vue.filter(key, globalFilter[key])
})

Vue.use(ElementUI)

Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    console.log('token: ' + token);
    if (!store.state.UserToken) {
        if (to.matched.length > 0 && !to.matched.some(record => record.meta.requiresAuth)) {
            next()
        } else {
            next({ path: '/login' })
        }
    } else {
        if (!store.state.permission.permissionList) {
            store.dispatch('permission/FETCH_PERMISSION').then(() => {
                next({ path: to.path })
            })
        } else {
            if (to.path !== '/login') {
                next()
            } else {
                next(from.fullPath)
            }
        }
    }
})

router.afterEach((to, from, next) => {
    var routerList = to.matched
    store.commit('setCrumbList', routerList)
    store.commit('permission/SET_CURRENT_MENU', to.name)
})

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: { App },
    template: '<App/>'
})