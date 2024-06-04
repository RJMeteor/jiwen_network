<script setup lang="ts">
    import {ref, onMounted, computed, nextTick, reactive} from "vue";
    import {
        EllipsisHorizontalCircleOutline,
    } from "@vicons/ionicons5"
    import {
        addFeedbackRequest, deleteFavoriteArticlesOtherByIdRequest, deleteFeedbackIdRequest,
        deleteFeedbackIdsRequest,
        editFeedbackRequest,
        getBackListByNameRequest,
        getFeedbackListAndUserIdRequest
    } from "@/config/request";
    import {useUserStore} from "@/config/store/user";
    import {favoritesComponentInitField} from "@/config/data/personalFavorites";

    const {
        executeCallback,
        options,
        checkBoxShow,
        handleSelect,
        pushKey,
        handleUpdateValue,
        citiesRef
    } = favoritesComponentInitField()
    const feedbackField = ref<{
        backs: BlogFeedback[]
        feebackInput: string
    }>({
        backs: [],
        feebackInput: ""
    })
    // 24
    const heightFeedbck = ref<number>(0)
    onMounted(function () {
        loadFeedbackList()
        nextTick(function () {
            heightFeedbck.value = window.innerHeight - (document.getElementById("feedbackBox")?.getBoundingClientRect().top as number) - 34-120
        })

    })

    const userStore = useUserStore()
    executeCallback.value = reloadCollect

    function reloadCollect(ids: number[]) {
        deleteFeedbackIdsRequest(ids).then(res => {
            if (res) {
                loadFeedbackList()
            }
        })
    }

    function loadFeedbackList() {
        getFeedbackListAndUserIdRequest(userStore.getUser.id).then(res => {
                if (res?.length) {
                    feedbackField.value.backs = res
                }
            }
        )
    }

    const editField = reactive<{ isEdit: string, backItem: BlogFeedback | undefined }>({
        isEdit: "提交",
        backItem: undefined
    })

    function addFeedBackAction() {
        if (!feedbackField.value.feebackInput) {
            window.$message.error("反馈内容不能为空")
            return;
        }
        if (Object.is(editField.isEdit, "提交")) {
            addFeedbackRequest(userStore.getUser.id, feedbackField.value.feebackInput).then(res => {
                if (res) {
                    feedbackField.value.feebackInput = ""
                    loadFeedbackList()
                }
            })
        } else if (Object.is(editField.isEdit, "修改")) {
            editFeedbackRequest(editField.backItem?.feedbackContent?.id as number, feedbackField.value.feebackInput).then(res => {
                if (res) {
                    (editField.backItem?.feedbackContent as any).feedbackContent = feedbackField.value.feebackInput
                    feedbackField.value.feebackInput = ""
                    editField.isEdit = "提交"
                }
            })
        }

    }


    function editActive(item: BlogFeedback) {
        editField.isEdit = "修改"
        feedbackField.value.feebackInput = item.feedbackContent?.feedbackContent as string
        editField.backItem = item
    }

    function deleteActive(item: BlogFeedback, index: number) {
        console.log(item.id)
        deleteFeedbackIdRequest(item.id).then(res => {
            if (res) {
                feedbackField.value.backs.splice(index, 1)
            }
        })
    }

    /*
    * 输入框事情焦点时回调的方法
    * */
    function commentInputBlurAction() {
        editField.isEdit = "提交"
        feedbackField.value.feebackInput = ""
    }


</script>

