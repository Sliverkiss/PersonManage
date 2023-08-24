<template>
  <div style="height:80px;line-height:60px;display:flex">
    <div style="width:200px;padding-left: 37px;" @click="router.push('/index')">
      <OfficeBuilding class="m-1 " width="30px" style="font-weight:bold; color:dodgerblue"/>
      <el-link style="font-weight:bold;font-size:20px;">Cheerio</el-link>
    </div>
    <div style="flex: 1"></div>
    <div style="width:100px">
      <el-dropdown>
        <span class="el-dropdown-link mt-3 p-2  text-center">
            {{userStore.user.username}}<ArrowDown width="15px"/>
          </span>
        <template v-slot:dropdown>
          <el-dropdown-menu>
            <div class="px-2 py-3 bg-light rounded-top">
              <h5 class="h6 text-center mb-0">{{ userStore.user.username + treeData }}</h5>
            </div>
            <el-dropdown-item>个人资料
              <el-icon class="ms-4">
                <UserFilled/>
              </el-icon>
            </el-dropdown-item>
            <el-dropdown-item @click="logout">注销登录
              <el-icon class="ms-4">
                <SwitchButton/>
              </el-icon>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import {useUser} from '@/stores/user.js'
import {useRouter} from "vue-router";

const router = useRouter();
const userStore = useUser();

const logout = () => {
  userStore.setUser('');
  router.push('/login')
}

const props = defineProps(['treeData'])
console.log(props.treeData)
</script>

<style>
</style>