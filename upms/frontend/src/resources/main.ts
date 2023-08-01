import App from './App.vue'
// @ts-ignore
import { createApp } from 'vue'
import { router, setupRouter } from './router'
async function bootstrap() {
  const app = createApp(App)
  setupRouter(app)
  app.mount('#app');
}

bootstrap()
