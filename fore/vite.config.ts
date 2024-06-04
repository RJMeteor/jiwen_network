import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

import Components from 'unplugin-vue-components/vite'
import {NaiveUiResolver} from 'unplugin-vue-components/resolvers'
import vueJsx from "@vitejs/plugin-vue-jsx"


// https://vitejs.dev/config/
export default defineConfig({
    server: {
        port: 9005,
    },
    plugins: [
        vue(),
        vueJsx(),
        Components({
            resolvers: [NaiveUiResolver()],
        })
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
    },
    envDir: "src/config",
    // 以 envPrefix 开头的环境变量会通过 import.meta.env 暴露在你的客户端源码中。
    envPrefix:"BLOG_",
    build: {
        minify: 'terser',
        terserOptions: {
            compress: {
                drop_console: true,
                drop_debugger: true,
            },
        },
    },
})