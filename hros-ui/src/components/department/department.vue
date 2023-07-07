<template>
  <div>
    <div class="content" id="pjax-container">
      <div class="block block-rounded">
        <el-card class="notice-card ">
          <template #header>
            <div class="text-muted fw-bold " style="display:flex">
              <div style="width:200px">
                <UserFilled class="m-1 " width="20px"/>
                部门岗位管理
              </div>
              <div style="flex:1"></div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <el-input style="width:160px" placeholder="请输入员工编号"></el-input>
                  <el-input style="width:160px;margin-left:10px" placeholder="请输入姓名"></el-input>
                  <el-button type="primary" class="ms-2">
                    <el-icon>
                      <Search/>
                    </el-icon>
                    搜索
                  </el-button>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 p-3">
                  <div class="demo-collapse">
                    <el-collapse v-model="state.tableData" accordion>
                      <el-collapse-item v-for="(item,index) in state.tableData" title="Feedback" :name="activeName">

                      </el-collapse-item>
                    </el-collapse>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-md-4">
                  <div class="dataTables_info" id="usersList_info" role="status" aria-live="polite"><span
                      class="text-muted ">共有 5 条 / 1 页</span></div>
                </div>
                <div class="col-sm-12 col-md-7">
                  <el-pagination
                      background
                      page-size="10"
                      layout="prev, pager, next"
                      :total="50">
                  </el-pagination>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, reactive, ref} from 'vue'
import request from "@/request.js";
import {ElMessage} from "element-plus";

const activeName = ref('1')
const size = ref('')
const blockMargin = computed(() => {
  const marginMap = {
    large: '32px',
    default: '28px',
    small: '24px',
  }
  return {
    marginTop: marginMap[size.value] || marginMap.default,
  }
})
const state = reactive({
  tableDate:[]
})

const load=()=>{
  request.get('/admin/department/list').then((res) => {
    try{
      if (res.code ==200){
        state.tableData = res.data?.records
      }else{
        ElMessage.error(res.msg)
      }
    }catch (e) {
      ElMessage.error(e)
    }
  })
}

load();
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>