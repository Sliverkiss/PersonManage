<template>
  <div class="app_container">
    <div class="common-layout">
      <el-container>
        <el-header>
            <Header/>
        </el-header>
        <el-container>
          <el-aside width="250px">
            <Aside/>
          </el-aside>
          <el-main style="background-color: #F5F5F5">
            <router-view/>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script setup lang="ts">

import {reactive} from "vue";
import request from "../request.js";
import Header from '../components/Header.vue'
import Aside from '../components/Aside.vue'
import {ref} from 'vue'
import Foot from '../components/footer/Foot.vue'

const state = reactive({
  tableData: []
})
const isCollapse = ref(true)
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
// //请求获取后端数据
// const load = () => {
//   request.get('/user').then(res => {
//     // {"code":"200","msg":"","data":null}
//     state.tableData = res.data
//   })
// }
// //初始化获取数据
// load()


//编辑数据


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