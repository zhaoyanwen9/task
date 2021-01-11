import Vue from 'vue'
import Vuex from 'vuex'
import user from '../components/'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: localStorage.getItem('token') ? true : false
  },
  mutations: {
    setLoginState(state, bool) {
      state.isLogin = bool;
      // true 表示登录，false 表示注销
  }
  },
  actions: {
    login({commit}, user) {
      // 发起登录请求，请求都拆分出去 service 文件夹中
      // 使用 service 的请求方法
      debugger;
      user.login(user).then(res => {
          // 从 res.data 中解构数据，code和token
          const { code, token } = res.data;
          if (code) {
              // 登录成功，修改Vuex中间的登录状态
              commit('setLoginState', true);
              // 缓存令牌 token
              localStorage.setItem("token", token);
          }
          return code; // 返回code 给下一个 .then 使用
      });
    },
    logout({ commit }) {
      // 清除缓存
      localStorage.removeItem('token');
      // 重置状态
      commit('setLoginState', false);
   }
  },
  modules: {
  }
})
