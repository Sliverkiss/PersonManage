<template>
  <el-container>
    <el-header>
      <Header/>
    </el-header>
    <el-container>
      <el-aside width="230px">
        <el-scrollbar max-height="750px" height="750px">
          <el-menu
              default-active="$route.path" router
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
          >
            <!--            <div style="height:160px;background-color:#F5F5F5" class="p-3 demo-type text-center">-->
            <!--              <div>-->
            <!--                <el-avatar :size="70"-->
            <!--                           src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"-->
            <!--                />-->
            <!--              </div>-->
            <!--              <div class="mt-3 text-dual" style="font-weight: bold;font-size:17px">{{ user.username }}-->
            <!--                <Setting class="ms-1" width="18px"/>-->
            <!--                <Expand class="ms-1" width="18px"/>-->
            <!--              </div>-->
            <!--            </div>-->
            <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">功能</p>
            <TreeMenu :treeData="state.treeData"/>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-main style="background-color: #F5F5F5">
        <el-scrollbar max-height="680px" height="680px">
          <router-view/>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import {useRouteStore} from "../stores/myRoute.js";
import Header from '../components/Header.vue'
import {useRouter} from 'vue-router'
import {computed} from "@vue/reactivity";
import {onBeforeMount, reactive, ref} from "vue";
import {useUser} from '../stores/user.js'
import TreeMenu from "../components/TreeMenu.vue";
import {GetDynamicRoutes} from '../api/home'
// import {activeRouter} from "../router/permission.js";
const router = useRouter()
const useStore = useUser();
const user = useStore.getUser();
const state = reactive({
  treeData: []
});
const store = useRouteStore()
const test = ref("测试");
// 动态路由表
const routes = computed(() => store.routes)
// 初始化时加载动态路由
onBeforeMount(() => {
  GetDynamicRoutes(user.id).then(res => {
    state.treeData = res.data.permissions;

  })
})

</script>
<style>
/* 手动配置全局样式 */
html,
body,
.app_container,
.el-container {
  padding: 0;
  margin: 0;
  height: calc(100vh - 60px);
}

/* 背景颜色 */
a {
  text-decoration: none;
}
</style>