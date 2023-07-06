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
          <div>
            <el-avatar :size="70"
                       src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
          </div>
          <div class="mt-3 text-dual" style="font-weight: bold;font-size:17px">{{userStore.user.username}}
            <Setting class="ms-1" width="18px"/>
            <Expand class="ms-1" width="18px"/>
          </div>
        </div>
        <userAside v-show="userStore.user.role==0?true:false" />
        <consoleAside v-show="userStore.user.role==1?true:false"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting, DArrowRight,
} from '@element-plus/icons-vue'
import {useUser} from '@/stores/user.js'
const userStore =useUser();
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
import {useRouter} from "vue-router";
import ConsoleAside from "@/components/aside/consoleAside.vue";
import userAside from '@/components/aside/userAside.vue'
const router = useRouter();

</script>

<style scoped>
.asidecolor{
  background-color: #F5F5F5;
}
</style>