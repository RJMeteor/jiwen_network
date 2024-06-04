import { useMessage } from 'naive-ui'
import { defineComponent } from 'vue'

// content
export default defineComponent({
    setup () {
        const message = useMessage()
        window.$message = message
    }
})