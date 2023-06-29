import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()]
        }),
        Components({
            resolvers: [ElementPlusResolver()]
        })
    ],
    //是否自动在浏览器打开
    open: true,
    //是否开启https
    https: false,
    //服务器渲染
    ssr: false,
    //在生产中服务时的基本公共路径
    base: './',
    //与根相关的目录
    outDir: 'dist',
    resolve: {
        alias: {
            '@': resolve(__dirname, '.', 'src'),
        }
    },
    //反向代理
    server: {
        port: 8080,
        host: "0.0.0.0",
        open: true,
        https: false,
        proxy: {
            '/api': {
                target: 'http://localhost',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
        },
    },
})
