import {nextTick, ref} from "vue";
import {codeBlockLineNumber} from "@/util/marked";
import tocbot from "tocbot";
import {domainToASCII} from "url";


const processCallback: Array<(mutations: MutationRecord[], observe: MutationObserver) => void> = []

function listener() {
    const observer = new MutationObserver(function (mutations, observe) {
        codeBlockLineNumber(observe)
        for (let processCallbackElement of processCallback) {
            processCallbackElement(mutations, observe)
        }
        observe.disconnect()
    });
    observer.observe(document.getElementsByClassName("markdown-body")[0], {
        childList: true
    })
}

function tocbotInit() {
    nextTick(function () {
        tocbot.init({
            tocSelector: '#toc23',
            contentSelector: '#markdown-body',
            headingSelector: 'h1, h2, h3, h4, h5, h6',
            scrollSmooth: true,
            scrollSmoothDuration: 1000,
            scrollContainer: "#renjia_01",
            // collapseDepth: 7,
            onClick(e: any) {
                e.stopPropagation()
                e.preventDefault()
                const id = e.target.getAttribute("href").slice(
                    1
                )
                document.getElementById(id)?.scrollIntoView({
                    behavior: 'smooth',
                    block: 'center',
                })
            }
        })
    })
}

function tocObserver(): any {
    let timer: any = null
    const observer = new MutationObserver(function (mutations, observe) {
        // clearTimeout(timer)
        // timer = setTimeout(function () {
        //     const dom = document.querySelector(".toc-link.is-active-link")
        //     dom?.scrollIntoView({
        //         behavior: 'smooth',
        //         block: 'center',
        //     })
        // }, 500)
    });
    return observer
}

export {
    processCallback,
    tocbotInit,
    tocObserver,
    listener
}