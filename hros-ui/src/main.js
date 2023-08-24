import {createApp} from 'vue'
//import './style.css'
import App from './App.vue'
import router from './router'
//引入elementUI
import 'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
import './assets/css/global.css'
//引入Bootstrap5
import './bootstrap/js/bootstrap.bundle'
import './bootstrap/css/bootstrap.min.css'
import * as ElIcons from '@element-plus/icons-vue'
import store from './stores'


const app = createApp(App);

for (const iconName in ElIcons) {
    app.component(iconName, ElIcons[iconName])
}
app.use(router).use(store).use(ElementPlus).mount('#app')
