import {DropdownOption, MenuOption} from "naive-ui";
import {ref} from "vue"

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
const citiesRef = ref<(string | number)[] | null>(null)

function pushKey(key: number) {
    allSelect.push(key)
}

function handleSelect(key: string | number, option: DropdownOption) {
    switch (key) {
        case "show": {
            checkBoxShow.value = !checkBoxShow.value
            options.value[0].disabled=!checkBoxShow.value
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
                const all = citiesRef.value
            }
            break
        }
    }
    window.$message.info(JSON.stringify(option))
}


function handleUpdateValue(value: (string | number)[]) {
    citiesRef.value = value
}

export {
    options,
    handleSelect,
    handleUpdateValue,
    pushKey,
    checkBoxShow,
    citiesRef
}