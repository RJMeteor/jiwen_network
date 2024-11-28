<script setup lang="ts">
    import {getColor} from "@/util/currency"
    import Loading from "@/d/Loading.vue"
    import {ChatbubbleEllipsesSharp, Heart, Reload, Search} from "@vicons/ionicons5"
    import {EyeRegular, Plus} from "@vicons/fa"
    import TopicContent from "./TopicContent.vue";
    import {onMounted, ref} from "vue";
    import {
        addAttentionRequest,
        articlesByNameAndLabelRequest, deleteAttentionRequest,
        getLablesRequest,
        getOhtArticleRequest, getOhtTopicRequest,
        getOhtUserRequest
    } from "@/config/request";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {useUserStore} from "@/config/store/user";
    import {router} from "@/config/router";
    import {menuOptions} from '@/config/data/personalHomepage'

    const userStore = useUserStore()
    const articles = ref<BlogArticle[]>([])
    const searchLabelField = ref<any>(null)
    const searchArticlesNameField = ref<string>("")
    const emptyField = ref<boolean>(false)
    const pageInfo = ref<any>({
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: true
    })
    const labelsOptionField = ref<any>([])
    onMounted(function () {
        labelsOptionField.value = []
        //得到文章用到的标签
        getLablesRequest().then(res => {
            if (res) {
                res.sort((a, b) => a.artName.localeCompare(b.artName));
                for (let re of res) {
                    labelsOptionField.value.push({
                        label: re.artName,
                        value: re.id
                    })
                }
            }
        })
    })

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
        articlesByNameAndLabelRequest(
            searchArticlesNameField.value,
            +searchLabelField.value,
            1,
            0,
            pageInfo.value.pageNum,
            pageInfo.value.pageSize
        ).then((res: any) => {
            console.log(res)
            if (res.list) {
                let domParser = new DOMParser();
                articles.value.push(...res.list)
                // for (let value of res.list) {
                //     renderMarkdownToHtml(value.articleContent.articleMd).then(value1 => {
                //         value.articleContent.articleMd = domParser.parseFromString(value1, "text/html").body.textContent
                //         emptyField.value = articles.value.length > 0 ? false : true
                //     })
                // }
                delete res.list
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

    const ohtTopic = ref<BlogArticle[]>([])

    function ohtTopicAction() {
        ohtTopic.value = []
        getOhtTopicRequest().then(res => {
            ohtTopic.value = res
        })
    }


    onMounted(function () {
        ohtTopicAction()
    })
    const observer = new MutationObserver(function (mutations, observe) {
        codeBlockLineNumber(observe)
        observe.disconnect()
    });
    const previewTopic = ref<BlogArticle>()
    const showModal = ref<boolean>(false)
    const html = ref<string>()
    const topicContentRef = ref<Element>()

    function changeShowModal(personId: number, topic: BlogArticle) {
        previewTopic.value = topic;
        showModal.value = true;
        renderMarkdownToHtml(topic?.articleContent?.articleMd as string).then(res => {
            if (res) {
                const dr = document.createElement('div')
                dr.style.cssText = "width: 100%;background-color: var(--blog_background_color);"
                dr.className = "markdown-body"
                dr.innerHTML = res
                html.value = res
                observer.observe((topicContentRef.value && topicContentRef.value.getElementsByClassName("markdown-body")[0]) as Node, {
                    childList: true
                })
            }
        })

    }

    const preViewImages = ref<string[]>([])

    function codeBlockLineNumber(observe: MutationObserver) {
        if (topicContentRef.value) {
            let container = topicContentRef.value.getElementsByClassName("markdown-body")[0]
            new Promise((resolve, reject) => {
                resolve("")
            }).then(function () {
                let images = container.querySelectorAll("img")
                images.forEach(value => {
                    preViewImages.value.push(value.src)
                })
            })
        }
    }


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

</script>

<template>
    <div class="topic">
        <div ref="topicContentRef">
            <n-modal v-model:show="showModal">
                <n-card
                        style="width: 600px"
                        :bordered="false"
                        size="huge"
                        role="dialog"
                        aria-modal="true"
                >
                    <div>
                        <n-thing>
                            <template #header>
                                <span style="font-size: 17px;font-weight: bold;">
                                         {{previewTopic?.user?.userName}}
                                </span>
                            </template>
                            <template #description>
                                <n-ellipsis expand-trigger="click" line-clamp="2" :tooltip="false">
                                    {{previewTopic?.user?.userIntro}}
                                </n-ellipsis>
                            </template>
                            <template #header-extra>
                                <n-button
                                        v-if="userStore.getUser.id != previewTopic?.user?.id"
                                        @click="addAttentionAction(previewTopic?.user.id,previewTopic?.user)"
                                        icon-placement="right"
                                        strong secondary
                                        round
                                        :type="previewTopic?.user.fensUserId?.includes(userStore.getUser.id)?'tertiary':'info'">
                                    {{previewTopic?.user.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                    <template #icon>
                                        <n-icon>
                                            <Plus/>
                                        </n-icon>
                                    </template>
                                </n-button>
                            </template>
                            <template #avatar>
                                <n-avatar
                                        round
                                        size="large"
                                        :src="previewTopic?.user?.userImage"
                                />
                            </template>
                        </n-thing>
                        <viewer :images="preViewImages">
                            <n-list style="background-color: var(--blog_foreground_color);">
                                <n-list-item>
                                    <n-space vertical>
                                        <div style="font-size: 25px;font-weight: bold;">
                                            {{previewTopic?.articleTitle}}
                                        </div>
                                        <div>
                                            <n-space vertical>
                                                <div style="display: flex;;align-items: center;">
                                                    <div style="display: flex;;align-items: center;">
                                                        <n-icon size="20">
                                                            <TimeOutline/>
                                                        </n-icon>
                                                        <span style="line-height: 12px;text-align: center;margin-left: 5px;">
                                                   {{articles.updateTime}}
                                                  </span>
                                                    </div>
                                                </div>
                                                <div>
                                                    <div style="display: flex;align-items: center;">
                                                        <n-space>
                                                            <n-button v-for="la in previewTopic?.articleLableClasss"
                                                                      size="tiny"
                                                                      tertiary>
                                                                {{la.articleLables.artName}}
                                                            </n-button>
                                                        </n-space>
                                                    </div>
                                                </div>
                                            </n-space>
                                        </div>
                                    </n-space>
                                </n-list-item>
                            </n-list>
                            <div class="markdown-body" style="padding: 20px;width: 100%;box-sizing: border-box;"
                                 data-theme="light"
                                 v-html="html"/>
                        </viewer>
                    </div>

                </n-card>
            </n-modal>
        </div>
        <div id="mobile">
            <n-card :bordered="false" :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                <template #header-extra>
                    <n-input-group>
                        <n-select style="width: 120px;" v-model:value="searchLabelField" clearable
                                  :options="labelsOptionField"/>
                        <n-input style="max-width: 200px" v-model:value="searchArticlesNameField"
                                 clearable
                                 placeholder="搜索">
                            <template #suffix>
                                <n-icon @click="searchAction" class="blog_cursor" :component="Search" :size="20"/>
                            </template>
                        </n-input>
                    </n-input-group>
                </template>
                <template #header>
                    <n-tabs style="visibility: hidden;" default-value="oasis" type="bar">
                        <n-tab-pane name="综合" tab="综合">
                        </n-tab-pane>
                        <n-tab-pane name="关注" tab="关注">
                        </n-tab-pane>
                    </n-tabs>
                </template>
            </n-card>
            <n-list v-for="(item,index) in articles" :key="index"
                    :style="{marginTop: index==0?'0px':'12px',padding: '0px 20px'}">
                <n-list-item>
                    <n-thing>
                        <template #header>
                                <span style="font-size: 17px;font-weight: bold;">
                                         {{item.user.userName}}
                                </span>
                        </template>
                        <template #description>
                            <n-ellipsis expand-trigger="click" line-clamp="2" :tooltip="false">
                                {{item.user.userIntro}}
                            </n-ellipsis>
                        </template>
                        <template #header-extra>
                            <n-button
                                    v-if="userStore.getUser.id != item?.user?.id"
                                    @click="addAttentionAction(item?.user.id,item?.user)"
                                    icon-placement="right"
                                    strong secondary
                                    round
                                    :type="item?.user.fensUserId?.includes(userStore.getUser.id)?'tertiary':'info'">
                                {{item?.user.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                <template #icon>
                                    <n-icon>
                                        <Plus/>
                                    </n-icon>
                                </template>
                            </n-button>
                        </template>
                        <template #avatar>
                            <n-avatar
                                    round
                                    @click.stop="toHome(item?.user?.id)"
                                    size="large"
                                    :src="item.user.userImage"
                            />
                        </template>
                    </n-thing>
                </n-list-item>
                <TopicContent :articles="item"/>
            </n-list>
            <n-card v-show="emptyField || pageInfo.loading">
                <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
                <Loading v-show="pageInfo.loading" :callback-action="pageDataAction"/>
            </n-card>
        </div>
        <div id="pc" style="display: flex;width: 100%;">
            <div id="pc" style="flex: 5;">
                <n-card id="pc" :bordered="false" :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                    <template #header-extra>
                        <n-input-group>
                            <n-select style="width: 120px;" v-model:value="searchLabelField" clearable
                                      :options="labelsOptionField"/>
                            <n-input style="max-width: 200px" v-model:value="searchArticlesNameField"
                                     clearable
                                     placeholder="搜索">
                                <template #suffix>
                                    <n-icon @click="searchAction" class="blog_cursor" :component="Search" :size="20"/>
                                </template>
                            </n-input>
                        </n-input-group>
                    </template>
                    <template #header>
                        <n-tabs style="visibility: hidden;" default-value="oasis" type="bar">
                            <n-tab-pane name="综合" tab="综合">
                            </n-tab-pane>
                            <n-tab-pane name="关注" tab="关注">
                            </n-tab-pane>
                        </n-tabs>
                    </template>
                </n-card>
                <n-list id="pc" v-for="(item,index) in articles" :key="index"
                        :style="{marginTop: index==0?'0px':'12px',padding: '0px 20px'}">
                    <n-list-item>
                        <n-thing>
                            <template #header>
                                <span style="font-size: 17px;font-weight: bold;">
                                         {{item.user.userName}}
                                </span>
                            </template>
                            <template #description>
                                <n-ellipsis expand-trigger="click" line-clamp="2" :tooltip="false">
                                    {{item.user.userIntro}}
                                </n-ellipsis>
                            </template>
                            <template #header-extra>
                                <n-button
                                        v-if="userStore.getUser.id != item?.user?.id"
                                        @click="addAttentionAction(item?.user.id,item?.user)"
                                        icon-placement="right"
                                        strong secondary
                                        round
                                        :type="item?.user.fensUserId?.includes(userStore.getUser.id)?'tertiary':'info'">
                                    {{item?.user.fensUserId?.includes(userStore.getUser.id)?"已关注":"关注"}}
                                    <template #icon>
                                        <n-icon>
                                            <Plus/>
                                        </n-icon>
                                    </template>
                                </n-button>
                            </template>
                            <template #avatar>
                                <n-avatar
                                        round
                                        @click.stop="toHome(item?.user?.id)"
                                        size="large"
                                        :src="item.user.userImage"
                                />
                            </template>
                        </n-thing>
                    </n-list-item>
                    <TopicContent :articles="item"/>
                </n-list>
                <n-card id="pc" v-show="emptyField || pageInfo.loading">
                    <n-empty v-if="emptyField" style="margin-top: 20px;" description="空空如也..."/>
                    <Loading v-show="pageInfo.loading" :callback-action="pageDataAction"/>
                </n-card>
            </div>
            <div id="pc" style="flex: 2;margin-left: 12px;">
                <div v-csdnScroll>
                    <n-card
                            title="热门话题"
                            size="small"
                            :segmented="{
                      content: true,
                      footer: 'soft'
                    }">
                        <template #header-extra>
                            <n-button @click="ohtTopicAction" text>
                                <template #icon>
                                    <n-icon>
                                        <Reload/>
                                    </n-icon>
                                </template>
                                换一换
                            </n-button>
                        </template>
                        <n-list hoverable clickable>
                            <n-list-item>
                                <n-grid x-gap="12" :cols="1">
                                    <n-gi>
                                        <n-space vertical>
                                            <n-button text v-for="(ele,index) in ohtTopic"
                                                      @click="changeShowModal(ele.user.id,ele)">
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
                                    </n-gi>
                                </n-grid>
                            </n-list-item>
                        </n-list>
                    </n-card>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    .topic {
        width: 100%;
    }

    ::v-deep(.n-thing-avatar) {
        align-items: flex-start !important;
    }

    .more:hover {
        color: #63e2b7;
        cursor: pointer;
    }

    ::v-deep(.n-card__content) {
        padding: 0px 0px;
        padding-bottom: 0px;
    }

    ::v-deep(.n-card__content) {
        padding: 0px 20px;
        padding-bottom: 20px;
    }

    ::v-deep(.n-card.n-card--content-segmented) {
        > .n-card__content {
            padding-top: 20px;
        }
    }

    ::v-deep(.n-thing-avatar) {
        display: flex;
        align-items: center;
    }
</style>
