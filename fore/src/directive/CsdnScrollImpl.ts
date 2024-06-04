import {App} from 'vue'


let timer: any = null
let scroll: any = null
export default (app: App<Element>): void => {
    app.directive('csdnScroll', {
        mounted(el){
            let elAddress: number = 0
            const other: any = document.getElementById("header")
            const otherTop = other?.offsetHeight + other?.offsetTop * 2
            const innerHeight = window.innerHeight
            let offsetTop: any = null
            let offsetWidth: any = null
            let offsetHeight: any = null
            scroll = function (e: Event) {
                console.log("gggg")
                e.stopPropagation()
                e.preventDefault()
                clearTimeout(timer)
                const target: any = e.target
                timer = setTimeout(function () {
                    if (!elAddress && !offsetTop && !offsetWidth) {
                        offsetHeight = el.offsetHeight
                        elAddress = offsetHeight + el.offsetTop + otherTop
                        offsetTop = el.offsetTop
                        offsetWidth = el.offsetWidth
                    }
                    const scrollTop = target.scrollTop
                    if (elAddress >= (scrollTop + otherTop + offsetTop)) {
                        el.style.cssText = `position:"";bottom:0px;width:${offsetWidth}px;`
                    }
                    if ((elAddress + offsetTop) < innerHeight && (otherTop + offsetTop) < scrollTop) {
                        el.style.cssText = `position:fixed;top:${offsetTop + otherTop}px;width:${offsetWidth}px;`
                    }
                    if (elAddress >= innerHeight && (elAddress - innerHeight) <= scrollTop) {
                        el.style.cssText = `position:fixed;bottom:${offsetTop}px;width:${offsetWidth}px;`
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