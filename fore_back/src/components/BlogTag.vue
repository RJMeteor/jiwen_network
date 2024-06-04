<script setup lang="tsx">
    import {ref, onMounted, nextTick} from 'vue';
    import {MessagePlugin} from 'tdesign-vue-next';

    import {tableInit} from "@/data/table";
    import BPagination from "@/components/BPagination.vue"
    import {tableStructure} from "@/hooks/tableStructure";
    import {instructionsArticleLabel, instructionsUser} from "@/data/Instructions";
    import {
        addLablesRequest,
        cancelDisabledArticleIdsRequest,
        cancelDisabledLableIdsRequest,
        cancelDisabledLableRequest,
        disabledArticleIdsRequest,
        disabledLableIdsRequest,
        disabledLableRequest,
        editLableRequest,
        getArticleListRequest,
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
    columns.value = tableStructure(instructionsArticleLabel)
    data.value = []

    onMounted(function () {
        nextTick(function () {
            tableHeight.value = (document.getElementById("content_box")?.offsetHeight as number) - 170
        })
    })
    const lableFidld = ref<string>("")
    const pageInfoP = ref()
    const visibleModal = ref<boolean>(false)
    const modelTitle = ref<string>("添加标签")
    const editLabel = ref<BlogArticleLabel>()
    const tagListField = ref<string>("")

    function editCallback(row) {
        modelTitle.value = "编辑标签"
        tagListField.value = row.artName
        visibleModal.value = !visibleModal.value
        editLabel.value = row
    }


    const requestData = (pageInfo) => {
        getLableListByNameRequest(lableFidld.value, pageInfo.pageNum, pageInfo.pageSize).then(res => {
            data.value = res.list?.map(value => {
                const info = {index: 0, ...value, selectKeys: selectedRowKeys, editCallback}
                info.index = value.id
                return info
            })
            pageInfo.total = res.total
            pageInfoP.value = pageInfo
        })
        // MessagePlugin.success(`pageSize变化为${JSON.stringify(pageInfo)}`);
        pageInfoP.value = pageInfo
    };


    function addTag() {
        if (!(tagListField.value && tagListField.value.trim().length)) {
            return;
        }
        if (Object.is("编辑标签", modelTitle.value)) {
            editLableRequest(editLabel.value?.id as number, tagListField.value.trim()).then(res => {
                if (res) {
                    editLabel.value && (editLabel.value.artName = tagListField.value.trim())
                    visibleModal.value = !visibleModal.value
                }
            })
        } else {
            let strings = tagListField.value.split(",").map(value => value.trim());
            addLablesRequest(strings).then(res => {
                if (res) {
                    requestData(pageInfoP.value)
                    tagListField.value = ""
                    visibleModal.value = false
                }
            })
        }
    }

    function showModel() {
        modelTitle.value = "添加标签"
        tagListField.value = ""
        visibleModal.value = !visibleModal.value
    }

    function cancelDisabledLableIds() {
        cancelDisabledLableIdsRequest(selectedRowKeys.value).then(res => {
            if (res) {
                selectedRowKeys.value.length = 0
                requestData(pageInfoP.value)
            }
        })
    }

    function disabledLableIds() {
        disabledLableIdsRequest(selectedRowKeys.value).then(res => {
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
                :header="modelTitle"
                mode="modal"
                draggable
                :on-confirm="addTag"
        >
            <template #body>
                <t-textarea
                        v-model="tagListField"
                        placeholder="添加多个标签用英文逗号分开"
                        name="description"
                        :autosize="{ minRows: 5, maxRows: 5 }"
                />
            </template>
        </t-dialog>
        <t-space direction="vertical">

            <div style="display: flex;justify-content: space-between;">
                <div>
                    <t-space>
                        <t-input label="标签名：" v-model="lableFidld" clearable placeholder="请输入" @clear="onClear"/>
                        <t-button @click="(pageInfoP.pageNum = 1 ) && requestData(pageInfoP)" theme="primary"
                                  shape="rectangle" variant="base">
                            搜索
                        </t-button>
                    </t-space>
                </div>
                <div>
                    <t-space style="display: flex;align-items: center">
                        <t-button @click="showModel" theme="primary">
                            添加
                        </t-button>
                        <t-space v-if="selectEmpty">
                            <t-button @click="cancelDisabledLableIds" v-if="deletedStatus" theme="success">
                                取消禁用
                            </t-button>
                            <t-button @click="disabledLableIds" v-else theme="danger">
                                禁用
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
                    size="small"
                    :select-on-row-click="false"
                    :selected-row-keys="selectedRowKeys"
                    @select-change="rehandleSelectChange"
                    :show-header="true"
                    cell-empty-content="-"
                    resizable
                    lazy-load
                    @row-click="handleRowClick"
            >
            </t-table>
            <BPagination @page="requestData"/>
        </t-space>
    </t-card>
</template>


<style scoped>


</style>
