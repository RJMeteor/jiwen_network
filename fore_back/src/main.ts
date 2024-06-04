import { createApp } from 'vue'
import App from '@/App.vue'
import "@/assets/index.css"
import 'animate.css/animate.min.css' //引入动画


// 引入组件库的少量全局样式变量
import 'tdesign-vue-next/es/style/index.css';
import {router} from "@/config/router";
import pinia from "@/config/pinia"

const app = createApp(App);
app.config.warnHandler = () => null;
app.use(pinia)
app.use(router)
app.mount('#app')
