<template>
  <div class="">
    <el-scrollbar max-height="1000px">
      <el-menu
          default-active="$route.path" router
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
      >
        <div style="height:160px;background-color:#F5F5F5" class="p-3 demo-type text-center">
          <!--          <div>-->
          <!--            <el-avatar :size="70"-->
          <!--                       src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"-->
          <!--            />-->
          <!--          </div>-->
          <div class="mt-3 text-dual" style="font-weight: bold;font-size:17px">{{ userStore.user.username }}
            <Setting class="ms-1" width="18px"/>
            <Expand class="ms-1" width="18px"/>
          </div>
        </div>
        <div>
          <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">控制台</p>
          <el-menu-item index="/index/text" @click="asideVisible=true">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            <span>用户中心</span>
          </el-menu-item>
          <el-menu-item index="/text" v-show="userStore.getUser().role==1" @click="asideVisible=false">
            <el-icon>
              <Platform/>
            </el-icon>
            <span>系统后台</span>
          </el-menu-item>
          <userAside v-show="asideVisible"/>
          <consoleAside v-show="!asideVisible"/>
        </div>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import {Setting,} from '@element-plus/icons-vue'

import {useUser} from '@/stores/user.js'
import {useAside} from '@/stores/aside.js'
import {useRouter} from "vue-router";
import ConsoleAside from "@/components/aside/consoleAside.vue";
import {ref} from "vue";

const userStore = useUser();
const asideStore = useAside();

const asideVisible = ref(true);

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
const router = useRouter();

</script>

<style scoped>
.asidecolor{
  background-color: #F5F5F5;
}
</style>