import axios, {AxiosHeaders, AxiosRequestConfig} from 'axios';
import { format } from 'timeago.js';

import {router} from "./router"
import Progress from 'nprogress'
import {useUserStore} from "@/config/store/user";
import {useNotification} from "naive-ui";
import {useNotificationsSettingsStore} from "@/config/store/notificationsSettings";

const baseURL: string = import.meta.env.BLOG_BASE_URL
const headerToken: string = import.meta.env.BLOG_TOKEN_NAME

Progress.configure({
    easing: 'speed',  // 动画方式
    speed: 500,  // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
});


const axiosInstance = axios.create({
    baseURL,
    method: "POST",
    timeout: 15000,
    headers: {
        "Content-Type": "application/json",
    }
});

axiosInstance.interceptors.request.use(
    (config: any) => {
        const userStore = useUserStore()
        Progress.start()
        return {
            ...config,
            headers: {
                ...config.headers,
                Authorization: userStore.$state.token
            },
        };
    },
    (error) => {
        Progress.done()
    },
);


axiosInstance.interceptors.response.use(
    function (response: any) {
        Progress.done()
        const userStore = useUserStore()
        const userNoft = useNotificationsSettingsStore()
        const responseData: ResponseType<any> = response.data
        if (responseData?.message) {
            if (responseData.code > 0) {
                if (responseData.code == 500) {
                    for (let dataKey in response.data) {
                        window.$message.error(responseData.message)
                    }
                } else if (responseData.code == 4004) {
                    window.$message.error(responseData.message)
                    router.back()
                } else if (responseData.code == 2001) {
                    userStore.$reset()
                    userNoft.$reset()
                    router.push({
                        name: "homePage"
                    })
                } else {
                    window.$message.error(responseData.message)
                }
                return Promise.resolve(null);
            } else window.$message.success(responseData.message)
        }
        // let data = responseData?.data
        // if(data instanceof  Array){
        //     data.forEach(ele=>{
        //         ele?.updateTime && (ele.updateTime = format(ele.updateTime,'zh_CN'))// ele.updta\\
        //         ele?.createTime && (ele.createTime = format(ele.createTime,'zh_CN'))// ele.updta\\
        //     })
        // }else if(data?.list instanceof  Array){
        //    data.list.forEach(ele=>{
        //        ele?.updateTime && (ele.updateTime = format(ele.updateTime,'zh_CN'))// ele.updta\\
        //        ele?.createTime && (ele.createTime = format(ele.createTime,'zh_CN'))// ele.updta\\
        //    })
        // }else if(data instanceof Object){
        //     data?.updateTime && (data.updateTime = format(data.updateTime,'zh_CN'))// ele.updta\\
        //     data?.createTime && (data.createTime = format(data.createTime,'zh_CN'))// ele.updta\\
        // }
        return Promise.resolve(responseData?.data);
    },
    function (error) {
        Progress.done()
        window.$message.error("请求失败")
        return Promise.reject(error);
    },
);

const includePaths = [
    "/blogUser/getUserById",
    "/blogArticleLables/getLables",
    "/blogArticle/articlesByNameAndLabel",
    "/blogArticle/getArticlesByUserIdAndArticlesId",
    "/blogComment/getCommentByArticleId",
    "/blogFavorite/getFavoriteArticleByUserId",
    "/blogLikeBrowse/addLikeByArticleIdAndUserIdAndpersonId/1",
    "/blog/login",
    "/blog/registry",
    "/blog/registryCode",
    "/blog/reset",
    "/blog/resetCode",
    "/blogLikeBrowse/getOhtArticle",
    "/blogLikeBrowse/getOhtTopic",
    "/blogAttention/getOhtUser",
    "/blogUserNotiSet/notisetById",
    "/blogArticle/articleByUserIdAndName"
]

const axiosRequset = async <T>(config: AxiosRequestConfig): Promise<any> => {
    const userStore = useUserStore()
    if (!includePaths.some(value => {
        // console.log(config.url,value,(config.url as string).includes(value))
        return (config.url as string).includes(value);
    }) && !userStore.$state.init) {
        window.$message.info("请登录！！")
        console.log(config.url)
        userStore.$patch({
            loginStatus: true
        })
    } else {
        let response = await axiosInstance(config);
        return response
    }
}

interface ResponseType<T> {
    code: number;

    data: T;

    message: string;

    description: string;

}

export {axiosRequset}
