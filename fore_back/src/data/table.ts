import {ref} from "vue";
import {TableProps} from "tdesign-vue-next";

function tableInit() {
    const selectedRowKeys = ref([]);
    let data = ref<TableProps['data']>([]);
    for (let i = 0; i < 20; i++) {
        data.value?.push({
            index: i + 1,
            applicant: ['贾明', '张三', '王芳'][i % 3],
            channel: ['电子签署', '纸质签署', '纸质签署'][i % 3],
            detail: {
                email: ['w.cezkdudy@lhll.au', 'r.nmgw@peurezgn.sl', 'p.cumx@rampblpa.ru'][i % 3],
            },
            createTime: ['2022-01-01', '2022-02-01', '2022-03-01', '2022-04-01', '2022-05-01'][i % 4],
        });
    }
    let columns = ref<TableProps['columns']>([
        {
            colKey: 'row-select',
            type: 'multiple',
            width: 50,
        },
        {
            colKey: 'applicant',
            title: '申请人',
            width: '100',
        },
        {
            colKey: 'channel',
            title: '签署方式',
        },
        {
            colKey: 'detail.email',
            title: '邮箱地址',
            ellipsis: true,
        },
        {
            colKey: 'createTime',
            title: '申请时间',
        },
    ]);
    const deletedStatus = ref<boolean>(false)
    const selectEmpty = ref<boolean>(false)
    const handleRowClick: TableProps['onRowClick'] = (e) => {
        // console.log(e);
    };


    const rehandleSelectChange = (value, ctx) => {
        selectedRowKeys.value = value;
        let selectArr = ctx.selectedRowData
        selectEmpty.value = selectedRowKeys.value.length > 0
        deletedStatus.value = selectEmpty.value && selectArr[0].deleted == 1
        if (selectArr.length > 1) {
            if (selectArr[selectArr.length - 1].deleted != selectArr[selectArr.length - 2].deleted) {
                selectedRowKeys.value.splice(value.length - 1, 1)
                selectArr.splice(selectArr.length - 1, 1)
            }
        }
        // console.log(value, ctx);
    };

    return {
        deletedStatus, //判断删除类型
        selectEmpty, //控制删除按钮显示
        selectedRowKeys,
        columns,
        data,
        handleRowClick,
        rehandleSelectChange
    }
}

const showArticle = ref<boolean>(false)
const showArticleMDRef = ref<string>("")

export {
    tableInit,
    showArticleMDRef,
    showArticle
}
