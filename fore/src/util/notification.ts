import { useNotification} from 'naive-ui'
import {defineComponent} from 'vue'

export default defineComponent({
    setup() {
        const notification = useNotification()
        window.$notification = notification
    }
})