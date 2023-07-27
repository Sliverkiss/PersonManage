<template>
  <div>
    <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">功能</p>
    <el-sub-menu index="3">
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>薪资查询</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="3-1">打卡签到/下班</el-menu-item>
        <el-menu-item index="3-2">个人考勤信息管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="4">
      <template #title>
        <el-icon>
          <AlarmClock/>
        </el-icon>
        <span>合同管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="4-1">申请请假</el-menu-item>
        <el-menu-item index="4-2">个人请假信息管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="5">
      <template #title>
        <el-icon>
          <Medal/>
        </el-icon>
        <span>岗位管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="5-1">申请调岗</el-menu-item>
        <el-menu-item index="5-1">申请离职</el-menu-item>
        <el-menu-item index="5-2">申请复职</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="6">
      <template #title>
        <el-icon>
          <SwitchFilled/>
        </el-icon>
        <span>培训管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="6-2">个人培训信息管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="7">
      <template #title>
        <el-icon>
          <Coin/>
        </el-icon>
        <span>绩效考核</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="7-1">绩效考核</el-menu-item>
        <el-menu-item index="6-2">个人奖惩信息管理</el-menu-item>
      </div>
    </el-sub-menu>
    <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">其它</p>
    <el-menu-item index="/index/userInfo">
      <el-icon>
        <setting/>
      </el-icon>
      <span>个人资料管理</span>
    </el-menu-item>
  </div>
</template>

<script setup>
import {useUser} from '@/stores/user.js'

const userStore = useUser();
</script>

<style scoped>

</style>