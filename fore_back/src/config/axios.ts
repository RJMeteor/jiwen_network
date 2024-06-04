import axios, {AxiosHeaders, AxiosRequestConfig} from 'axios';

import {router} from "./router"
import Progress from 'nprogress'
import {MessagePlugin} from "tdesign-vue-next";
import {useUserStore} from "@/config/store/user";


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
        // console.log(response)
        const responseData: ResponseType<any> = response.data
        if (responseData?.message) {
            if (responseData.code > 0) {
                MessagePlugin.error(responseData.message)
                if (responseData.code >= 2000) {
                    router.push({
                        name: "login"
                    })
                }
                return Promise.resolve(null);
            } else MessagePlugin.success(responseData.message)
        }
        return Promise.resolve(responseData?.data);
    },
    function (error) {
        Progress.done()
        MessagePlugin.error("请求失败")
        return Promise.reject(error);
    },
);

const axiosRequset = async <T>(config: AxiosRequestConfig): Promise<any> => {
    let response = await axiosInstance(config);
    return response
}

interface ResponseType<T> {
    code: number;

    data: T;

    message: string;

    description: string;

}

export {axiosRequset}
