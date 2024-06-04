import {App} from 'vue'

let timer: any = null
let scroll: any = null
export default (app: App<Element>): void => {
    app.directive('csdnScrollFooter', {
        mounted(el) {
            let elAddress: number = 0
            const otherTop = el.offsetHeight
            const otherWidth = el.offsetWidth
            // console.log(otherWidth)
            let top:any = undefined
            document.documentElement.style.setProperty("--footer_width",otherWidth+"px")
            scroll = function (e: Event) {
                e.stopPropagation()
                e.preventDefault()
                clearTimeout(timer)
                if (!top){
                    top =  (document.getElementById("markdown-body")?.offsetHeight as any)
                    - (document.getElementById("nono")?.offsetHeight as any) - el.offsetHeight - 800
                }
                const target: any = e.target
                timer = setTimeout(function () {
                    const scrollTop = target.scrollTop
                    // console.log(top,scrollTop)
                    if (scrollTop < top) {
                        el.classList.add("footer_position")
                    } else {
                        el.classList.remove("footer_position")
                    }
                }, 100)
            }
            document.getElementById("global_scrollbar")?.getElementsByClassName("n-scrollbar-container")[0]?.addEventListener("scroll", scroll)
            // document.getElementById("app")?.addEventListener("scroll", scroll)
        },
        unmounted(el) {
            document.getElementById("global_scrollbar")?.getElementsByClassName("n-scrollbar-container")[0]?.removeEventListener("scroll", scroll)

            // document.getElementById("app")?.removeEventListener("scroll", scroll)
        }
    })
}