module.exports = {
    runtimeCompiler: true,
    // axios域代理，解决axios跨域问题
    publicPath: '/task',
    lintOnSave: false,
    devServer: {
        // host: "localhost",
        port: 4060,
        // https: false,
        // open: false,
        proxy: {
            '/task/gateway/zuul-task': {
                // target: 'http://localhost:5050',
                target: 'http://localhost:7070',
                changeOrigin: true, //开启代理
                ws: true, // 是否启用websockets
                pathRewrite: {
                    "^/task": "" // '/api'代替target
                }
            },
            '/api': {
                target: 'http://localhost:4050',
                changeOrigin: true, //开启代理
                ws: true, // 是否启用websockets
                pathRewrite: {
                    "^/api": "" // '/api'代替target
                }
            },
            '/proxy-permission/api/': {
                target: 'http://localhost:xxxx/',
                changeOrigin: true
            },
            '/proxy-dashboard/adm/': {
                target: 'http://localhost:xxxx/',
                changeOrigin: true
            },
            '/httpsProxy-monitor/api/': {
                target: 'https://100.95.153.4',
                secure: false, // https协议才设置
                changeOrigin: true
            }
        }
    }
}
