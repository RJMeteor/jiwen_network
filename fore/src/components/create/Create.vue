<script setup lang="ts">
    import {ref, onMounted, watchEffect, watch, nextTick} from "vue"
    import {
        ChevronBackSharp, ChevronForward, Trash
    } from "@vicons/ionicons5"
    import indexDB from "@/util/indexDB"
    import {useRoute, useRouter} from "vue-router";
    import 'vditor/dist/index.css';
    import {changeMdTheme, mdTheme, mouned, vditor} from "@/config/data/create";
    import {useUserStore} from "@/config/store/user";
    import {changeTheme, themeState} from "@/config/data/app";
    import {renderMarkdownToHtml} from "@/util/marked";
    import {getArticlesByUserIdAndArticlesIdRequest} from "@/config/request";
    import {tocbotInit} from "@/util/observer";
    //因为vue是单页面,但是现在重新打开了一个窗口，会重新加载资源，会丢失数据，所以从本地缓存读取主题的状态，改变页面主题
    const userStore = useUserStore()

    const articleIdField = ref<number>(0)
    const editTypeField = ref<string>("")
    const flexRef = ref()
    const draftsActiveField = ref<boolean>(false) //草稿箱状态
    const router = useRouter()
    const route = useRoute()

    onMounted(async () => {
        const rootHeight = document.documentElement.offsetHeight
        const flexHeight = flexRef.value?.$el.offsetHeight
        const height = rootHeight - flexHeight - 3 * 12
        //初始化markdown编辑区
        mouned(height)
        const {type, id} = route.params
        editTypeField.value = type as string
        if (Object.is(type, "request")) {
            //    从后端请求数据
            getArticlesByUserIdAndArticlesIdRequest(userStore.getUser.id, +id).then(res => {
                if (res.articleContent?.articleMd) {
                    articleIdField.value = res.articleContent?.articleId as number
                    vditor.value?.setValue(res.articleContent?.articleMd)
                } else {
                    window.$message.error("无法编辑！")
                    router.back()
                }
            }).catch(error => {
                window.$message.error("无法编辑！")
                router.back()
            })
        } else if (Object.is(type, "indexDB")) {
            let db = await indexDB.openDB();
            let request = db
                .transaction(table.name, "readwrite")
                .objectStore(table.name).get(+id)
            request.onsuccess = function () {
                if (request.result) {
                    vditor.value?.setValue(request.result.content)
                    draftsId.value = request.result.id
                } else {
                    draftsId.value = 0
                }
            }
        } else {

        }
    })


    //indexdb表名
    const table = {
        name: indexDB.store.submitArticleContent.name
    }
    //保存为草稿箱的Id,0表示新建的内容不是切换为草稿箱的
    const draftsId = ref<number>(0)

    watchEffect(async function () {
        //初始化indexdb
        indexDB.initDB()
    })


    const draftsField = ref<any>([]) //草稿箱数据
    //打卡草稿箱遮罩层
    async function draftsActiveAction() {
        draftsActiveField.value = !draftsActiveField.value
        let db = await indexDB.openDB();
        let request = db
            .transaction(table.name, "readwrite")
            .objectStore(table.name);
        let all = request.getAll();
        all.onsuccess = function () {
            draftsField.value = []
            all.result?.forEach((value, index) => {
                if (value.active) {
                    let domParser = new DOMParser();
                    renderMarkdownToHtml(value.content).then(value1 => {
                        let textContent = domParser.parseFromString(value1, "text/html").body.textContent;
                        draftsField.value.push({
                                content: textContent,
                                id: value.id
                            }
                        )
                    })
                }
            })
        }
    }


    //保存为草稿箱
    async function saveTodraftsAtion() {
        if (!(vditor.value?.getHTML())) {
            window.$message.error("写文章内容为空，无法保存！")
            return;
        }
        const data = {
            content: vditor.value?.getValue(),
            active: 1 //active为1表示保存为草稿箱
        }
        if (!draftsId.value) {
            indexDB.insert(table, data).then(value => {
                if (value) {
                    window.$message.success("保存成功")
                }
            }).catch(error => {
                window.$message.error("保存失败")
            })
        } else {
            indexDB.update(table, {...data, id: draftsId.value})
        }
    }

    //跳转提交页面
    function nexAction() {
        if (!(vditor.value?.getHTML())) {
            window.$message.error("写文章内容为空，无法下一步！")
            return;
        }
        nextTick(function () {
            //添加数据到indexdb中用提交文章内容md
            indexDB.insert(table, {
                content: vditor.value?.getValue(),
                active: 0
            }).then(async (value) => {
                let db = await indexDB.openDB();
                let request = db
                    .transaction(table.name, "readwrite")
                    .objectStore(table.name).openCursor(null, "prev")  //prev 按id倒叙排列
                //得到最后一次添加的文章ID
                request.onsuccess = function () {
                    let result = request.result;
                    router.push({
                        name: "submit",
                        params: {
                            type: editTypeField.value,
                            articleIdto: articleIdField.value,
                            contentId: result.value.id
                        }
                    })
                }
            })
        })
    }

    //删除指定草稿文章
    /**
     *
     * @param id
     * @param index 删除的下标，方便删除页面响应数据
     */
    function deleteDraftsItemAction(id: number, index: number) {
        indexDB.delete(table, id).then(value => {
            if (value) {
                draftsField.value.split(index, 1)
                window.$message.success("删除成功")
                if (id == draftsId.value) {
                    draftsId.value = 0
                }
            }
        }).catch(error => {
            window.$message.error("删除失败")
        })
    }

    //切换草稿箱的数据到编辑区
    async function showDraftsItemAction(id: number) {
        const {type} = route.params

        let db = await indexDB.openDB();
        let request = db
            .transaction(table.name, "readwrite")
            .objectStore(table.name);
        let newVar = request.get(id);
        newVar.onsuccess = function () {
            draftsId.value = newVar.result.id  // 记录的草稿箱的内容id，方便后面提交数据
            vditor.value?.setValue(newVar.result.content)
            editTypeField.value = "indexDB"
        }
    }

    //内容重置
    function resetBeginAction() {
        vditor.value?.setValue("")
        draftsId.value = 0
    }
