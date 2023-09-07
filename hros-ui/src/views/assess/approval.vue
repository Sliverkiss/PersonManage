<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>绩效审核</span>
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
            <!--管理员审核表单-->
            <el-col :span="12">
              <div class="border rounded">
                <el-descriptions title="员工自评" class="ms-4 me-5 mt-3" column="1">
                  <el-descriptions-item v-for="(domain,index) in state.declare.declareList" :key="index">
                    <div class="border rounded">
                      <el-descriptions :title="domain.itemName" class="ms-4 me-5 mt-3" column="1" size="small">
                        <div class="border rounded">
                          <el-descriptions-item label="自评分">{{ domain.score }}</el-descriptions-item>
                          <el-descriptions-item label="自评内容">{{ domain.content }}</el-descriptions-item>
                        </div>
                      </el-descriptions>
                    </div>
                  </el-descriptions-item>
                  <el-descriptions-item label="总分">{{ state.declare.score }}

                  </el-descriptions-item>
                </el-descriptions>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="border rounded">
                <el-descriptions title="考核审批" class="ms-4 me-5 mt-3" column="1">
                  <el-descriptions-item>
                    <div>
                      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
                        <el-row :gutter="24">
                          <el-col :span="24" class="mt-1">
                            <el-form-item prop="score" size="large" label="审批评分：" label-width="120">
                              <el-input v-model.number="state.formData.score"></el-input>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="status" size="large" label="审批状态：" label-width="120">
                              <el-select v-model="state.formData.status" style="width:360px;"
                                         placeholder="请选择审核状态"
                                         clearable>
                                <el-option label="通过" value="审核通过"/>
                                <el-option label="未通过" value="审核未通过"/>
                              </el-select>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="comment" size="large" label="审批意见：" label-width="120">
                              <el-input v-model="state.formData.comment" type="textarea"
                                        placeholder="请输入审批意见"></el-input>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="assessId" size="large" label="" label-width="120">
                              <el-form-item>
                                <el-button type="primary" @click="save()"
                                >确定
                                </el-button
                                >
                                <el-button type="warning" @click="router.push('/assess/declare')">返回</el-button>
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
import request from "@/request.js";
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {ElMessage} from "element-plus";
//加载路由
import {useRoute, useRouter} from "vue-router";
//加载用户数据
import {useUser} from '@/stores/user.js'

const router = useRouter();
const route = useRoute();
const useStore = useUser();
const user = useStore.getUser();

const {proxy} = getCurrentInstance();
const userStore = useUser();
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
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
}

const save = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //整理数据
      state.formData.declareId = route.query.declareId;
      state.formData.employeeId = route.query.employeeId;
      //将数据发送到后端
      request.post('/admin/assess/approval/save', state.formData).then(res => {
        try {
          res.code == 200 ? ElMessage.success("审批成功！") : ElMessage.error(res.msg);
          router.push('/assess/declare')
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
const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
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
//获取员工自评详情
const getDeclareList = () => {
  let data = {
    employeeId: route.query.employeeId,
    assessId: route.query.assessId,
  }
  request.get('/admin/assess/approval/info/' + data.employeeId + '/' + data.assessId).then(res => {
    try {
      if (res.code === 200) {
        state.declare = res?.data
        console.log(toRaw(state.declare));
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
load()
//获取考核计划列表
getAssessList()
getDeclareList()

</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>
