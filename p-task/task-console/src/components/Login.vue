<template>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="login-box">
        <h3 class="login-title">欢迎登录</h3>
        <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
// Vuex 是一个专为 Vue.js应用程序开发的状态管理模式。它采用集中式存储管理应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化
    import Vue from 'vue';
    import Vuex from 'vuex';
    Vue.use(Vuex);
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
    }
});
    //export default {
    //    name: "Login",
    //    data() {
    //        return {
    //            ruleForm: {
    //                username: '',
    //                password: ''
    //            },
    //            rules: {
    //                password: [
    //                    {required: true, trigger: 'blur'}
    //                ],
    //                username: [
    //                    {required: true, trigger: 'blur'}
    //                ]
    //            }
    //        };
    //    },
    //    methods: {
    //        submitForm(formName) {
    //            this.$refs[formName].validate((valid) => {
    //                if (valid) {
    //                    const loading = this.$loading({
    //                        lock: true,
    //                        text: '登录中',
    //                        spinner: 'el-icon-loading',
    //                        background: 'rgba(0, 0, 0, 0.7)'
    //                    });
    //                    this.axios.post('/api/auth/login?username=admin1&password=admin1').then(response => {
    //                        loading.close();
    //                        if (response.status == 200) {
    //                            // 登录成功后，重定向，如果没有则重定向到首页
    //                            const path = this.$route.query.redirect || '/home';
    //                            this.$router.push(path);
    //                            this.$notify({
    //                                title: '成功',
    //                                message: '登录成功',
    //                                type: 'success'
    //                            });
    //                        }
    //                    }).catch(function (error) {
    //                        console.log(error);
    //                    });
    //                } else {
    //                    console.log('error submit!!');
    //                    return false;
    //                }
    //            });
    //        },
    //        resetForm(formName) {
    //            this.$refs[formName].resetFields();
    //        }
    //    }
    //}
</script>
<style scoped>

</style>
<style lang="scss" scoped>
    .login-box {
        border: 1px solid #DCDFE6;
        width: 400px;
        margin: 100px auto;
        padding: 35px 35px 15px 35px;
        border-radius: 5px;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        box-shadow: 0 0 25px #909399;
    }

    .login-title {
        text-align: center;
        margin: 0 auto 40px auto;
        color: #303133;
    }
</style>
