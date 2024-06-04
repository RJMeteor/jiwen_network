import {App} from 'vue'

let timer: any = null
let scroll: any = null
export default (app: App<Element>): void => {
    app.directive('csdnScrollHeader', {
        mounted(el) {
            let elAddress: number = 0
            const otherTop = el.offsetHeight
            const otherWidth = el.otherWidth
            const contentDom = document.getElementById("global_scrollbar")?.getElementsByClassName("n-layout-content")[0]
            document.documentElement.style.setProperty('--content_margin', otherTop+"px")
            scroll = function (e: Event) {
                e.stopPropagation()
                e.preventDefault()
                clearTimeout(timer)
                const target: any = e.target
                timer = setTimeout(function () {
                    const scrollTop = target.scrollTop
                    if (scrollTop > otherTop) {
                        el.classList.add("header_position")
                        contentDom?.classList.add("content_margin")
                    } else {
                        el.classList.remove("header_position")
                        contentDom?.classList.remove("content_margin")
                    }
                }, 100)
            }
            document.getElementById("global_scrollbar")?.getElementsByClassName("n-scrollbar-container")[0]?.addEventListener("scroll", scroll)
        },
        unmounted(el) {
            document.getElementById("global_scrollbar")?.getElementsByClassName("n-scrollbar-container")[0]?.removeEventListener("scroll", scroll)
        }
    })
}