<template>
  <div>
    <div style="width:400px; margin:150px auto">
      <h1 style="text-align:center;margin-bottom:30px">登录</h1>
      <el-form v-model="user" size="large">
        <el-form-item>
          <el-input v-model="user.name" :prefix-icon="User"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="user.password" :prefix-icon="Lock" show-password/>
        </el-form-item>
        <el-form-item>
          <el-button type = "primary" style = "width:100%">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>
<script>
import {User, Lock} from '@element-plus/icons-vue'
export default {
  name: "Login",
  components: {User,Lock}
}
</script>
<script setup>
import {reactive} from 'vue'
const user =reactive({
  name:"张三"
})
</script>

<style scoped>

</style>