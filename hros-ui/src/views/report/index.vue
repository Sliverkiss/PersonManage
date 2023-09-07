<template>
  <div class="p-3" v-if="user.employeeVo.workState!='离职'">
    <el-row :gutter="12">
      <el-col :span="12">
        <Gender/>
      </el-col>
      <el-col :span="12">
        <WorkState/>
      </el-col>
      <el-col :span="12" class="mt-3">
        <TiptopDegree/>
      </el-col>
      <el-col :span="12" class="mt-3">
        <EngageForm/>
      </el-col>
      <el-col :span="12" class="mt-3">
        <Politic/>
      </el-col>
    </el-row>
    <el-row :gutter="24" class="mt-3">
      <el-col :span="24">
        <DepartmentBar/>
      </el-col>
    </el-row>
    <Foot/>
  </div>
  <div v-else>无权限</div>
</template>

<script setup>
import {ref} from "vue";
import TiptopDegree from "@/components/report/TiptopDegree.vue";
import DepartmentBar from '@/components/report/Department.vue';
import {useUser} from '@/stores/user.js'

const activeName = ref('first')
const userStore = useUser();
const user = userStore.getUser();
</script>
<style scoped>
.chart {
  height: 400px;
  width: 400px;
}

.notice-card {
  border-radius: 0.875rem 1rem;
  /*font-size: 15px;*/
}
</style>