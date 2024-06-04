import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router"
import {useUserStore} from "@/config/store/user";

const routes: RouteRecordRaw[] = [
    {
        path: "/",
        name: "index",
        redirect: {
            name: "normal"
        },
        component: () => import("@/components/Index.vue"),
        children: [
            {
                path: "annunciation",
                name: "annunciation",
                component: () => import("@/components/BlogAnnunciation.vue")
            },
            {
                path: "article",
                name: "article",
                component: () => import("@/components/BlogArticle.vue")
            },
            {
                path: "feedback",
                name: "feedback",
                component: () => import("@/components/BlogFeedback.vue"),
            },
            {
                path: "normal",
                name: "normal",
                component: () => import("@/components/BlogNormalUser.vue"),
            },
            {
                path: "tag",
                name: "tag",
                component: () => import("@/components/BlogTag.vue"),
            },
        ]
    },
    {
        path: "/login",
        name: "login",
        component: () => import("@/components/Login.vue"),
    },
]


const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const userStore = useUserStore()
    userStore.$patch({
        activeMenu:to.name as string
    })
    next()
})


export {
    router
}