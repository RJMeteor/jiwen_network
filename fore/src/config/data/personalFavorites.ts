import {
    DataTableBaseColumn,
    DataTableColumns,
    DataTableRowKey,
    DropdownOption,
    MenuOption,
    NButton,
    NFlex,
    NIcon,
    NSpace
} from "naive-ui";
import {deleteFavoriteArticleByIdRequest} from "@/config/request";
import {h, reactive, ref} from "vue"
import {Trash, EllipsisHorizontalCircleOutline} from "@vicons/ionicons5";
import {deleteFavoriteClassByIdRequest} from "../request";
import {NTag} from "naive-ui/lib";
import {RouterLink, useRouter} from "vue-router";


const favoritesComponentInitField = function () {

    const executeCallback = ref<(ids: number[]) => void>(() => {
    })  //完成执行操作的后要执行的操作
    const options = ref<DropdownOption[]>([
        {
            label: '操作',
            key: "delete",
            disabled: true,
            children: [
                {
                    label: '删除',
                    key: 'execute',
                },
                {
                    label: '全选',
                    key: 'all',
                },
                {
                    label: '取消全选',
                    key: 'no',
                },
            ]
        },
        {
            label: '多洗激活/隐藏',
            key: 'show',
        },
    ])
    const checkBoxShow = ref<boolean>(false)  //多选框的显示状态
    const allSelect: number[] = []
    const citiesRef = ref<(string | number)[] | null>(null)  //多选框的的组件实例，方便后面的到选中的收藏夹ID

    //所以文件夹的ID,方便后面显示选中
    function pushKey(key: number) {
        allSelect.push(key)
    }

    //触发收藏夹头下拉框回调
    function handleSelect(key: string | number, option: DropdownOption) {
        switch (key) {
            //显示复选框
            case "show": {
                checkBoxShow.value = !checkBoxShow.value
                if (!checkBoxShow.value) {
                    citiesRef.value = []
                }
                options.value[0].disabled = !checkBoxShow.value
                break
            }
            //全选
            case "all": {
                if (checkBoxShow.value) {
                    citiesRef.value = allSelect
                }
                break
            }
            //取消全选
            case "no": {
                if (checkBoxShow.value) {
                    citiesRef.value = []
                }
                break
            }
            //执行要请求的操作
            case "execute": {
                if (checkBoxShow.value) {
                    const all: any = citiesRef.value
                    executeCallback.value.call(undefined, all)
                    // deleteFavoriteClassByIdRequest(all).then(res => {
                    //     if (res) {
                    //         executeCallback.value()
                    //     }
                    // })
                }
                break
            }
        }
        // window.$message.info(JSON.stringify(option))
    }

    //删除选中的内容ID
    function handleUpdateValue(value: (string | number)[]) {
        citiesRef.value = value
    };


    return {
        executeCallback,
        options,
        handleSelect,
        handleUpdateValue,
        pushKey,
        checkBoxShow,
        citiesRef
    }
}

//下面是收藏文章表格数据
//表格的数据格式化类型
type RowData = {
    key: string;
    articlesName: string;
    userName: string;
    lable: string[];
}

function favoritesComponentTableInitField() {
    const router = useRouter()
    const executeCallback = ref<() => void>(() => {
    })  //
    const deleteASExecuteCallback = ref<() => void>(() => {
    })  //
    //数据模拟
    const data = Array.from({length: 10}).map((_, index) => ({
        key: index + "",
        articlesName: `仁爱仁爱仁爱仁爱仁爱仁爱`,
        userName: "任家",
        lable: ["111",],
    } as RowData))
    //渲染表格表头下拉框
    const filterColumn = reactive<DataTableBaseColumn>({
        title: '标签',
        key: 'lable',
        filter: 'default',
        renderFilterIcon: () => {
            return h(NIcon, {color: "#ab1f3f"}, {default: () => h(EllipsisHorizontalCircleOutline)})
        },
        render(row: any) {
            const lable = row.lable.map((value) => {
                return h(
                    NTag,
                    {
                        style: {
                            marginRight: '6px'
                        },
                        // size:"smail",
                        type: 'info',
                        bordered: false
                    },
                    {
                        default: () => value
                    }
                )
            })
            return lable
        },
        renderFilterMenu: ({hide}) => {
            return h(
                NSpace,
                {style: {padding: '12px'}, vertical: true},
                {
                    default: () => [
                        h(
                            NButton,
                            {
                                onClick: () => {
                                    const ids: number[] = []
                                    console.log(checkedRowKeysRef.value)
                                    checkedRowKeysRef.value.forEach(value => {
                                        ids.push(+(value as string).split("-")[0])
                                    })
                                    deleteFavoriteArticleByIdRequest(ids).then(res => {
                                        if (res) {
                                            deleteASExecuteCallback.value()
                                        }
                                    })
                                }
                            },
                            {default: () => '删除'}
                        ),
                        h(
                            NButton,
                            {
                                onClick: () => {
                                    executeCallback.value()
                                }
                            },
                            {default: () => '全部收藏'}
                        ),
                    ]
                }
            )
        }
    })
    //表格的表头定义
    const createColumns = (): DataTableColumns => [
        {
            type: 'selection',
        },
        {
            title: '文章',
            key: 'articlesName',
            render(row) {
                return h(
                    NFlex,
                    {
                        size: 'small',
                        onClick: () => {
                            let routeUrl = router.resolve({
                                name: "reader",
                                params: {
                                    userId: +(row.key as string).split("-")[2],
                                    articleId: +(row.key as string).split("-")[1]
                                }
                            });
                            window.open(routeUrl.href, '_blank');
                        }
                    },
                    {default: () => row.articlesName}
                )
            }
        },
        {
            title: '作者',
            key: 'userName',
            render(row) {
                console.log(row)
                return h(
                    NFlex,
                    {
                        size: 'small',
                        onClick: () => {
                            let routeUrl = router.resolve({
                                name: "personalHomepage",
                                params: {
                                    userId: +(row.key as string).split("-")[2],
                                }
                            });
                            window.open(routeUrl.href, '_blank');
                        }
                    },
                    {default: () => row.userName}
                )
            }
        },
        filterColumn,
    ]
    const checkedRowKeysRef = ref<DataTableRowKey[]>([])   //表格多选组件的实力
    const columns = createColumns() //创建爱你表头
    const rowKey = (row: RowData) => row.key  //


    //点击表格多选框的回调
    function handleCheck(rowKeys: DataTableRowKey[]) {
        checkedRowKeysRef.value = rowKeys
    }

    //点击表格每行的回调
    function rowProps(row: RowData) {
        return {
            style: 'cursor: pointer;',
            onClick: () => {
                // window.$message.info(row.articlesName)
            }
        }
    }

    return {
        executeCallback,
        deleteASExecuteCallback,
        checkedRowKeysRef,
        columns,
        rowKey,
        rowProps,
        handleCheck,
        data
    }
}

export {
    favoritesComponentInitField,
    favoritesComponentTableInitField
}
export type{
    RowData
}