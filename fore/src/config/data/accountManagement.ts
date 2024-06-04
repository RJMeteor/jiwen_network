import {computed, ref} from "vue";
import {UploadFileInfo} from "naive-ui";

const updateDisabled = ref(false)
const formRef = ref(null)
const previewImageUrl = ref('')
const showModal = ref(false)
const formDisable = ref<boolean>(true)
function handlePreview(file: UploadFileInfo) {
    const {url} = file
    previewImageUrl.value = url as string
    showModal.value = true
}

export {
    updateDisabled,
    formRef,
    previewImageUrl,
    showModal,
    formDisable,
    handlePreview,
}