<template>
    <div id="feedbackBox" class="feedback">
        <n-space vertical>
            <n-card title="反馈受理" style="margin-bottom: 16px">
                <template #header-extra>
                    <div style="display: flex;align-items: center;">
                        <n-dropdown
                                trigger="hover"
                                :options="options"
                                :show-arrow="true"
                                @select="handleSelect"
                        >
                            <n-icon size="25">
                                <EllipsisHorizontalCircleOutline/>
                            </n-icon>
                        </n-dropdown>
                    </div>
                </template>
                <n-checkbox-group :value="citiesRef" @update:value="handleUpdateValue">
                    <n-tabs type="line" animated>
                        <n-tab-pane name="oasis" tab="未受理">
                            <n-scrollbar :style="{maxHeight: heightFeedbck*7 / 10+'px'}">
                                <n-list clickable hoverable>
                                    <n-list-item
                                            v-for="(ele,index) in feedbackField.backs.filter(value =>  value.active == 0)"
                                            :key="index">
                                        <template v-if="checkBoxShow" #prefix>
                                            <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                                            {{pushKey(ele?.id)}}
                                        </template>
                                        <n-collapse>
                                            <n-collapse-item :name="index">
                                                <p v-html="ele?.feedbackContent?.feedbackContent?.replaceAll('\n','<br/>')"></p>
                                                <template #header-extra>
                                                    <n-space>
                                                        <span>{{ele?.createTime}}</span>
                                                        <n-button @click="editActive(ele)" type="info" text>
                                                            编辑
                                                        </n-button>
                                                        <n-button @click="deleteActive(ele,index)" type="error" text>
                                                            删除
                                                        </n-button>
                                                    </n-space>
                                                </template>
                                                <template #header>
                                                    <n-ellipsis style="max-width: 300px" :tooltip="false">
                                                        {{ele?.feedbackContent?.feedbackContent}}
                                                    </n-ellipsis>
                                                </template>
                                            </n-collapse-item>
                                        </n-collapse>
                                    </n-list-item>
                                </n-list>
                            </n-scrollbar>
                        </n-tab-pane>
                        <n-tab-pane name="the beatles" tab="已受理">
                            <n-scrollbar :style="{maxHeight: heightFeedbck*7 / 10+'px'}">
                                <n-list clickable hoverable>
                                    <n-list-item
                                            v-for="(ele,index) in feedbackField.backs.filter(value =>  value.active == 1)"
                                            :key="index">
                                        <template v-if="checkBoxShow" #prefix>
                                            <n-checkbox v-show="checkBoxShow" :value="ele?.id"/>
                                            {{pushKey(ele?.id)}}
                                        </template>
                                        <n-collapse>
                                            <n-collapse-item :name="index">
                                                <p v-html="ele?.feedbackContent?.feedbackContent?.replaceAll('\n','<br/>')"></p>
                                                <template #header-extra>
                                                    <n-space>
                                                        <span>{{ele?.createTime}}</span>
<!--                                                        <n-button @click="editActive(ele)" type="info" text>-->
<!--                                                            编辑-->
<!--                                                        </n-button>-->
                                                        <n-button @click="deleteActive(ele,index)" type="error" text>
                                                            删除
                                                        </n-button>
                                                    </n-space>
                                                </template>
                                                <template #header>
                                                    <n-ellipsis style="max-width: 50px" :tooltip="false">
                                                        {{ele?.feedbackContent?.feedbackContent}}
                                                    </n-ellipsis>
                                                </template>
                                            </n-collapse-item>
                                        </n-collapse>
                                    </n-list-item>
                                </n-list>
                            </n-scrollbar>
                        </n-tab-pane>
                    </n-tabs>
                </n-checkbox-group>
            </n-card>
            <n-input
                    v-model:value="feedbackField.feebackInput"
                    type="textarea"
                    placeholder="写下你反馈的内容..."
                    size="small"
                    @blur="commentInputBlurAction"
                    :autosize="{
        minRows:(heightFeedbck*3 / 10) /24 > 6 ? (heightFeedbck*3 / 10) /24 : 6,
        maxRows: (heightFeedbck*3 / 10) /24 > 6 ? (heightFeedbck*3 / 10) /24 : 6
      }"
                    round clearable>
                <template #suffix>
                    <n-button @click="addFeedBackAction" strong secondary type="primary">
                        {{editField.isEdit}}
                    </n-button>
                </template>
            </n-input>
            <!--            <n-flex justify="space-between">-->
            <!--                <div></div>-->
            <!--                <n-button @click="addFeedBackAction" type="primary">-->
            <!--                    {{editField.isEdit}}-->
            <!--                </n-button>-->
            <!--            </n-flex>-->
        </n-space>
    </div>
</template>

<style lang="scss" scoped>
    .feedback {
        width: 100%;
    }

    ::v-deep(.n-input__suffix) {
        .n-button {
            align-self: flex-end !important;
            margin-bottom: 12px;
        }
    }

</style>
