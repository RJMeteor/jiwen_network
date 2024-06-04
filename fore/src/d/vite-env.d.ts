/// <reference types="vite/client" />
declare module "*.vue" {
    import { DefineComponent } from "vue"
    const component: DefineComponent<{}, {}, any>
    export default component
}

interface ImportMetaEnv {
    readonly BLOG_BASE_URL:string;
    readonly BLOG_WEBSOCKET_URL:string
    readonly BLOG_TOKEN_NAME:string;
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}


