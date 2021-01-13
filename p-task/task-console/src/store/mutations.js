export default {
    LOGIN_IN(state, token) {
        debugger;
        console.log("===========" + token);
        state.UserToken = token
    },
    LOGIN_OUT(state) {
        debugger;
        console.log("===========" + state.UserToken);
        state.UserToken = ''
    },
    toggleNavCollapse(state) {
        state.isSidebarNavCollapse = !state.isSidebarNavCollapse
    },
    setCrumbList(state, list) {
        state.crumbList = list
    }
}
