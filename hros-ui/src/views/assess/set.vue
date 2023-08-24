<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>

        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>考核计划管理</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="title" style="width:180px" clearable
                        placeholder="请输入考核计划名称">
              </el-input>
              <el-button type="primary" class="ms-2" @Click="load">
                <el-icon>
                  <Search/>
                </el-icon>
                搜索
              </el-button>
            </div>
          </div>
          <div style="flex:1"></div>
          <div class="" style="width:120px">
            <el-button type="success" plain @click="dialogFormVisible= true" v-if="user.role">
              <el-icon>
                <Plus/>
              </el-icon>
              <span>新增计划</span></el-button>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column label="标题" prop="title" align="center"
                               :width="flexWidth('title',state.tableData,'标题')"/>
              <el-table-column label="开始日期" prop="startDate" align="center"/>
              <el-table-column label="结束日期" prop="endDate" align="center"/>
              <el-table-column label="总分" prop="socre" align="center"/>
              <el-table-column label="评分标准" prop="standard" align="center"
                               :width="flexWidth('standard',state.tableData,'评分标准')"/>
              <el-table-column label="考核说明" prop="remark" align="center"
                               :width="flexWidth('remark',state.tableData,'考核说明')"/>
              <el-table-column fixed="right" align="center" label="操作" width="120" v-if="user.role">
                <template #default="scope">
                  <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">
                    <el-icon>
                      <Edit style="color:#213d5b"/>
                    </el-icon>
                  </el-button>
                  <el-popconfirm @confirm="Del(scope.row.id)" title="确认删除?"
                                 confirm-button-text="确认"
                                 cancel-button-text="取消">
                    <template #reference>
                      <el-button type="danger" size="small">
                        <el-icon>
                          <Delete style="color:#582e2e"/>
                        </el-icon>
                      </el-button>
                    </template>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div style="display:flex">
          <div>
            <div class="dataTables_info" id="usersList_info" role="status" aria-live="polite"><span
                class="text-muted ">共有 {{ total }} 条 / {{ currentPage }} 页</span></div>
          </div>
          <div style="flex:1"></div>
          <div>
            <el-pagination
                background
                :page-sizes="[1,10,20,30]"
                layout="prev,pager,next"
                v-model::current-page="currentPage"
                v-model:page-size="pageSize"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :total="total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <Foot/>
    </el-tabs>
  </div>

  <!--  新增-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="新增考核计划" align-center center class="" width="580"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="title" size="large" label="标题：" label-width="130">
              <el-input v-model="state.formData.title" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="startDate" size="large" label="开始日期：" label-width="130">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.formData.startDate"
                      type="date"
                      placeholder=""
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      style="width:400px"
                      size="large"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="endDate" size="large" label="结束日期：" label-width="130">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.formData.endDate"
                      type="date"
                      placeholder=""
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      style="width:400px"
                      size="large"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="socre" size="large" label="总分：" label-width="130">
              <el-input v-model.number="state.formData.socre" placeholder="请输入总分"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="remark" size="large" label="考核说明：" label-width="130">
              <el-input v-model="state.formData.remark" type="textarea" placeholder="请输入考核说明"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="standard" size="large" label="评分标准：" label-width="130">
              <el-input v-model="state.formData.standard" type="textarea" placeholder="请输入评分标准"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="assessItemList" size="large" label="选择考核项：" label-width="130">
              <el-checkbox-group v-model="state.formData.itemIdList">
                <el-checkbox :label="item.id" v-for="item in state.assessItemList" :key="item.id" :value="item.id">
                  {{ item.name }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">添加</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  审核表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="修改计划" align-center center class="" width="580"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="title" size="large" label="标题：" label-width="130">
              <el-input v-model="state.updateData.title" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="startDate" size="large" label="开始日期：" label-width="130">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.updateData.startDate"
                      type="date"
                      placeholder=""
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      style="width:400px"
                      size="large"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="endDate" size="large" label="结束日期：" label-width="130">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.updateData.endDate"
                      type="date"
                      placeholder=""
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      style="width:400px"
                      size="large"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="socre" size="large" label="总分：" label-width="130">
              <el-input v-model.number="state.updateData.socre" placeholder="请输入总分"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="remark" size="large" label="考核说明：" label-width="130">
              <el-input v-model="state.updateData.remark" type="textarea" placeholder="请输入考核说明"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="standard" size="large" label="评分标准：" label-width="130">
              <el-input v-model="state.updateData.standard" type="textarea" placeholder="请输入评分标准"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="itemIdList" size="large" label="选择考核项：" label-width="130">
              <el-checkbox-group v-model="state.updateData.itemIdList">
                <el-checkbox :label="item.id" v-for="(item,index) in state.assessItemList" :key="index">
                  {{ item.name }}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="update" type="primary">审核</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
//加载后端合同数据
import request from "@/request.js";
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {useContract} from "@/stores/employee.js";
import {useDepartment} from "@/stores/department.js"
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'
import {useUser} from '@/stores/user.js'

const useStore = useUser();
const user = useStore.getUser();
const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const departmentStore = useDepartment();
const activeName = ref('first')
const state = reactive({
  tableData: [],
  assessItemList: [],
  formData: {},
  updateData: {},
  planTitleMap: [
    {
      key: 'id',
      value: '编号'
    }, {
      key: 'planName',
      value: '培训计划名称'
    }, {
      key: 'employeeId',
      value: '员工编号'
    }, {
      key: 'employeeName',
      value: '姓名'
    }, {
      key: 'score',
      value: '成绩'
    }
  ]
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
      // state.formData.director = userStore.getUser().username
      //发送后台请求
      console.log(toRaw(state.formData))
      request.post('/admin/assess/set/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('添加成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
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

//
const load = () => {
  request.get('/admin/assess/set/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      title: title.value
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        if (res.data.records.length == 0) {
          ElMessage.warning('查询结果为空～');
        }
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
//考核项列表
const getAssessItemList = () => {
  request.get('/admin/assess/item/list').then((res) => {
    try {
      if (res.code == 200) {
        state.assessItemList = res.data
      }
    } catch {
      ElMessage.error(res.msg);
    } finally {
      load();
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
getAssessItemList()
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>