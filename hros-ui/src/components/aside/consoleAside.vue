<template>
  <div>
    <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">功能</p>
    <el-sub-menu index='/console/employee'>
      <template #title>
        <el-icon>
          <location/>
        </el-icon>
        <span>员工管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="/console/employee/info">员工资料管理</el-menu-item>
        <el-menu-item index="/console/employee/contract"> 员工合同管理</el-menu-item>
        <el-menu-item index="/console/employee/salary">员工薪资管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="/console/department">
      <template #title>
        <el-icon>
          <AlarmClock/>
        </el-icon>
        <span>岗位管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="/console/department/info">部门岗位管理</el-menu-item>
        <el-menu-item index="/console/department/transfer">调岗审核管理</el-menu-item>
        <el-menu-item index="/console/department/reinstatement">离职复职管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="/console/indicator">
      <template #title>
        <el-icon>
          <SwitchFilled/>
        </el-icon>
        <span>绩效考核管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="/console/indicator/info">考核指标管理</el-menu-item>
        <el-menu-item index="/console/indicator/performance">绩效考核列表</el-menu-item>
      </div>
    </el-sub-menu>
    <el-sub-menu index="/console/trainning">
      <template #title>
        <el-icon>
          <SwitchFilled/>
        </el-icon>
        <span>培训管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="/console/trainning/info">培训计划管理</el-menu-item>
        <el-menu-item index="/console/trainning/record">培训计划管理</el-menu-item>
      </div>
    </el-sub-menu>
    <el-menu-item index="/i">
      <el-icon>
        <HomeFilled/>
      </el-icon>
      <span>统计分析</span>
    </el-menu-item>
    <p class="m-0 ms-4 mt-3" style="font-size:13px;color:#6C6E72">其它</p>
    <el-sub-menu index="8">
      <template #title>
        <el-icon>
          <setting/>
        </el-icon>
        <span>系统数据管理</span>
      </template>
      <div class="asidecolor">
        <el-menu-item index="5-1">公告数据管理</el-menu-item>
        <el-menu-item index="5-1">用户数据管理</el-menu-item>
        <el-menu-item index="5-2">数据备份</el-menu-item>
      </div>
    </el-sub-menu>
  </div>
</template>

<script setup>

</script>

<style scoped>

</style>