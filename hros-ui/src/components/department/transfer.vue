<template>
  <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
    <el-tab-pane name="first">
      <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>调岗审核</span>
        </span>
      </template>
      <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
        <div class="row">
          <div class="col-sm-12 col-md-12">
            <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号" clearable></el-input>
            <el-input v-model="employeeName" style="width:120px;margin-left:10px" placeholder="请输入员工姓名"
                      clearable></el-input>
            <el-button type="primary" class="ms-2" @click="load">
              <el-icon>
                <Search/>
              </el-icon>
              搜索
            </el-button>
          </div>
        </div>
        <div style="flex:1"></div>
        <div class="" style="width:120px">
          <el-button type="primary" plain @click="dialogFormVisible= true">
            <el-icon>
              <Plus/>
            </el-icon>
            <span>新增调岗</span></el-button>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 p-3 ">
          <el-auto-resizer>
            <el-table :data="state.tableData" stripe
                      class="text-center">
              <template v-for="(col,index) in departmentStore.transferMap" :key="index">
                <el-table-column :prop="col.key" :label="col.value" align="center"
                                 :width="flexWidth(col.key,state.tableData,col.value)"
                ></el-table-column>
              </template>
              <el-table-column prop="state" label="审核状态" align="center" sortable width="110">
                <template #default="scope">
                  <el-tag
                      :type="scope.row.state === '通过' ? '' :scope.row.state ==='未通过'?'danger': 'warning'"
                      disable-transitions
                  >{{ scope.row.state }}
                  </el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="操作" width="120">
                <template #default="scope">
                  <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">
                    <el-icon>
                      <Edit style="color:#213d5b"/>
                    </el-icon>
                  </el-button>
                  <el-popconfirm @confirm="DeleteEntity(scope.row.id)" title="确认删除?"
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
          </el-auto-resizer>
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
    <el-tab-pane label="调岗记录" name="second">Config</el-tab-pane>
  </el-tabs>

</template>

<script setup>
import {getCurrentInstance, reactive, ref, toRaw} from 'vue'
import {useDepartment} from "@/stores/department.js"
import request from "@/request.js";
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'

const {proxy} = getCurrentInstance();
const departmentStore = useDepartment();
const activeName = ref('first')

const state = reactive({
  tableData: [],
  formData: {},
  updateData: {},
})

//通用参数模块区域
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const employeeId = ref('');//员工编号
const employeeName = ref('');//员工姓名
//打开视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
//换页
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}

const DeleteEntity = (id) => {
  request.delete('admin/department/transfer/delete/' + id).then((res) => {
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
const load = () => {
  request.get('/admin/department/transfer/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      employeeId: employeeId.value,
      employeeName: employeeName.value,
    }
  }).then((res) => {
    try {
      if (res.code == 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        console.log(toRaw(state.tableData))
      } else {
        total.value = res.data.total - 0;
        ElMessage.error(res.msg)
      }
    } catch (e) {
      ElMessage.error(e)
    }
  })
}


load()
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}

.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}
</style>