import {ref} from "vue"
import {marked} from 'marked'
import {markedHighlight} from "marked-highlight"
import hljs from 'highlight.js'
import {TocbotTemplate} from "@/util/tocbot";

let tocbot = new TocbotTemplate()
const preViewImages = ref<string[]>([])
marked.use({
    async: true,
    pedantic: false,
    gfm: true,
})

marked.use(markedHighlight({
    langPrefix: 'hljs language-',
    highlight(code, lang) {
        const language = hljs.getLanguage(lang) ? lang : 'javascript'
        return hljs.highlight(code, {language}).value
    }
}))

marked.use({
    renderer: {
        text(text: string): string {
            return text
        },
        heading(text: string, level: number, raw: string): string {
            let index = tocbot.tocbot.pushIndex?.index
            let unique = tocbot.tocbot.pushIndex?.unquie
            let id = `toc${index}+${level}+${raw}+${unique}`
            if (raw.length) {
                tocbot.analysis(level, raw, id)
            }
            return `<h${level} class="mark" id="${id}">${text}</h${level}>`
        },
    },
})

function codeBlockLineNumber(observe: MutationObserver) {
    let container = document.getElementsByClassName("markdown-body")[0]
    new Promise((resolve, reject) => {
        resolve("")
    }).then(function () {
        let images = container.querySelectorAll("img")
        images.forEach(value => {
            preViewImages.value.push(value.src)
        })
    })
}


function renderMarkdownToHtml(markdown: string): Promise<string> {
    const mark: any = marked.parse(markdown)
    tocbot = new TocbotTemplate()
    return mark
}

export {
    renderMarkdownToHtml,
    tocbot,
    preViewImages,
    codeBlockLineNumber
}