import {Component, h} from "vue";
import {NIcon} from "naive-ui";

/**
 * 图标vnode
 * @param icon
 */
function renderIcon(icon: Component) {
    return () => h(NIcon, null, {default: () => h(icon)})
}

const color = ["tertiary", "primary", "info", "warning", "error"]

/**
 * 随机颜色
 */
function getColor() {
    return color[Math.ceil(Math.random() * color.length)]
}

/**
 * 放入微队列
 * @param callback
 */
function microqueueIn(callback: () => void) {
    new Promise(resolve => {
        resolve("")
    }).then(function () {
        callback && callback()
    })
}

export {
    renderIcon,
    getColor,
    microqueueIn
}