</script>

<template>
    <div class="create">
        <n-drawer v-model:show="draftsActiveField" :width="300" :placement="placement">
            <n-drawer-content :native-scrollbar="false" title="草稿箱">
                <n-list hoverable clickable v-for="(ele,index) in draftsField" :key="index">
                    <n-list-item>
                        <template #suffix>
                            <n-button @click="deleteDraftsItemAction(ele.id)" tertiary circle type="error">
                                <template #icon>
                                    <n-icon>
                                        <Trash/>
                                    </n-icon>
                                </template>
                            </n-button>
                        </template>
                        <n-grid @click="showDraftsItemAction(ele.id,index)" x-gap="12" :cols="1">
                            <n-gi>
                                <n-ellipsis :tooltip="false" style="max-width: 90%">
                                    {{ele.content}}
                                </n-ellipsis>
                            </n-gi>
                        </n-grid>
                    </n-list-item>
                </n-list>
            </n-drawer-content>
        </n-drawer>
        <n-space vertical>
            <div id="vditor"></div>
            <n-flex ref="flexRef" justify="space-between">
                <div>

                </div>
                <n-space>
                    <n-button @click="saveTodraftsAtion" strong secondary type="primary">
                        保存
                    </n-button>
                    <n-button @click="resetBeginAction" strong secondary type="error">
                        清空编辑区
                    </n-button>
                    <n-button @click="draftsActiveAction" type="tertiary">
                        草稿箱
                    </n-button>
                    <n-button @click="nexAction" strong secondary icon-placement="right" type="primary">
                        <template #icon>
                            <n-icon>
                                <ChevronForward/>
                            </n-icon>
                        </template>
                        下一步
                    </n-button>
                </n-space>
            </n-flex>
        </n-space>
    </div>
</template>

<style lang="scss" scoped>
    .create {
        width: 100%;
        height: 100%;
        padding: 12px;
        box-sizing: border-box;
    }

    ::v-deep(.vditor-reset) {
        color: var(--blog_md_color);
    }

</style>
