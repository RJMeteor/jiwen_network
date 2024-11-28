<script setup lang="ts">
    import {getColor} from "@/util/currency"
    import Loading from "@/d/Loading.vue"
    import {computed, ref, onMounted, nextTick} from "vue"
    import {ChatbubbleEllipsesSharp, Heart, Reload, Search} from "@vicons/ionicons5"
    import {EyeRegular} from "@vicons/fa"
    import {
        addAttentionRequest,
        articleByUserIdAndNameRequest,
        articlesByNameAndLabelAndUserIdRequest,
        articlesByNameAndLabelRequest, deleteAttentionRequest,
        getLablesRequest,
        getOhtArticleRequest, getOhtUserRequest
    } from "@/config/request";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {useUserStore} from "@/config/store/user";
    import {useRouter} from "vue-router";
    import {menuOptions} from '@/config/data/personalHomepage'

    const userStore = useUserStore()
    const router = useRouter()

    const articles = ref<BlogArticle[]>([])

    const emptyField = ref<boolean>(false)
    const pageInfo = ref<any>({
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: true
    })
    userStore.$state.searchAction = searchAction

    //请求搜索
    function searchAction() {
        articles.value = []
        pageInfo.value = {
            total: 0,
            pageNum: 1,
            pageSize: 10,
            loading: true
        }
    }


    /**
     *
     * 请求分页数据
     * @param
     */
    function pageDataAction() {
        emptyField.value = false
        articlesByNameAndLabelRequest(userStore.$state.searchArticlesNameField,
            +(userStore.$state.searchLabelField as any),
            0,
            0,
            pageInfo.value.pageNum,
            pageInfo.value.pageSize
        ).then((res: any) => {
            if (res.list) {
                let domParser = new DOMParser();
                let index = 0
                for (let value of res.list) {
                    // console.log(++index)
                    renderMarkdownToHtml(value.articleContent.articleMd).then(value1 => {
                        value.articleContent.articleMd = domParser.parseFromString(value1, "text/html").body.textContent
                        articles.value.push(value)
                        console.log(articles.value.length >= 0 ? false : true)
                        emptyField.value = articles.value.length >= 0 ? false : true
                    })
                }
                console.log(res.list)
                // delete res.list
                pageInfo.value = {...pageInfo.value, ...res}
            }
            loadingAction()
        }).catch(error => {
            emptyField.value = true
            pageInfo.value.loading = false
        })
    }

    //加载状态验证
    function loadingAction() {
        emptyField.value = articles.value.length > 0 ? false : true
        const {total, pageNum, pageSize} = pageInfo.value
        if ((pageNum * pageSize) >= total) {
            pageInfo.value = {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                loading: false
            }
        } else {
            pageInfo.value.loading = true
            pageInfo.value.pageNum++
        }
    }

    /*
    * 阅读文章
    * */
    function readerAction(userId: number, articleId: number) {
        let routeUrl = router.resolve({
            name: "reader",
            params: {
                userId,
                articleId
            }

        });
        window.open(routeUrl.href, '_blank');
    }

    function toHome(userId: number) {
        userStore.$patch({
            mobileMenu: menuOptions(userId) as any
        })
        router.push({
            name: "personalHomepage",
            params: {
                userId
            }
        })
    }

    function anRquest() {
        emptyField.value = false
        articlesByNameAndLabelAndUserIdRequest(userStore.$state.searchArticlesNameField,
            +(userStore.$state.searchLabelField as any),
            userStore.$state.user.id,
            0,
            0,
            pageInfo.value.pageNum,
            pageInfo.value.pageSize
        ).then((res: any) => {
            if (res.list) {
                let domParser = new DOMParser();
                let index = 0
                for (let value of res.list) {
                    // console.log(++index)
                    renderMarkdownToHtml(value.articleContent.articleMd).then(value1 => {
                        value.articleContent.articleMd = domParser.parseFromString(value1, "text/html").body.textContent
                        articles.value.push(value)
                        console.log(articles.value.length >= 0 ? false : true)
                        emptyField.value = articles.value.length >= 0 ? false : true
                    })
                }
                console.log(res.list)
                // delete res.list
                pageInfo.value = {...pageInfo.value, ...res}
            }
            loadingAction()
        }).catch(error => {
            emptyField.value = true
            pageInfo.value.loading = false
        })
    }

    function changeTab(name: string) {
        userStore.$patch({
            searchArticlesNameField: "",
            searchLabelField: null
        })
        searchAction()
    }

    const tabValue = ref<string>("综合")

    function handleBeforeLeave(name: string) {
        if (!userStore.$state.user.id && name == "关注") {
            userStore.$patch({
                loginStatus: true
            })
            return false
        }
        tabValue.value = name;
        return true
    }

    const ohtArticle = ref<BlogArticle[]>([])

    function getOhtArticleAction() {
        ohtArticle.value = []
        getOhtArticleRequest().then(res => {
            ohtArticle.value = res
        })
    }

    const ohtUser = ref<BlogAttention[]>([])

    function getOhtUserAction() {
        ohtUser.value = []
        getOhtUserRequest().then(res => {
            ohtUser.value = res
        })
    }

    onMounted(function () {
        getOhtUserAction()
        getOhtArticleAction()
    })

    function addAttentionAction(userId: number, user: BlogUser) {
        const localUserId = userStore.getUser.id
        // const user = articles.value?.user
        if (user?.fensUserId && user.fensUserId.includes(localUserId)) {
            deleteAttentionRequest(userId, localUserId).then(res => {
                if (res) {
                    user?.fensUserId.splice(user?.fensUserId?.indexOf(localUserId), 1)
                }
            })
        } else {
            addAttentionRequest(userId, localUserId).then(res => {
                if (res) {
                    const fensUserIds = user?.fensUserId
                    if (fensUserIds) {
                        fensUserIds.push(userStore.getUser.id);
                    } else {
                        (user as any).fensUserId.push(userStore.getUser.id) as any
                    }
                }
            })
        }
    }
