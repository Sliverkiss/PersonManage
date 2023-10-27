<template>
  <div class="align:center">
    <el-card class="fw-bold text-muted notice-card">
      <template #header>
        <div class="row">
          <div class="col-sm-12 col-md-12">
            <el-select v-model="state.assessId" style="width:250px;;margin-left: 10px" placeholder="请选择考核主题">
              <el-option v-for="set in state.setList" :key="set.id" :label="set.title" :value="set.id"/>
            </el-select>
            <el-button type="primary" class="ms-2" @Click="genderPieChart">
              <el-icon>
                <Search/>
              </el-icon>
              搜索
            </el-button>
          </div>
        </div>
      </template>
      <e-charts :option="options" class="chart"/>
    </el-card>
  </div>
</template>

<script setup>
import ECharts from "vue-echarts";
import {computed, onMounted, reactive, ref, toRaw} from "vue";
import {assessPointBar} from "@/api/report/personal.js";
import {getTitles} from "@/api/assess/set.js";

const data = ref([])
//数据
const state = reactive({
  assessId: 1,
  setList: []
})
//动态响应数据
const options = computed(() => {
  return {
    title: {
      text: '各部门绩效考核平均分统计',
    },
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
  assessPointBar(state.assessId).then((res) => {
    data.value = res.data;
  });
}
const getSet = () => {
  getTitles().then((res) => {
    state.setList = res.data;
    console.log(toRaw(state.setList))
  });
}
//定时更新数据
setInterval(() => {
  genderPieChart();
}, 10000);
onMounted(() => {
  genderPieChart();
  getSet()
})
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