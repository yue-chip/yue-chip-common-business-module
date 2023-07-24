import App from './App.vue'
// @ts-ignore
import { createApp } from 'vue'
import { router, setupRouter } from './router'
import {message,Card,Modal,Select,Tree,Form,Row,Col,FormItem,Input,Space,Button,SelectOption,Table} from "ant-design-vue";
async function bootstrap() {
  const app = createApp(App)
  setupRouter(app)
  app.use(Card)
    .use(Modal)
    .use(Select)
    .use(Tree)
    .use(Form)
    .use(Col)
    .use(Row)
    .use(FormItem)
    .use(Input)
    .use(Space)
    .use(Button)
    .use(SelectOption)
    .use(Table)
    .mount('#app');
  app.config.globalProperties.$message = message;
}

bootstrap()
