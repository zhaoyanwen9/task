import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../components/Login.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router

// https://www.cnblogs.com/yuxi2018/p/11967311.html
// 路由守卫
router.beforeEach((to, from, next) => {
  debugger;
  console.log("路由守卫 ->" + to.meta.auth)
  // 判断要进入的路由是否需要认证
  if(to.meta.auth) {
      // 通过token令牌机制判断是否已经登录
      const token = localStorage.getItem('token');
      if (token) {
          next(); // 如果登录则放行，进入路由
      } else {
        // 跳转，并携带重定向地址
          next({
             path: '/login', 
             query: {
                 redirect: to.path 
             }
          });
      }
  } else {
      // 不需要验证的模块，直接放行
      next();
  }
});
