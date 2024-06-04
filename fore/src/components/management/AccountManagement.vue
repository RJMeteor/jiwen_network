<script setup lang="ts">
    import {ref, computed,onUnmounted} from "vue"
    import {UploadFileInfo} from "naive-ui";
    import {Edit} from "@vicons/fa"
    import {defaultUser} from "@/d/defautls";
    import {useUserStore} from "@/config/store/user";
    import {
        idToAllUpdate,
        resetCodeRequest,
        resetRequest,
        updateCodeRequest,
        updateUserRequest
    } from "@/config/request";
    import {
        updateDisabled,
        formRef,
        previewImageUrl,
        showModal,
        formDisable,
        handlePreview,
    } from "@/config/data/accountManagement"
    import {microqueueIn} from "@/util/currency";

    onUnmounted(function () {
        formDisable.value = true
    })

    const model = ref<BlogUser>({...defaultUser})
    const uploadUrl = ref<string>(import.meta.env.BLOG_BASE_URL + '/blogUser/uploadImg')
    const userStore = useUserStore()
    model.value = userStore.getUser
    const previewFileList = ref<UploadFileInfo[]>([
        {
            id: 'ac',
            name: '',
            status: 'finished',
            url: model.value?.userImage
        }
    ])
    const autoCompleteOptions = computed(() => {
        return ['@qq.com'].map((suffix) => {
            const prefix = model.value.userEmail.split('@')[0]
            return {
                label: prefix + suffix,
                value: prefix + suffix
            }
        })
    })
    //忘记密码，重置密码
    const updateCodeField = ref<string>("") //验证码
    const updateCodeTimeField = ref<number>(60) //验证码倒计时
    let timer: any = null

    //修改用户信息
    function idToAllUpdateAction() {
        idToAllUpdate(model.value).then(value => {
            value && userStore.$patch({
                user: model.value
            })
        })
    }

    //上传头像完成
    const imageUrl = ref<string>("")
    function finish(options: { file: UploadFileInfo, event?: ProgressEvent }) {
        let response = JSON.parse((event?.target as XMLHttpRequest).response);
        if (!(response.code > 0)) {
            window.$message.success("上传成功")
            imageUrl.value = response.data
            userStore.$state.user.userImage = response.data
        }
    }

    //预览设置
    function fileListChangeAction(fileList: UploadFileInfo[]) {
        fileList.forEach((value, index) => {
                if (index && value.status == "finished") {
                    fileList.splice(index, 1)
                }
                if (!index){
                    value.url = imageUrl.value
                }
            }
        )
    }

    //发送验证码
    function updateCodeAction() {
        updateCodeRequest(model.value)
        microqueueIn(function () {
            timer = setInterval(function () {
                if (!updateCodeTimeField.value--) {
                    clearInterval(timer)
                    updateCodeTimeField.value = 60;
                }
            }, 1000)
        })
    }
</script>

<template>
    <div class="accountManagement">
        <n-card :bordered="false" title="账号管理" style="margin-bottom: 16px">
            <template #header-extra>
                <n-popover
                        placement="right"
                        trigger="hover"
                >
                    <template #trigger>
                        <n-icon style="cursor: pointer;" @click="formDisable = !formDisable" size="20">
                            <Edit/>
                        </n-icon>
                    </template>
                    <span>编辑</span>
                </n-popover>
            </template>
            <n-space vertical>
                <div>
                    <!--                    :headers="{-->
                    <!--                    'Authorization': token-->
                    <!--                    }"-->
                    <n-upload
                            name="file"
                            method="POST"
                            accept="image/png, image/jpeg,image/webp,image/jpg"
                            :data="{
                            'userId':userStore.getUser.id,
                            'imageUrl':userStore.getUser.userImage
                                }"
                            :action="uploadUrl"
                            :multiple="false"
                            :default-file-list="previewFileList"
                            @finish="finish"
                            @update:file-list="fileListChangeAction"
                            list-type="image-card"
                            :disabled="formDisable"
                            :show-remove-button="false"
                            @preview="handlePreview"
                    />
                    <n-modal
                            v-model:show="showModal"
                            preset="card"
                            style="width: 600px"
                    >
                        <img :src="previewImageUrl" style="width: 100%">
                    </n-modal>
                </div>
                <n-form :disabled="formDisable">
                    <n-form-item label="姓名" path="userName">
                        <n-input v-model:value="model.userName" placeholder="姓名"/>
                    </n-form-item>
                    <n-form-item label="电话号码" path="userName">
                        <n-input v-model:value="model.userPhone" placeholder="电话号码"/>
                    </n-form-item>
                    <n-form-item label="邮箱" path="email">
                        <n-input-group>
                            <n-auto-complete
                                    v-model:value="model.userEmail"
                                    :disabled="true"
                                    :options="autoCompleteOptions"
                                    placeholder="邮箱"
                            />
                            <n-button
                                    @click="updateCodeAction"
                                    :disabled="(updateCodeTimeField>=60 && !formDisable)?false:true"
                                    type="primary" ghost>
                                {{updateCodeTimeField>=60?"发送验证码":updateCodeTimeField+"秒"}}
                            </n-button>
                        </n-input-group>
                    </n-form-item>
                    <n-form-item-row label="验证码">
                        <n-input v-model:value="registCodeField"/>
                    </n-form-item-row>
                    <n-form-item label="性别" path="sex">
                        <n-radio-group v-model:value="model.userSex" name="sex">
                            <n-radio :value="0">
                                男
                            </n-radio>
                            <n-radio :value="1">
                                女
                            </n-radio>
                        </n-radio-group>
                    </n-form-item>
                    <n-form-item label="个性签名" path="personalSignature">
                        <n-input
                                v-model:value="model.userIntro"
                                placeholder="个性签名"
                                type="textarea"
                                :autosize="{
          minRows: 5,
          maxRows: 5
        }"
                        />
                    </n-form-item>
                    <n-flex justify="space-between">
                        <div></div>
                        <n-button @click="idToAllUpdateAction" :disabled="formDisable" type="primary">
                            修改
                        </n-button>
                    </n-flex>
                </n-form>
            </n-space>
        </n-card>
    </div>
</template>

<style lang="scss" scoped>
    .accountManagement {
        width: 100%;
    }

</style>
