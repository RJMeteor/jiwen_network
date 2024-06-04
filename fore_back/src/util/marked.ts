import {ref} from "vue"
import {marked} from 'marked'
import {markedHighlight} from "marked-highlight"
import hljs from 'highlight.js'

marked.use({
    // 开启异步渲染
    async: true,
    pedantic: false,
    gfm: true,
})

marked.use(markedHighlight({
    langPrefix: 'hljs language-',
    highlight(code, lang) {
        const language = hljs.getLanguage(lang) ? lang : 'javascript'
        //@ts-ignore
        return hljs.highlight(code, {language}).value
    }
}))


marked.use({
    renderer: {
        text(text: string): string {
            return text
        }
    },
})

function codeBlockLineNumber(observe: MutationObserver) {
    let container = document.getElementsByClassName("markdown-body")[0]
    new Promise((resolve, reject) => {
        resolve("")
    }).then(function () {
        container.setAttribute("data-op-type", "text")
        for (let childrenKey in container.children) {
            console.log(container.children[childrenKey] instanceof HTMLElement)
            if (container.children[childrenKey] instanceof HTMLElement) {
                container.children[childrenKey].setAttribute("data-op-type", "text")
            }
        }
        let codeEle = container.querySelectorAll("code")
        codeEle.forEach((value, key) => {
            const parent = value.parentElement
            if (Object.is(parent?.nodeName.toLowerCase(), "pre")) {
                value.className = "block_code"
                try {
                    value.innerHTML = "<ul><li>" + value.innerHTML.replace(/\n/g, "\n</li><li>") + "</li></ul>";
                    let ul = value.getElementsByTagName('ul')[0];
                    if (ul.getElementsByTagName("li").length > 1) {
                        const el = ul.lastElementChild
                        if (el) {
                            ul.removeChild(el);
                            observe.disconnect()
                        }
                    }
                    let style = ul?.style
                    if (style) {
                        style.cssText = `margin-left:${
                            ("" + ul?.getElementsByTagName("li").length).length * 0.3
                        }em`
                    }
                } catch (e) {
                }
            }
        })
    })

}


function renderMarkdownToHtml(markdown: string): Promise<string> {
    const mark: any = marked.parse(markdown)
    return mark
}

export {
    renderMarkdownToHtml,
    codeBlockLineNumber
}