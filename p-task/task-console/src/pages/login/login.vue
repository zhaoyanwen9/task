<template>
    <div class="clearfix" id="login_wrap">
        <h2 class="title">欢迎登陆</h2>
        <div id="login">
            <div class="login--account">
                <span>账号：</span>
                <input type="text"  placeholder="请输入账户" name="account" v-model.trim="account"/>
            </div>
            <div class="login--password">
                <span>密码：</span>
                <input type="password"  placeholder="请输入密码" name="password" v-model.trim="password" @keyup.enter="login"/>
            </div>
            <p class="login--btn">
                <button id="loginBtn" @click="login">登录</button>
            </p>
        </div>
    </div>
</template>

<script>
import { login } from '@/api/permission'
export default {
    data() {
        return {
            account: '',
            password: ''
        }
    },
    methods: {
        async login() {
            try {
                let data = await login();
                console.log(data);
                let token = data.token
                this.$store.commit('LOGIN_IN', token)
                this.$cookies.set("JSESSIONID",token)
                // 实现指定url跳转，这个方法不会向history栈添加记录，使用后退this.$router.back();会返回到上上一个页面。因为它的上个页面是不存在的。history栈没有记录
                this.$router.replace('/'); // 
                // 实现指定url跳转，这个方法会向history栈添加一个记录，使用后退this.$router.back();会返回到上一个页面
                // this.$router.push("/");
            } catch (e) {
                console.log(e)
            }
        }
    }
}
</script>

<style scoped lang="scss">
.title {
    text-align: center;
    font-size: 22px;
    padding-top: 100px;
}
#login_wrap {
    position: relative;
    background: rgba(64, 64, 194, 0.1);
    > div {
        background: #fff;
        width: 479px;
        height: 325px;
        padding: 30px 40px;
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
        > div {
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
            &.login--account {
                margin: 25px 0 30px;
            }
            span {
                color: #666;
                display: inline-block;
                width: 84px;
                font-size: 20px;
            }
            input {
                background: none;
                font-size: 16px;
                border: none;
                height: 30px;
                width: 280px;
                padding-left: 12px;
                box-sizing: border-box;
                color: #666;
                &.error {
                    border: 1px solid #f00;
                }
                &::-webkit-input-placeholder {
                    color: #aaa;
                }
            }
        }

        p {
            text-align: right;
            &.login--btn {
                button {
                    width: 100%;
                    height: 50px;
                    font-size: 18px;
                    background: #0f6171;
                    border: none;
                    margin-top: 30px;
                    color: #fff;
                    border-radius: 6px;
                    cursor: pointer;
                }
            }
            a {
                color: #fff;
                display: inline-block;
                padding: 0 15px;
                font-size: 14px;
            }
        }
    }
}
</style>
