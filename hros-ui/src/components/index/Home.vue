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
        <div class="col-6 col-xl-3 card-hover">
          <a class="a-block mt-3 p-4 fw-bold">
            <div
                class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">
              <div class="d-none d-sm-block">
                <location class="m-1 " width="40px" style="font-weight:bold; color: #0284c7"/>
              </div>
              <div class="text-end">
                <div class="fs-3" style="color: #0284c7">5</div>
                <div class="a-fs  text-uppercase text-muted">本月考勤情况</div>
              </div>
            </div>
          </a>
        </div>
        <div class="col-6 col-xl-3 card-hover">
          <a class="a-block mt-3 p-4 fw-bold">
            <div
                class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">
              <div class="d-none d-sm-block">
                <AlarmClock class="m-1 " width="40px" style="font-weight:bold;color: #5ccea7"/>
              </div>
              <div class="text-end">
                <div class="fs-3 text-earth" style="color: #5ccea7 ">24</div>
                <div class="a-fs text-uppercase text-muted">本月请假次数</div>
              </div>
            </div>
          </a>
        </div>
        <div class="col-6 col-xl-3 card-hover">
          <a class="a-block mt-3 p-4 fw-bold ">
            <div
                class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">
              <div class="d-none d-sm-block">
                <Coin class="m-1 " width="40px" style="font-weight:bold;color: #8f55f2"/>
              </div>
              <div class="text-end">
                <div class="fs-3" style="color: #8f55f2">57</div>
                <div class="a-fs text-uppercase text-muted">绩效考核得分</div>
              </div>
            </div>
          </a>
        </div>
        <div class="col-6 col-xl-3 card-hover">
          <a class="a-block mt-3 p-4 fw-bold">
            <div
                class="block-content block-content-full d-sm-flex justify-content-between align-items-center border-black-op-b border-3">
              <div class="d-none d-sm-block">
                <OfficeBuilding class="m-1 " width="40px" style="font-weight:bold;color: #5aced3"/>
              </div>
              <div class="text-end">
                <div class="fs-3 " style="color: #5aced3">0</div>
                <div class="a-fs text-uppercase text-muted">本月培训计划</div>
              </div>
            </div>
          </a>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-8 mb-2 mt-4 ">
          <el-card class="notice-card">
            <template #header>
              <div class="text-muted fw-bold">公告通知</div>
            </template>
            <el-scrollbar max-height="200px">
              <div v-for="o in 6" :key="o" class="text item">{{ '富文本内容' + o }}</div>
            </el-scrollbar>
          </el-card>
        </div>
        <div class="col-lg-4 mb-2 mt-4 ">
          <el-card class="box-card notice-card">
            <template #header>
              <div class="text-center">
                <div>
                  <el-avatar :size="70"
                             src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                  />
                </div>
                <div class="" style="font-size:14px">
                  <div class="fw-semibold fs-6 fw-bold mb-1 text-uppercase text-muted">
                    <Male class="m-1 text-primary" width="20px"/>二勿载 <span style="font-size:8px">UID: 1</span>
                  </div>
                  <div class="fw-bold text-uppercase text-muted">入职日期 - 2030-08-13</div>
                  <div class="fw-bold text-uppercase text-muted">共有 100 个配额 | 已使用 ： 1 个</div>
                </div>
              </div>
            </template>
            <div v-for="o in 4" :key="o" class="text item ">{{ 'List item ' + o }}</div>
          </el-card>
        </div>
      </div>
    </div>
    <Foot/>
</template>

<script setup>
import Foot from '@/components/footer/Foot.vue'
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
  background-color: #f6f7f9;
  font-size: 15px;
}
</style>