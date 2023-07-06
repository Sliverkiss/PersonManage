<template>
  <el-dialog v-model="dialogFormVisible" title="入职登记" align-center center class=""
             style="border-radius: 0.875rem 1rem;">
    <h6 class="mb-3">基本信息</h6>
    <el-form class="" status-icon>
      <el-row :gutter="24">
        <el-col :span="16">
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="姓名:">
                <el-input v-model="state.formData.name"> </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别:">
                <el-select v-model="state.formData.gender" placeholder="">
                  <el-option label="男" value="男"/>
                  <el-option label="女" value="女"/>
                  <el-option label="其他" value="其他"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="民族:">
                <el-input v-model="state.formData.nation"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="籍贯:">
                <el-input v-model="state.formData.naticePlace"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="最高学历">
                <el-select v-model="state.formData.tiptopDegree" placeholder="">
                  <el-option label="博士" value="博士"/>
                  <el-option label="硕士" value="硕士"/>
                  <el-option label="本科" value="本科"/>
                  <el-option label="大专" value="大专"/>
                  <el-option label="高中" value="高中"/>
                  <el-option label="初中" value="初中"/>
                  <el-option label="小学" value="小学"/>
                  <el-option label="其他" value="其他"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="婚姻状况:">
                <el-select v-model="state.formData.wedlock" placeholder="">
                  <el-option label="已婚" value="shanghai"/>
                  <el-option label="未婚" value="beijing"/>
                  <el-option label="离异" value="beijing"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8" class="pb-3">
          <div class="bg-info text-center" style="height:100%;align:center">cd</div>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :span="10">
          <el-form-item label="政治面貌:">
            <el-select v-model="state.formData.politic" placeholder="">
              <el-option label="中共党员" value="中共党员"/>
              <el-option label="中共预备党员" value="中共预备党员"/>
              <el-option label="共青团员" value="共青团员"/>
              <el-option label="群众" value="群众"/>
              <el-option label="其他" value="其他"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="14">
          <el-form-item label="出生日期:">
            <el-input v-model="state.formData.birthday"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :span="10">
          <el-form-item label="毕业院校:">
            <el-input v-model="state.formData.school"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="14">
          <el-form-item label="联系电话:">
            <el-input v-model="state.formData.phone"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter=24>
        <el-col :span="10">
          <el-form-item label="所学专业:">
            <el-input v-model="state.formData.specialty"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="14">
          <el-form-item label="邮箱:">
            <el-input v-model="state.formData.email"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :span="10">
          <el-form-item label="身份证号">
            <el-input v-model="state.formData.idCard"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="14">
          <el-form-item label="地址">
            <el-input v-model="state.formData.address"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <h6 class="mb-3">工作信息</h6>
    <el-form>
      <el-row :gutter="24">
        <el-col :span="10">
          <el-form-item label="合同类型:">
            <el-select  v-model="state.formData.contractTerm" placeholder="">
              <el-option label="劳务合同" value="shanghai"/>
              <el-option label="外聘合同" value="beijing"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">

        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false" type="primary">登记</el-button>
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  name: "employee"
}
</script>

<style scoped>

</style>