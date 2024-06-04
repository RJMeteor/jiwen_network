import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from "vue-router"
import {useUserStore} from "@/config/store/user";
import {menuOptions} from '@/config/data/administration'

const routes: RouteRecordRaw[] = [
    {
        path: "/",
        name: "index",
        redirect: {
            name: "homePage"
        },
        component: () => import("@/components/Index.vue"),
        children: [
            {
                path: "homePage",
                name: "homePage",
                component: () => import("@/components/HomePage.vue")
            },
            {
                path: "administration",
                name: "administration",
                redirect: {
                    name: "accountManagement"
                },
                component: () => import("@/components/Administration.vue"),
                children: [
                    {
                        path: "accountManagement",
                        name: "accountManagement",
                        component: () => import("@/components/management/AccountManagement.vue"),
                    },
                    {
                        path: "notificationsSettings",
                        name: "notificationsSettings",
                        component: () => import("@/components/management/NotificationsSettings.vue"),
                    },
                    {
                        path: "privateLetter",
                        name: "privateLetter",
                        component: () => import("@/components/management/messagePush/PrivateLetter.vue"),
                    },
                    {
                        path: "chat/:userId",
                        name: "chat",
                        component: () => import("@/components/management/messagePush/Chat.vue"),
                    },
                    {
                        path: "comment",
                        name: "comment",
                        component: () => import("@/components/management/messagePush/Comment.vue"),
                    },
                    {
                        path: "followWithInterest",
                        name: "followWithInterest",
                        component: () => import("@/components/management/messagePush/FollowWithInterest.vue"),
                    },
                    {
                        path: "collect",
                        name: "collect",
                        component: () => import("@/components/management/messagePush/Collect.vue"),
                    },
                    {
                        path: "like",
                        name: "like",
                        component: () => import("@/components/management/messagePush/Like.vue"),
                    },
                    {
                        path: "interactive",
                        name: "interactive",
                        component: () => import("@/components/management/Interactive.vue"),
                    },
                    {
                        path: "feedback",
                        name: "feedback",
                        component: () => import("@/components/management/Feedback.vue"),
                    },
                    {
                        path: "backend",
                        name: "backend",
                        component: () => import("@/components/management/BackendNotification.vue"),
                    },
                ]
            },
            {
                path: "commentPush/:userId",
                name: "commentPush",
                component: () => import("@/components/management/messagePush/CommentPush.vue"),
            },
            {
                path: "personalHomepage/:userId",
                name: "personalHomepage",
                redirect: {
                    name: "personalArticles",
                },
                component: () => import("@/components/PersonalHomepage.vue"),
                children: [
                    {
                        path: "personalArticles/:userId",
                        name: "personalArticles",
                        component: () => import("@/components/personal/PersonalArticles.vue"),
                    },
                    {
                        path: "personalFavorites/:userId",
                        name: "personalFavorites",
                        component: () => import("@/components/personal/PersonalFavorites.vue"),
                    },
                    {
                        path: "personalTopics/:userId",
                        name: "personalTopics",
                        component: () => import("@/components/personal/PersonalTopics.vue"),
                    },
                ]
            },
            {
                path: "topic",
                name: "topic",
                component: () => import("@/components/Topic.vue")
            },
            {
                path: "reader/:userId/:articleId",
                name: "reader",
                component: () => import("@/components/create/Reader.vue")
            },
        ]
    },
    {
        //type: 修改类型，other为新建文章，indexDB修改indexDB的数据，request修改从请求来的数据
        //id:修改内容的id
        path: "/create/:type/:id",
        name: "create",
        component: () => import("@/components/create/Create.vue")
    },
    {
        //type: 修改类型，other为新建文章，indexDB修改indexDB的数据，request修改从请求来的数据
        //contentid: indexDB数据的id
        path: "/submit/:type/:contentId/:articleIdto",
        name: "submit",
        component: () => import("@/components/create/Submit.vue")
    },

]


const router = createRouter({
    history: createWebHashHistory(),
    routes
})
const includePaths = [
    "homePage",
    "personalHomepage",
    "reader",
    "topic",
    "personalArticles",
    "personalFavorites",
    "personalTopics",
]
router.beforeResolve((to, from, next) => {
    const userStore = useUserStore()
    const isPublic = includePaths.includes(to.name as string)
    console.log(to)
    if ((to.path as string).includes("administration")) {
        userStore.$patch({
            mobileMenu: menuOptions as any
        })
    } else if ((to.path as string).includes("personalHomepage")) {

    } else {
        userStore.$patch({
            mobileMenu: [] as any
        })
    }
    if (isPublic || userStore.$state.user.id) {
        userStore.updateRouteName(to.name == "chat" ? "privateLetter" : to.name as string)
        next()
    } else {
        if (!isPublic) {
            router.push({
                name: "homePage"
            })
        }
        if (!userStore.getInit) {
            window.$message.info("请登录！！")
            userStore.$patch({
                loginStatus: true
            })
        }
    }
})


export {
    router
}