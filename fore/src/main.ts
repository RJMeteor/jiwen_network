import {createApp} from 'vue'
import App from '@/App.vue'
import {router} from "@/config/router";
// 通用字体
import 'vfonts/Lato.css'
// 等宽字体
import 'vfonts/FiraCode.css'
//重置样式
import "normalize.css"
//动画样式
import "animate.css"
//请求进度条样式
import 'nprogress/nprogress.css'

import "@/assets/global.scss"
import Viewer from 'v-viewer';
import 'viewerjs/dist/viewer.css';
import vueTextOverflow from '@fcli/vue-text-overflow'
import CsdnScrollImpl from "@/directive/CsdnScrollImpl"
import CsdnScrollHeaderImpl from "@/directive/CsdnScrollHeaderImpl"
import CsdnScrollFooterImpl from "@/directive/CsdnScrollFooterImpl"
import pinia from "@/config/pinia"


const app = createApp(App)
// 屏蔽错误信息
// app.config.errorHandler = () => null;
// 屏蔽警告信息
CsdnScrollImpl(app)
CsdnScrollHeaderImpl(app)
CsdnScrollFooterImpl(app)
app.use(vueTextOverflow)
app.config.warnHandler = () => null;
app.use(pinia)
app.use(router)


app.use(Viewer, {
    defaultOptions: {
        inline: false, //默认值：false。启用内联模式。
        button: true, //在查看器的右上角显示按钮。
        navbar: true, //指定导航栏的可见性。
        title: true, //指定标题的可见性和内容。
        toolbar: true, //指定工具栏及其按钮的可见性和布局。
        tooltip: true, //放大或缩小时显示带有图像比率（百分比）的工具提示。
        movable: true, //启用以移动图像。
        zoomable: true, //启用以缩放图像
        rotatable: true, //启用以旋转图像
        scalable: true, //启用以缩放图像。
        transition: true, //为某些特殊元素启用CSS3转换。
        fullscreen: true, //启用以在播放时请求全屏。
        keyboard: true, //启用键盘支持。
        // url: 'src',  //默认值：'src'。定义获取原始图像URL以供查看的位置。
    },
});
app.mount('#app')
