import App from './App.vue'
// @ts-ignore
import { createApp } from 'vue'
import { router, setupRouter } from './router'
import {
  message,
  // Card,
  // Modal,
  // Select,
  // Form,
  // Col,
  // FormItem,
  // Input,
  // Space,
  // Button,
  // InputPassword,
  // Table,
  // Row
} from "ant-design-vue";
async function bootstrap() {
  const app = createApp(App)
  setupRouter(app)
  app.mount('#app');
  // app.config.globalProperties.$message = message;
}
bootstrap()
