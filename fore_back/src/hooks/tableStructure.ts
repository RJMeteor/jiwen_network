import {ref} from "vue"
import {Link} from "tdesign-vue-next";

enum DetectionType {
    String = "[object String]",
    Array = "[object Array]",
    Number = "[object Number]",
    Object = "[object Object]",
    Symbol = "[object Symbol]",
    Function = "[object Function]"

}

const tabelTemplate = {
    colKey: 'operation', title: '操作',
    fixed: 'right',
    cell: (h, {row}) => {
        return [h(Link, {
            theme: "primary", hover: "underline"
        }, "预览")
        ]
        // return "renjia"
    },
}

const detectionType = (type: any) => Object.prototype.toString.call(type)

export function tableStructure(atructure: Object) {
    if (detectionType(atructure) != DetectionType.Object) {
        return;
    }
    let tableArr: any = []
    for (let atructureKey in atructure) {
        if (detectionType(atructure[atructureKey]) == DetectionType.String) {
            tableArr.push({
                colKey: atructureKey,
                title: atructure[atructureKey]

            })
        } else if (detectionType(atructure[atructureKey]) == DetectionType.Object) {
            tableArr.push({
                colKey: atructureKey,
                ...atructure[atructureKey]
            })
        }
    }
    return tableArr
}

