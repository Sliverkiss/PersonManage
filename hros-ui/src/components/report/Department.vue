<template>
  <div class="align:center">
    <el-card class="fw-bold text-muted notice-card">
      <template #header>
        <div>
          <span>各部门员工人数</span>
        </div>
      </template>
      <e-charts :option="options" class="chart"/>
    </el-card>
  </div>
</template>

<script setup>
import ECharts from "vue-echarts";
import {computed, onMounted, ref} from "vue";
import {departmentBar} from "@/api/report/personal.js";

const data = ref([])
//动态响应数据
const options = computed(() => {
  return {
    xAxis: {
      type: 'value'
    },
    toolbox: {// 工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
      feature: {
        saveAsImage: {},//导出图片
        restore: {},//重置
      }
    },
    label: { // 柱状图 内部 显示数值
      show: true,
    },
    yAxis: {
      type: 'category',
      data: data.value.map(item => item.name)
    },
    series: [
      {
        data: data.value.map(item => item.value),
        type: 'bar'
      }
    ]
  }
})
const genderPieChart = () => {
  departmentBar().then((res) => {
    data.value = res.data;
  });
}
//定时更新数据
setInterval(() => {
  genderPieChart();
}, 10000);
onMounted(() => {
  genderPieChart();
})
</script>
<script>
export default {
  name: "DepartmentBar"
}
</script>
<style scoped>
.chart {
  height: 500px;
  width: 1050px;
}

.notice-card {
  border-radius: 0.875rem 1rem;
  /*font-size: 15px;*/
}
</style>