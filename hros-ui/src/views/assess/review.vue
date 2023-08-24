<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>绩效申报</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2">
          <el-row :gutter="24">
            <el-col :span="12">
              <div class="border rounded">
                <el-descriptions title="考核说明" class="ms-4 me-5 mt-3" column="1">
                  <el-descriptions-item label="考核标题">{{ state.tableData.title }}</el-descriptions-item>
                  <el-descriptions-item label="考核总分">{{ state.tableData.socre }}</el-descriptions-item>
                  <el-descriptions-item label="起始日期">{{
                      state.tableData.startDate
                    }}-{{ state.tableData.endDate }}
                  </el-descriptions-item>
                  <el-descriptions-item label="评分标准">
                    {{ state.tableData.standard }}
                  </el-descriptions-item>
                  <el-descriptions-item label="考核说明">
                    {{ state.tableData.remark }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="border rounded">
                <el-descriptions title="考核项说明" class="ms-4 me-5 mt-3" column="1">
                  <el-descriptions-item v-for="(item, index) in state.tableData.items" :key="item.id">
                    <div class="border rounded">
                      <el-descriptions :title="item.name" class="ms-4 me-5 mt-3" column="1" size="small">
                        <div class="border rounded">
                          <el-descriptions-item label="总分">{{ item.totalScore }}</el-descriptions-item>
                          <el-descriptions-item label="评分标准">{{ item.standard }}</el-descriptions-item>
                        </div>
                      </el-descriptions>
                    </div>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="24" class="mt-4">
            <!--员工自评表单-->
            <el-col :span="24">
              <div class="border rounded">
                <el-descriptions title="员工自评" class="ms-4 me-5 mt-3" column="1">
                  <el-descriptions-item>
                    <div>
                      <el-form :model="state.tableData" class="" status-icon :rules="rules" ref="ruleFormRef">
                        <el-row :gutter="24" v-for="(domain,index) in state.tableData.items">
                          <el-col :span="8" class="mt-1">
                            <el-form-item :prop="'assessId'+index" size="large" :label="domain.name" label-width="120">
                              <el-input style="width:250px;" type="textarea" v-model="domain.content"></el-input>
                            </el-form-item>
                          </el-col>
                          <el-col :span="6">
                            <el-form-item prop="assessId" size="large" label="自评分：" label-width="80">
                              <el-input v-model="domain.score" :placeholder="'请输入'+domain.name+'自评分'"></el-input>
                            </el-form-item>
                          </el-col>
                        </el-row>
                        <el-row :span="24">
                          <el-col :span="24">
                            <el-form-item prop="assessId" size="large" label="" label-width="120">
                              <el-form-item>
                                <el-button type="primary" @click="save()">确定</el-button>
                                <el-button type="warning" @click="resetForm(ruleFormRef)">返回</el-button>
                              </el-form-item>
                            </el-form-item>
                          </el-col>
                        </el-row>
                      </el-form>
                    </div>
                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </el-col>
          </el-row>
        </div>

      </el-tab-pane>
    </el-tabs>
    <Foot/>
  </div>

</template>

<script setup>
//加载后端合同数据
import request from "@/request.js";
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {useContract} from "@/stores/employee.js";
import {ElMessage, ElNotification} from "element-plus";
import {useUser} from '@/stores/user.js'
//加载路由
import {useRoute, useRouter} from "vue-router";

const useStore = useUser();
const user = useStore.getUser();
const router = useRouter();
const route = useRoute();


const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const activeName = ref('first')


const state = reactive({
  tableData: [],
  reviewList: [],
  declare: {},
  formData: {},
  departmentList: [],
  assessSetList: [],
  updateData: {},
})

const rules = reactive({
  planId: [
    {required: true, message: '请输入培训计划编号', trigger: 'blur'},
  ],
  employeeId: [
    {required: true, message: '请输入教练名称', trigger: 'blur'},
  ]
})
//打开新增续约视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const title = ref('');

const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
const EditRenewal = (row) => {
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
  console.log(toRaw(state.updateData))
}
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
}

const save = () => {
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //整理数据
      state.declare = {
        employeeId: user.employeeId,
        assessId: route.query.assessId,
        assessSet: toRaw(state.tableData),
        type: 0,
        declareList: [],
      }
      let index = 0
      for (let item of state.tableData.items) {
        //自评子项
        let declare = {
          itemId: item.id,
          employeeId: user.employeeId,
          assessId: route.query.assessId,
          itemName: item.name,
          type: 1,
          content: item.content,
          score: item.score
        }
        state.declare.declareList[index] = declare
        index++
        console.log(toRaw(state.declare))
      }
      //将数据发送到后端
      request.post('/admin/assess/declare/save', state.declare).then(res => {
        try {
          ElMessage.success("已发送数据");
        } catch {
          ElMessage.error(res.msg);
        }
      })
    } else {
      ElMessage.error('添加信息填写错误')
    }
  })
}
//修改员工资料
const update = () => {
  request.post('/admin/assess/set/update', state.updateData).then((res) => {
    try {
      if (res.code == 200) {
        ElNotification.success(res.msg);
      } else {
        ElMessage.error(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    } finally {
      clearFormData();
      load();
    }
  })
}

const load = () => {
  request.get('/admin/assess/set/info/' + route.query.assessId).then(res => {
    try {
      state.tableData = res
    } catch {
      ElMessage.error(res.msg);
    }
  })
}
//考核计划列表
const getAssessList = () => {
  request.get('/admin/assess/set/title/list').then(res => {
    try {
      if (res.code === 200) {
        state.assessSetList = res.data
        console.log(toRaw(state.assessSetList));
      } else {
        state.tableData = [];
        total.value = 0
        ElMessage.warning(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    }
  })
}

const Del = (id) => {
  request.delete('admin/training/record/delete/' + id).then((res) => {
    try {
      if (res.code == 200) {
        ElNotification.success(res.msg);
      } else {
        ElMessage.error(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    } finally {
      load();
    }
  })
}
load()
//获取考核计划列表
getAssessList()
//获取员工个人申报考核计划

</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>
