const { createProxyMiddleware } = require('http-proxy-middleware')

module.exports = function (app) {
  app.use(
    'login',
    createProxyMiddleware({
      target: 'http://localhost:5000/login',
      changeOrigin: true
    })
  )
  app.use(
    'api',
    createProxyMiddleware({
      target: 'http://localhost:5000/api',
      changeOrigin: true
    })
  )
}
