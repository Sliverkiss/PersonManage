<template>
  <div class="container">
    <div class="row" width="400px">
      <div class="col-md-12 mb-4">
        <div class="fs-lg p-4 bg-earth text-white text-center rounded">
          <div class="mb-2">
            <OfficeBuilding class="m-1 " width="50px" style="font-weight:bold; color:#E5EAF3"/>
          </div>
          <h4 class="mb-1 fs-5 fw-bold">
            欢迎来到 Cheerio !
          </h4>
          <p class=" fw-medium text-white mb-0 pt-1" style="font-size:14px">
            你的Cheerio，不仅仅是<strong>Cheerio</strong>
          </p>
        </div>
      </div>
      <!--      <div class="col-6 col-xl-3 card-hover">-->
      <!--        <a class="a-block mt-3 p-4 fw-bold">-->
      <!--          <div-->
      <!--              class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">-->
      <!--            <div class="d-none d-sm-block">-->
      <!--              <location class="m-1 " width="40px" style="font-weight:bold; color: #0284c7"/>-->
      <!--            </div>-->
      <!--            <div class="text-end">-->
      <!--              <div class="fs-3" style="color: #0284c7">5</div>-->
      <!--              <div class="a-fs  text-uppercase text-muted">本月考勤情况</div>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </a>-->
      <!--      </div>-->
      <!--      <div class="col-6 col-xl-3 card-hover">-->
      <!--        <a class="a-block mt-3 p-4 fw-bold">-->
      <!--          <div-->
      <!--              class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">-->
      <!--            <div class="d-none d-sm-block">-->
      <!--              <AlarmClock class="m-1 " width="40px" style="font-weight:bold;color: #5ccea7"/>-->
      <!--            </div>-->
      <!--            <div class="text-end">-->
      <!--              <div class="fs-3 text-earth" style="color: #5ccea7 ">24</div>-->
      <!--              <div class="a-fs text-uppercase text-muted">本月请假次数</div>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </a>-->
      <!--      </div>-->
      <!--      <div class="col-6 col-xl-3 card-hover">-->
      <!--        <a class="a-block mt-3 p-4 fw-bold ">-->
      <!--          <div-->
      <!--              class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">-->
      <!--            <div class="d-none d-sm-block">-->
      <!--              <Coin class="m-1 " width="40px" style="font-weight:bold;color: #8f55f2"/>-->
      <!--            </div>-->
      <!--            <div class="text-end">-->
      <!--              <div class="fs-3" style="color: #8f55f2">57</div>-->
      <!--              <div class="a-fs text-uppercase text-muted">绩效考核得分</div>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </a>-->
      <!--      </div>-->
      <!--      <div class="col-6 col-xl-3 card-hover">-->
      <!--        <a class="a-block mt-3 p-4 fw-bold">-->
      <!--          <div-->
      <!--              class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">-->
      <!--            <div class="d-none d-sm-block">-->
      <!--              <OfficeBuilding class="m-1 " width="40px" style="font-weight:bold;color: #5aced3"/>-->
      <!--            </div>-->
      <!--            <div class="text-end">-->
      <!--              <div class="fs-3 " style="color: #5aced3">0</div>-->
      <!--              <div class="a-fs text-uppercase text-muted">本月培训计划</div>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </a>-->
      <!--      </div>-->
    </div>
    <div class="row">
      <div class="col-lg-8 mb-2 mt-4 ">
        <el-card class="notice-card">
          <template #header>
            <div class="text-muted fw-bold">公告通知</div>
          </template>
          <el-scrollbar max-height="200px">
            <div v-for="notice in state.noticeList" :key="notice.id" class="text item">
              <div class="tip custom-block">
                <el-link style="font-size:15px" @click="openDialog(notice)"
                         class="custom-block-title fw-bold text-uppercase ">
                  {{ notice.title }}
                </el-link>
                <p style="font-size:13px" class="text-muted">{{ notice.createDate }}</p>
              </div>
            </div>
          </el-scrollbar>
        </el-card>
      </div>
      <div class="col-lg-4 mb-2 mt-4 " v-if="user.employeeVo.workState!='离职'">
        <el-card class="box-card notice-card">
          <template #header>
            <div class="text-center">

              <div class="" style="font-size:14px">
                <div class="fw-semibold fs-6 fw-bold mb-1 text-uppercase text-muted">
                  <div>
                    <el-avatar :size="70"
                               src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    />
                  </div>
                  <Male v-if="user.employeeVo.gender='男'" class="m-1 text-primary" width="20px"/>
                  <Female v-else-if="user.employeeVo.gender='女'" class="m-1 text-danger" width="20px"/>
                  <View v-else class="m-1 text-success" width="20px"/>
                  {{ user.employeeVo.name }} <span style="font-size:8px">UID: {{ user.employeeId }}</span>
                </div>
                <div class="fw-bold text-uppercase text-muted">入职日期 - {{ user?.employeeVo?.hireDate }}</div>
                <!--                  <div class="fw-bold text-uppercase text-muted">共有 100 个配额 | 已使用 ： 1 个</div>-->
              </div>
            </div>
          </template>
          <div class="block-content">
            <div class="row items-push text-center">
              <div class="col-6">
                <div class="mb-1">
                  <User class="m-1 " width="30px"/>
                </div>
                <div class="fs-sm fw-medium text-muted">{{ user?.employeeVo?.departmentName }}</div>
              </div>
              <div class="col-6">
                <div class="mb-1">
                  <Medal class="m-1 " width="30px"/>
                </div>
                <div class="fs-sm fw-medium text-muted">{{ user?.employeeVo?.post }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
  <el-drawer
      v-model="tableDialog"
      title="公告详情"
      direction="rtl"
      size="50%">
    <template #header="{ close, titleId, titleClass }">
      <div>
        <h4 :id="titleId" class="titleClass text-center">{{ state.tableData.title }}</h4>
        <div class="text-center">{{ state.tableData.director }}&nbsp;&nbsp;{{ state.tableData.createDate }}</div>
      </div>
    </template>
    <div v-html="state.tableData.content"></div>
  </el-drawer>
  <Foot/>
</template>

<script setup>
import {useUser} from '@/stores/user.js'
import {onMounted, reactive, ref, toRaw} from "vue";
import {getNoticeList} from "@/api/system/notice.js";

const userStore = useUser();
const user = userStore.getUser();

const state = reactive({
  tableData: {},
  noticeList: []
})
//抽屉
const tableDialog = ref(false);

const openDialog = (notice) => {
  tableDialog.value = true;
  state.tableData = JSON.parse(JSON.stringify(notice));
  console.log(toRaw(state.tableData))
}
const getNotices = () => {
  getNoticeList().then(res => {
    state.noticeList = res.data;
    console.log(toRaw(state.noticeList))
  })
}

onMounted(() => {
  getNotices();
})
</script>

<style scoped>
.bg-earth {
  background-color: #32a67f !important;
}

.a-block {
  display: block;
  color: #3e444a;
  transition: all .12s ease-out;
  background-color: #fff;
  box-shadow: 0 1px 2px rgba(220, 225, 232, .5), 0 1px 2px rgba(220, 225, 232, .5);
  text-align: right !important;
  text-decoration: none;
  border-radius: 0.875rem 1rem;
}

.a-fs {
  font-size: 14px;
}

.card-hover :hover {
  background-color: #E5EAF3
}

.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}

.custom-block.tip {
  padding: 8px 16px;
  background-color: var(--block-tip-bg-color);
  border-radius: 4px;
  border-left: 5px solid var(--el-color-primary);
  margin: 10px 0;
}
</style>