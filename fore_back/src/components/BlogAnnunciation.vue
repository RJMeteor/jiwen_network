<script setup lang="tsx">
    import {ref, onMounted, nextTick, reactive} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';
    import {tableInit} from "@/data/table";
    import BPagination from "@/components/BPagination.vue"
    import {tableStructure} from "@/hooks/tableStructure";
    import {instructionsBackMessages, instructionsFeedback} from "@/data/Instructions";
    import {
        addBackRequest,
        addLablesRequest, cancelDisabledLableIdsRequest, deleteBackIdsRequest, disabledLableIdsRequest, editBackRequest,
        editLableRequest,
        getBackListByNameRequest,
        getLableListByNameRequest
    } from "@/config/request";

    const {
        deletedStatus, //判断删除类型
        selectEmpty, //控制删除按钮显示
        selectedRowKeys,
        columns,
        data,
        handleRowClick,
        rehandleSelectChange
    } = tableInit();

    const tableHeight = ref<number>(110)
    columns.value = tableStructure(instructionsBackMessages)
    data.value = []
    onMounted(function () {
        nextTick(function () {
            tableHeight.value = (document.getElementById("content_box")?.offsetHeight as number) - 170
        })
    })

    const annunciationField = reactive<{
        annunciationName: string,
        startTime: string,
        endTime: string
    }>({
        annunciationName: "",
        startTime: "",
        endTime: ""
    })
    const pageInfoP = ref()
    const visibleModal = ref<boolean>(false)
    const modelContent = ref<string>("发布公告")  //公告遮罩标题
    const editAnnunciation = ref<BlogBackMessages>() //正在编辑的公告
    const annunciationInput = ref<string>("") //发布公告的内容

    function editCallback(row) {
        modelContent.value = "编辑标签"
        annunciationInput.value = row.backContent
        visibleModal.value = !visibleModal.value
        editAnnunciation.value = row
    }

    const requestData = (pageInfo) => {
        getBackListByNameRequest(annunciationField.annunciationName, annunciationField.startTime, annunciationField.endTime, pageInfo.pageNum, pageInfo.pageSize).then(res => {
            data.value = res.list?.map(value => {
                const info = {index: 0, ...value, datas:data,selectKeys: selectedRowKeys, editCallback}
                info.index = value.id
                return info
            })
            pageInfo.total = res.total
            pageInfoP.value = pageInfo
        })
        pageInfoP.value = pageInfo
        // MessagePlugin.success(`pageSize变化为${JSON.stringify(pageInfo)}`);
        // console.log(pageInfo);
        console.log(annunciationField);
    };

    function handleRangePick(value, context) {
        let times = context.dayjsValue.map((d) => d.format('YYYY-MM-DD'))
        annunciationField.startTime = times[0]
        annunciationField.endTime = times[1]
    }


    function changeModal() {
        annunciationInput.value = ""
        modelContent.value = "发布公告"
        visibleModal.value = !visibleModal.value
    }

    function addAnnunciation() {
        if (!(annunciationInput.value && annunciationInput.value.trim().length)) {
            return;
        }
        if (Object.is("编辑标签", modelContent.value)) {
            editBackRequest(editAnnunciation.value?.id as number, annunciationInput.value.trim()).then(res => {
                if (res) {
                    editAnnunciation.value && (editAnnunciation.value.backContent = annunciationInput.value.trim())
                    visibleModal.value = !visibleModal.value
                }
            })
        } else {
            addBackRequest(annunciationInput.value.trim()).then(res => {
                if (res) {
                    requestData(pageInfoP.value)
                    annunciationInput.value = ""
                    visibleModal.value = false
                }
            })
        }
    }

    function deleteBackIds() {
        deleteBackIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }
</script>
<template>
    <t-card style="border-radius: 0px;" :bordered="false">
        <t-dialog
                v-model:visible="visibleModal"
                :header="modelContent"
                mode="modal"
                draggable
                :on-confirm="addAnnunciation"
        >
            <template #body>
                <t-textarea
                        v-model="annunciationInput"
                        placeholder="公告内容"
                        name="description"
                        :autosize="{ minRows: 10, maxRows: 20 }"
                />
            </template>
        </t-dialog>


        <t-space direction="vertical">
            <div style="display: flex;justify-content: space-between;">
                <div>
                    <t-space>
                        <t-input label="公告名：" v-model="annunciationField.annunciationName" clearable placeholder="请输入"
                                 @clear="onClear"/>
                        <t-date-range-picker allow-input clearable @change="handleRangePick"/>
                        <t-button @click="requestData(pageInfoP)" theme="primary" shape="rectangle" variant="base">
                            搜索
                        </t-button>
                    </t-space>
                </div>
                <div>
                    <t-space>
                        <t-button @click="changeModal" theme="success" shape="rectangle" variant="base">
                            发布
                        </t-button>
                        <t-space v-if="selectEmpty">
                            <t-button @click="deleteBackIds" theme="danger">
                                删除
                            </t-button>
                        </t-space>
                    </t-space>
                </div>
            </div>
            <!-- 当数据为空需要占位时，会显示 cellEmptyContent -->
            <t-table
                    row-key="index"
                    :data="data"
                    :height="tableHeight"
                    :headerAffixedTop="true"
                    :columns="columns"
                    :stripe="false"
                    :bordered="false"
                    :hover="true"
                    :table-layout="'fixed'"
                    size="medium"
                    :select-on-row-click="false"
                    :selected-row-keys="selectedRowKeys"
                    @select-change="rehandleSelectChange"
                    :show-header="true"
                    cell-empty-content="-"
                    resizable
                    lazy-load
                    @row-click="handleRowClick"
            >
                <template #expandedRow="{ row }">
                    <div class="more-detail">
                        <p class="title"><b>公告内容:</b></p>
                        <p v-html="row.backContent?.replaceAll('\n','<br/>')"></p>
                    </div>
                </template>
            </t-table>
            <BPagination @page="requestData"/>
        </t-space>
    </t-card>
</template>

<style scoped>


</style>