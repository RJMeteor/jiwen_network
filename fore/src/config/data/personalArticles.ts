import {DropdownOption, MenuOption} from "naive-ui";
import {ref} from "vue"
import {deleteArticleByIdsRequest} from "@/config/request";

const options = ref<DropdownOption[]>([
    {
        label: '操作',
        key: "delete",
        disabled:true,
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


const checkBoxShow = ref<boolean>(false)
const allSelect: number[] = []
const citiesRef = ref<number[]>([])
const callback = ref<()=>void>(()=>{})

function pushKey(key: number) {
    allSelect.push(key)
}

function handleSelect(key: string | number, option: DropdownOption) {
    switch (key) {
        case "show": {
            checkBoxShow.value = !checkBoxShow.value
            options.value[0].disabled = !checkBoxShow.value
            break
        }
        case "all": {
            if (checkBoxShow.value) {
                citiesRef.value = allSelect
            }
            break
        }
        case "no": {
            if (checkBoxShow.value) {
                citiesRef.value = []
            }
            break
        }
        case "execute": {
            if (checkBoxShow.value) {
                callback.value()
                // console.log(citiesRef.value)
                const all = citiesRef.value
            }
            break
        }
    }
}

function handleUpdateValue(value: number[]) {
    citiesRef.value = value
}

export {
    callback,
    handleSelect,
    options,
    handleUpdateValue,
    pushKey,
    checkBoxShow,
    citiesRef
}