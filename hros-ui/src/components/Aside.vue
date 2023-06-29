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
          <div class="mt-3 text-dual" style="font-weight: bold;font-size:17px">用户名
            <Setting class="ms-1" width="18px"/>
            <Expand class="ms-1" width="18px"/>
          </div>
        </div>
        <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">控制台</p>
        <el-menu-item index="/index/home">
          <el-icon>
            <HomeFilled/>
          </el-icon>
          <span>用户中心</span>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon>
            <Platform/>
          </el-icon>
          <span>系统后台</span>
        </el-menu-item>
        <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">功能</p>
        <el-sub-menu index="3">
          <template #title >
            <el-icon>
              <location/>
            </el-icon>
            <span >打卡考勤</span>
          </template>
          <div class="asidecolor">
            <el-menu-item  index="3-1">打卡签到/下班</el-menu-item>
            <el-menu-item   index="3-2">个人考勤信息管理</el-menu-item>
          </div>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title>
            <el-icon><AlarmClock /></el-icon>
            <span>请假管理</span>
          </template>
          <div class="asidecolor">
            <el-menu-item index="4-1">申请请假</el-menu-item>
            <el-menu-item index="4-2">个人请假信息管理</el-menu-item>
          </div>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
            <el-icon><Medal /></el-icon>
            <span>职务管理</span>
          </template>
          <div class="asidecolor">
            <el-menu-item index="5-1">申请离职</el-menu-item>
            <el-menu-item index="5-2">个人职务信息管理</el-menu-item>
          </div>
        </el-sub-menu>
        <el-sub-menu index="6">
          <template #title>
            <el-icon><SwitchFilled /></el-icon>
            <span>培训管理</span>
          </template>
          <div class="asidecolor">
            <el-menu-item index="6-1">申请培训</el-menu-item>
            <el-menu-item index="6-2">个人培训信息管理</el-menu-item>
          </div>
        </el-sub-menu>
        <el-sub-menu index="7">
          <template #title>
            <el-icon><Coin /></el-icon>
            <span>薪资管理</span>
          </template>
          <div class="asidecolor">
            <el-menu-item index="7-1">绩效考核</el-menu-item>
            <el-menu-item index="6-2">个人薪资信息管理</el-menu-item>
          </div>
        </el-sub-menu>
        <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">其它</p>
        <el-menu-item index="8">
          <el-icon>
            <setting/>
          </el-icon>
          <span>个人资料管理</span>
        </el-menu-item>
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

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
import {useRouter} from "vue-router";
const router = useRouter();

</script>

<style scoped>
.asidecolor{
  background-color: #F5F5F5;
}
</style>