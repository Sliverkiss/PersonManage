<template>
<div>
  <div class="content" id="pjax-container">
    <div class="block block-rounded">
      <el-card class="notice-card ">
        <template #header>
          <div class="text-muted fw-bold " style="display:flex">
            <div style="width:200px">
              <UserFilled class="m-1 " width="20px"/>
              员工合同管理
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
                <el-auto-resizer>
                  <el-table  stripe class="text-center">


                    <el-table-column prop="workState" label="工作状态"></el-table-column>
                    <el-table-column fixed="right" label="操作" width="120">
                      <template #default>
                        <el-button size="small" @click="handleClick" style="background-color:#66b1ff">
                          <el-icon>
                            <Edit style="color:#213d5b"/>
                          </el-icon>
                        </el-button>
                        <el-button type="danger" size="small">
                          <el-icon>
                            <Delete style="color:#582e2e"/>
                          </el-icon>
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-auto-resizer>
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

<script>
export default {
  name: "contract"
}
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>