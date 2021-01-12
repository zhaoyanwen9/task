<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>
export default {
    name: 'App'
}
</script>

<style lang="scss">
    #app{
        height:100%;
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: left;
        color: #2c3e50;
        >div{
            height: 100%;
        }
    }

    /**
    滚动条
     */
    ::-webkit-scrollbar {
        width: 3px;
        height: 4px;
    }

    ::-webkit-scrollbar-thumb { //滑块部分
        border-radius: 4px;
        background-color: rgba(0,0,0,0.2);
    }

    ::-webkit-scrollbar-track { //轨道部分
        box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
        background: #ededed;
        border-radius: 4px;
    }
</style>