</script>

<template>
    <div class="homePage">
        <n-card id="mobile" :bordered="false" :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
            <template #header-extra>
            </template>
            <template #header>
                <n-tabs default-value="综合" @before-leave="handleBeforeLeave" @update:value="changeTab"
                        type="bar">
                    <n-tab-pane name="综合" tab="综合">
                    </n-tab-pane>
                    <n-tab-pane name="关注" tab="关注">
                    </n-tab-pane>
                </n-tabs>
            </template>
            <n-list clickable style="padding: 0px;">
                <n-list-item @click="readerAction(ele.user.id,ele.articleContent.articleId)"
                             v-for="ele in articles">
                    <n-thing>
                        <template #header>
                            <n-text strong>
                                <div style="display: flex;justify-content: center;align-items: center">
                                    {{ele.articleTitle}}
                                </div>
                            </n-text>
                        </template>
                        <template #footer>
                            <n-flex justify="space-between" size="large">
                                <n-text depth="3">
                                    <div style="display: flex;align-items: center;">
                                        <n-avatar
                                                @click.stop="toHome(ele?.user?.id)"
                                                round
                                                size="small"
                                                :src="ele?.user?.userImage"
                                        />
                                        <n-divider vertical/>
                                        <n-icon size="14">
                                            <ChatbubbleEllipsesSharp/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                        {{ele?.commentCount?ele?.commentCoun:""}}
                                        </span>

                                        <n-divider vertical/>
                                        <n-icon size="14">
                                            <Heart/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                         {{ele?.likeUserId.length?ele?.likeUserId.length:""}}
                                        </span>

                                        <n-divider vertical/>
                                        <n-icon size="14">
                                            <EyeRegular/>
                                        </n-icon>
                                        <span style="line-height: 12px;text-align: center;">
                                       {{ele?.browseCount?ele?.browseCount:""}}
                                        </span>

                                    </div>
                                </n-text>
                                <div>
                                    <n-space>
                                        <n-button v-for="la in ele?.articleLableClasss" size="tiny" tertiary>
                                            {{la.articleLables.artName}}
                                        </n-button>
                                    </n-space>
                                </div>
                            </n-flex>
                        </template>
                        <n-grid :x-gap="12"
                                :y-gap="12" :cols="8" style="margin-top: 5px;"
                                layout-shift-disabled>
                            <n-gi :span="ele?.articleImage?7 : 8">
                                <n-ellipsis :tooltip="false" :line-clamp="3" style="max-width: 100%">
                                    <n-text depth="3">
                                        {{ele?.articleContent?.articleMd}}
                                    </n-text>
                                </n-ellipsis>
                            </n-gi>
                            <n-gi v-show="ele?.articleImage">
                                <n-image
                                        width="100%"
                                        :src="ele?.articleImage"
                                />
                            </n-gi>
                        </n-grid>
                    </n-thing>
                </n-list-item>
            </n-list>
            <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
            <Loading v-show="pageInfo.loading"
                     :callback-action="(tabValue =='综合') ? pageDataAction:anRquest"/>
        </n-card>
        <div id="pc" style="display: flex;width: 100%;">
            <div id="pc" style="flex: 5;">
                <n-card :bordered="false" :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                    <template #header-extra>
                    </template>
                    <template #header>
                        <n-tabs default-value="综合" @before-leave="handleBeforeLeave" @update:value="changeTab"
                                type="bar">
                            <n-tab-pane name="综合" tab="综合">
                            </n-tab-pane>
                            <n-tab-pane name="关注" tab="关注">
                            </n-tab-pane>
                        </n-tabs>
                    </template>
                    <n-list clickable style="padding: 0px;">
                        <n-list-item @click="readerAction(ele.user.id,ele.articleContent.articleId)"
                                     v-for="ele in articles">
                            <n-thing>
                                <template #header>
                                    <n-text strong>
                                        <div style="display: flex;justify-content: center;align-items: center" v-html="ele.articleTitle">
                                        </div>
                                    </n-text>
                                </template>
                                <template #footer>
                                    <n-flex justify="space-between" size="large">
                                        <n-text depth="3">
                                            <div style="display: flex;align-items: center;">
                                                <n-avatar
                                                        @click.stop="toHome(ele?.user?.id)"
                                                        round
                                                        size="small"
                                                        :src="ele?.user?.userImage"
                                                />
                                                <n-divider vertical/>
                                                <n-icon size="14">
                                                    <ChatbubbleEllipsesSharp/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                        {{ele?.commentCount?ele?.commentCoun:""}}
                                        </span>

                                                <n-divider vertical/>
                                                <n-icon size="14">
                                                    <Heart/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                         {{ele?.likeUserId.length?ele?.likeUserId.length:""}}
                                        </span>

                                                <n-divider vertical/>
                                                <n-icon size="14">
                                                    <EyeRegular/>
                                                </n-icon>
                                                <span style="line-height: 12px;text-align: center;">
                                       {{ele?.browseCount?ele?.browseCount:""}}
                                        </span>

                                            </div>
                                        </n-text>
                                        <div>
                                            <n-space>
                                                <n-button v-for="la in ele?.articleLableClasss" size="tiny" tertiary>
                                                    {{la.articleLables.artName}}
                                                </n-button>
                                            </n-space>
                                        </div>
                                    </n-flex>
                                </template>
                                <n-grid :x-gap="12"
                                        :y-gap="12" :cols="8" style="margin-top: 5px;"
                                        layout-shift-disabled>
                                    <n-gi :span="ele?.articleImage?7 : 8">
                                        <n-ellipsis :tooltip="false" :line-clamp="3" style="max-width: 100%">
                                            <n-text depth="3">
                                              <span v-html="ele?.articleContent?.articleMd"></span>
                                            </n-text>
                                        </n-ellipsis>
                                    </n-gi>
                                    <n-gi v-show="ele?.articleImage">
                                        <n-image
                                                width="100%"
                                                :src="ele?.articleImage"
                                        />
                                    </n-gi>
                                </n-grid>
                            </n-thing>
                        </n-list-item>
                    </n-list>
                    <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
                    <Loading v-show="pageInfo.loading"
                             :callback-action="(tabValue =='综合') ? pageDataAction:anRquest"/>
                </n-card>
            </div>
            <div id="pc" style="flex: 2;margin-left: 12px;">
                <div v-csdnScroll>
                    <n-space vertical>
                        <n-card
                                title="文章榜"
                                size="small"
                                :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                            <template #header-extra>
                                <n-button @click="getOhtArticleAction" text>
                                    <template #icon>
                                        <n-icon>
                                            <Reload/>
                                        </n-icon>
                                    </template>
                                    换一换
                                </n-button>
                            </template>
                            <n-space vertical>
                                <n-button text v-for="(ele,index) in ohtArticle"
                                          @click="readerAction(ele.user.id,ele?.articleContent?.articleId)">
                                    <n-grid x-gap="12" :cols="1">
                                        <n-gi>
                                            <n-ellipsis :tooltip="false"
                                                        style="max-width: 100%">
                                                {{ele?.articleTitle}}
                                            </n-ellipsis>
                                        </n-gi>
                                    </n-grid>
                                </n-button>
                            </n-space>
                        </n-card>
                        <n-card
                                title="作者榜"
                                size="small"
                                :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                            <template #header-extra>
                                <n-button @click="getOhtUserAction" text>
                                    <template #icon>
                                        <n-icon>
                                            <Reload/>
                                        </n-icon>
                                    </template>
                                    换一换
                                </n-button>
                            </template>
                            <n-space vertical>
                                <n-grid x-gap="12" :cols="1">
                                    <n-gi>
                                        <n-list>
                                            <n-list-item v-for="(ele,index) in ohtUser"
                                                         v-show="userStore.getUser.id != ele?.attentionUser?.id">
                                                <template #prefix>
                                                    <n-avatar
                                                            round
                                                            @click.stop="toHome(ele?.attentionUser?.id)"
                                                            size="large"
                                                            :src="ele.attentionUser.userImage"
                                                    />
                                                </template>
                                                <template #suffix>
                                                    <!--                                                    <n-button strong secondary type="info">-->
                                                    <!--                                                        关注-->
                                                    <!--                                                    </n-button>-->
                                                    <n-button
                                                            @click="addAttentionAction(ele?.attentionUser.id,ele.attentionUser)"
                                                            strong secondary
                                                            :type="ele?.attentionUser.fensUserId?.includes(userStore.getUser.id)?'tertiary':'info'">
                                                        {{ele?.attentionUser.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                                    </n-button>
                                                </template>
                                                <n-thing :title="ele.attentionUser.userName">
                                                </n-thing>
                                            </n-list-item>
                                        </n-list>
                                    </n-gi>
                                </n-grid>
                            </n-space>
                        </n-card>
                    </n-space>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    .homePage {
        width: 100%;
    }

    .more:hover {
        color: #63e2b7;
        cursor: pointer;
    }

    ::v-deep(.n-card__content) {
        padding: 0px 12px;
        padding-bottom: 20px;
    }

    ::v-deep(.n-card.n-card--content-segmented) {
        > .n-card__content {
            padding-top: 20px;
        }
    }

    ::v-deep( .n-thing-main__content) {
        margin-top: 0px !important;
    }

    ::v-deep(  .n-thing-main__footer) {
        margin-top: 0px !important;
    }

    ::v-deep(. .n-thing-header) {
        margin-bottom: 0px !important;
    }

</style>
