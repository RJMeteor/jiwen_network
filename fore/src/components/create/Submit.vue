<script setup lang="ts">
    import {ref, onMounted, computed, watchEffect} from "vue"
    import {
        ChevronBackSharp, ChevronForward
    } from "@vicons/ionicons5"
    import indexDB from "@/util/indexDB"
    import {useRoute, useRouter} from "vue-router";
    import {defaultArticle, defaultArticleContent, defaultArticleLabel, defaultArticleLabelClass} from "@/d/defautls";
    import {vditor} from "@/config/data/create";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {
        getArticlesByUserIdAndArticlesIdRequest,
        getLablesRequest,
        inserArticleRequest,
        updateArticleRequest
    } from "@/config/request";
    import {useUserStore} from "@/config/store/user";
    //indexdb表名
    const table = {
        name: indexDB.store.submitArticleContent.name
    }
    watchEffect(async function () {
        //初始化indexdb
        indexDB.initDB()
    })

    const formRef = ref(null)
    const articleField = ref<BlogArticle>({...defaultArticle})  //提交的文章信息
    const articleContentField = ref({...defaultArticleContent}) //提价的文章内容
    const articleLabelField = ref({...defaultArticleLabel}) //文章标签
    const labelsField = ref<any>([]) //提交多个文章标签
    const labelsOptionField = ref<any>([])
    const classTypesField = [
        {
            label: "文章",
            value: 0
        },
        {
            label: "话题",
            value: 1
        }
    ]

    const router = useRouter()
    const userStore = useUserStore()
    const route = useRoute()
    console.log(route.params.contentId);

    onMounted(async function () {
        let db = await indexDB.openDB();
        let request = db
            .transaction(table.name, "readwrite")
            .objectStore(table.name).get(+route.params.contentId)
        request.onsuccess = function () {
            articleContentField.value.articleMd = request.result.content;
        }
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

        const {type, articleIdto} = route.params
        if (Object.is(type, "request")) {
            //    从后端请求数据
            getArticlesByUserIdAndArticlesIdRequest(userStore.getUser.id, +articleIdto).then(async (res) => {
                if (res) {
                    await getIndexDBToArticleContent()
                    articleField.value = res;
                    res.articleLableClasss?.forEach(ele => {
                        labelsField.value.push(ele.articleLables?.id)
                    });
                    articleField.value.id = articleField.value.articleContent?.articleId as number
                    articleField.value.articleContent = articleContentField.value
                } else {
                    window.$message.error("编辑错误")
                    router.back()
                }
            }).catch(error => {
                router.back()
            })
        } else if (Object.is(type, "indexDB")) {
            await getIndexDBToArticleContent()
        } else {
            await getIndexDBToArticleContent()
        }
    })

    //返回编辑区
    function preAction() {
        router.push({
            name: `create`,
            params: {
                type: route.params.type,
                id: route.params.articleIdto
            }
        })
    }


    //清理提交的数据，或误保存（action=0）的数据
    async function clearDbItem() {
        let db = await indexDB.openDB();
        let request = db
            .transaction(table.name, "readwrite")
            .objectStore(table.name);
        let newVar = request.getAll();
        newVar.onsuccess = function () {
            newVar.result?.forEach((value, index) => {
                if (!value.active || (value.id == +route.params.contentId)) {
                    indexDB.delete(table, value.id)
                }
            })
        }
    }


    //indexDB中获取文章内容
    async function getIndexDBToArticleContent() {
        let db = await indexDB.openDB();
        let request = db
            .transaction(table.name, "readwrite")
            .objectStore(table.name);
        let newVar = request.get(+route.params.contentId);
        newVar.onsuccess = function () {
            if (newVar.result) {
                let domParser = new DOMParser();
                articleContentField.value.articleMd = newVar.result.content
                articleField.value.userId = userStore.getUser.id
                articleField.value.articleContent = articleContentField.value
                renderMarkdownToHtml(newVar.result.content).then(value1 => {
                    let imageEle = domParser.parseFromString(value1, "text/html").body.querySelector("img");
                    articleField.value.articleImage = imageEle?.src ? imageEle?.src : ""
                })
            } else {
                window.$message.error("无文章内容")
                router.back()
            }
        }
    }

    // 提交数据请求
    async function submitAction() {
        if (!articleField.value.articleTitle) {
            window.$message.error("文章名不能为空！")
            return
        }
        let labels: any = [];
        for (let valueElement of labelsField.value) {
            let defalut = {...defaultArticleLabelClass}
            defalut.articleLablesId = valueElement
            if (+route.params.articleIdto > 0) {
                defalut.articleId = +route.params.articleIdto
            }
            labels.push(defalut)
        }
        articleField.value.articlePrivacy = articleField.value.articlePrivacy ? 1 : 0
        articleField.value.articleLableClasss = labels
        if (route.params.type == "request") {
            updateArticleRequest(articleField.value).then(res => {
                if (res) {
                    router.go(-2)
                }
            })
            console.log(articleField.value)
        } else {
            inserArticleRequest(articleField.value).then(res => {
                if (res) {
                    clearDbItem()
                    router.push({
                        name: `create`,
                        params: {
                            type: "other",
                            id: 0
                        }
                    })
                }
            })
        }
    }


    const lsfls = computed(() => {
        return articleField.value.articlePrivacy ? true : false
    })
</script>

<template>
    <div class="submit">
        <n-card style="height: calc(100vh - 12 * 2px);">
            <n-space vertical>
                <n-form
                        ref="formRef"
                        :model="articleField"
                        label-placement="top"
                        :label-width="160">
                    <n-form-item label="名字" path="articleName">
                        <n-input v-model:value="articleField.articleTitle" placeholder="文章名"/>
                    </n-form-item>
                    <n-form-item label="标签" path="label">
                        <n-select
                                v-model:value="labelsField"
                                placeholder="Select"
                                :options="labelsOptionField"
                                multiple
                        />
                        <!--                        <n-dynamic-tags v-model:value="model.label"/>-->
                    </n-form-item>
                    <n-form-item label="类型" path="selectValue">
                        <n-select
                                :default-value="0"
                                v-model:value="articleField.articleLimiter"
                                placeholder="类型"
                                :options="classTypesField"
                        />
                    </n-form-item>
                    <n-form-item label="私密" path="privacy">
                        <n-space>
                            <n-switch :value="lsfls" v-model:value="articleField.articlePrivacy"/>
                            <n-button type="info" text>
                                {{articleField.articlePrivacy?"私密":"公开"}}
                            </n-button>
                        </n-space>
                    </n-form-item>
                </n-form>
                <n-flex justify="space-between">
                    <n-button @click="preAction" strong secondary icon-placement="left" type="primary">
                        <template #icon>
                            <n-icon>
                                <ChevronBackSharp/>
                            </n-icon>
                        </template>
                        上一步
                    </n-button>
                    <n-button @click="submitAction" strong secondary type="primary">
                        提交
                    </n-button>
                </n-flex>
            </n-space>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .submit {
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        padding: 12px;
    }

</style>
