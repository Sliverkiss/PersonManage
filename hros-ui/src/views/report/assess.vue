<template>
  <div class="p-3" v-if="user.employeeVo.workState!='离职'">
    <el-row :gutter="24" class="mt-3">
      <el-col :span="24">
        <AssessAvg/>
      </el-col>
    </el-row>
    <Foot/>
  </div>
  <div v-else>无权限</div>
</template>

<script setup>
import {ref} from "vue";
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