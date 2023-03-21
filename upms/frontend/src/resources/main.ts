import App from './App.vue'
// @ts-ignore
import { createApp } from 'vue'
import { router, setupRouter } from './router'
// import 'ant-design-vue/dist/antd.css'
// import ant from 'ant-design-vue'
async function bootstrap() {
  const app = createApp(App)
  setupRouter(app)
  app.mount('#app')
}

bootstrap()
