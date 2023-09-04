<template>
  <div class="align:center">
    <el-card class="fw-bold text-muted notice-card">
      <template #header>
        <div>
          <span>员工学历统计</span>
        </div>
      </template>
      <e-charts :option="options" class="chart"/>
    </el-card>
  </div>
</template>

<script setup>
import ECharts from "vue-echarts";
import {computed, onMounted, ref} from "vue";
import {TiptopDegreePie} from "@/api/report/personal.js";

const data = ref([])
//动态响应数据
const options = computed(() => {
  return {
    toolbox: {// 工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
      feature: {
        saveAsImage: {},//导出图片
        restore: {},//重置
      }
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        //百分比
        label: {
          normal: {
            show: true,
            formatter: '{b}: {c}({d}%)' //自定义显示格式(b:name, c:value, d:百分比)
          }
        },
        data: data.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
})
const getChart = () => {
  TiptopDegreePie().then((res) => {
    data.value = res.data;
  });
}
//定时更新数据
setInterval(() => {
  getChart();
}, 10000);

onMounted(() => {
  getChart();
})

</script>
<script>
export default {
  name: "TiptopDegree"
}
</script>
<style scoped>
.chart {
  height: 300px;
  width: 480px;
}

.notice-card {
  border-radius: 0.875rem 1rem;
  /*font-size: 15px;*/
}
</style>