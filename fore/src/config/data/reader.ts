import {ref} from "vue";
import {tocbotInit} from "@/util/observer";

const favoriteShow = ref<boolean>(false)
const commentShow = ref<boolean>(false)



export {
    favoriteShow,
    commentShow,
}