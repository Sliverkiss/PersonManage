<template>
  <div>
    <e-charts :option="options" class="chart"/>
  </div>
</template>

<script setup>
import ECharts from "vue-echarts";
import {computed, ref} from "vue";

const data = ref([
  {value: 50, name: "a"},
  {value: 40, name: "b"},
  {value: 30, name: "c"},
  {value: 55, name: "d"},
  {value: 120, name: "e"},
])
//定时更新数据
setInterval(() => {
  data.value = data.value.map(item => ({
    ...item,
  }));
}, 10000);

//动态响应数据
const options = computed(() => {
  return {
    xAxis: {
      type: 'category',
      data: data.value.map(data => data.name)
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: data.value.map(data => data.value),
        type: 'line'
      }
    ]
  }
})
</script>
<script>
export default {
  name: "PersonalCount"
}
</script>
<style scoped>
.chart {
  height: 400px;
  width: 400px;
}
</style>