<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>复职管理</span>
        </span>
        </template>
        <Resignation/>
      </el-tab-pane>
      <Foot/>
    </el-tabs>
  </div>
</template>

<script setup>
//请求方法
import {getCurrentInstance, ref} from "vue";
//复职组件
import Resignation from "@/components/department/Resignation.vue"
//部门信息
import {useDepartment} from "@/stores/department.js"
//通知组件
//获取用户信息
import {useUser} from '@/stores/user.js'

const departmentStore = useDepartment();
const {proxy} = getCurrentInstance();
const userStore = useUser();
const user = userStore.getUser();
//头部面包屑
const activeName = ref('first')
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>