import {ref, watchEffect} from "vue"
import Vditor from 'vditor'
import {useUserStore} from "@/config/store/user";

const vditor = ref<Vditor>()
const arr = ['headings', 'bold', 'italic', 'strike', '|',
    'list', 'ordered-list', 'check', 'outdent', 'indent', '|',
    'line', 'quote', 'code', 'inline-code', 'link', 'table', 'upload', '|',
    'undo', 'redo', 'export', "edit-mode", "outline", "|"]
const m = arr.map(value => {
    return value == "|" ? value : {
        name: value,
        tipPosition: "sw"
    }
})
let toolbar = [
    ...m,
    {
        name: 'more',
        toolbar: [
            'fullscreen',
            'both',
        ],
    }
]
const mdTheme = ref<"dark" | "classic">("classic")
const contentTheme = ref<"light" | "dark">("light")
const codeTheme = ref<"github" | "dracula">("github")

function changeMdTheme() {
    const userStore = useUserStore()
    mdTheme.value = userStore.getMdThemee
    if (vditor.value) {
        mdTheme.value == "classic" ? (codeTheme.value = "github") : (codeTheme.value = "dracula")
        mdTheme.value == "classic" ? (contentTheme.value = "light") : (contentTheme.value = "dark")
        vditor.value.setTheme(mdTheme.value, contentTheme.value, codeTheme.value)
    }
}

function mouned(height: number) {
    //https://ld246.com/article/1549638745630
    const wr = new Vditor('vditor', {
        height,
        mode: 'ir', //sv左右分屏, ir及时渲染, wysiwyg所见即所得
        minHeight: 500,
        theme: mdTheme.value, //dark
        icon: 'material', // material
        toolbar,
        outline: {
            enable: true,
            position: 'left',
        },
        debugger: false,
        typewriterMode: true,
        placeholder: '',
        image: {
            isPreview: true
        },
        cache: {
            enable: false
        },
        preview: {
            actions: [],
            markdown: {
                toc: true,
                mark: false,
                footnotes: true,
                autoSpace: true,
            },
            theme: {
                current: contentTheme.value
            },
            math: {
                engine: 'KaTeX',
            },
            hljs: {
                lineNumber: true,
                enable: true,
                style: codeTheme.value
            }
        },
        toolbarConfig: {
            pin: false,
        },
        counter: {
            enable: true,
            type: 'text',
        },
        tab: '\t',
        upload: {
            accept: 'image/*',
            multiple: false,
            fieldName: 'file',
            url: import.meta.env.BLOG_BASE_URL+'/blogArticle/uploadimg',
            filename(name) {
                return name.replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, '').replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, '').replace('/\\s/g', '')
            },
            success(editor, msg) {
                msg = JSON.parse(msg) as any
                wr.insertValue(`![${(msg as any).fileName}](${(msg as any).url})`)
            }
        }
    })
    vditor.value = wr
}


export {
    mdTheme,
    vditor,
    mouned,
    changeMdTheme
}