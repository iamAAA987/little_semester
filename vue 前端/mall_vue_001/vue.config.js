const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0', // 允许局域网访问
    port: 8080,      // 端口号（默认是8080，可省略）
    hot: true,       // 启用热更新（可选）
  }
})
