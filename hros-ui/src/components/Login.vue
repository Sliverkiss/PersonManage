<template>
  <div class="m-0 p-0" style="width:100vw;height:100vh;overflow: hidden;display: flex">
    <div style="flex:1;">
      <el-image style="width:100%;height:100%;" fit="cover"
                src="https://img1.baidu.com/it/u=1878063085,1538548069&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"></el-image>
      <div class="welcome-title">
        <div class="p-4">
          <p class="fs-3 fw-semibold text-white">
            Get Inspired and Create.
          </p>
          <p class="text-white fw-medium">
            Copyright © <span>2023</span>
          </p>
        </div>
      </div>
    </div>
    <div style="width:400px;">
      <router-view/>
      测试动态路由跳转页面
      <el-button @click="router.push('/text')"></el-button>
    </div>
  </div>
  <div class="container" style="height: 100%" id="container" :class="{ 'right-panel-active': isActive }">
    <!-- 注册页面 -->
    <div class="form-container sign-up-container">
      <div class="form">
        <h1>创建新用户</h1>
        <input type="text" placeholder="用户名"/>
        <input type="email" placeholder="邮箱地址"/>
        <input type="password" placeholder="密码" @focus="infoStatus = true" @blur="infoStatus = false"/>
        <div v-show="infoStatus">6-18位,包括数字和字母</div>
        <input type="password" placeholder="再次输入密码"/>
        <button>注册</button>
      </div>
    </div>
    <!-- 登录页面 -->
    <div class="form-container sign-in-container">
      <div class="form">
        <h1>登录</h1>
        <input type="email" placeholder="邮箱地址"/>
        <input type="password" placeholder="密码"
               @focus="infoStatus = true" @blur="infoStatus = false"/>
        <div v-show="infoStatus">6-18位,包括数字和字母</div>
        <a href="#">忘记密码？</a>
        <button>登录</button>
      </div>
    </div>
    <!-- 说明页面 -->
    <div class="overlay-container">
      <div class="overlay">
        <!-- 登录信息 -->
        <div class="overlay-panel overlay-left">
          <h1>Welcome Back!</h1>
          <p>为了与我们保持联系，请登录您的个人信息</p>
          <button class="ghost" id="signIn" @click="signIn">登录</button>
        </div>
        <!-- 注册信息 -->
        <div class="overlay-panel overlay-right">
          <h1>Hello</h1>
          <p>即刻加入我们，开启新旅途</p>
          <button class="ghost" id="signUp" @click="signUp">注册</button>
        </div>
      </div>
    </div>
    <teleport to="body">
      <div class="modal" v-if="recoverPasswordStatus" @click="status = false">
        <div>
          <div class="hd">
            <el-icon @click="recoverPasswordStatus = false">
              <CloseBold/>
            </el-icon>
          </div>
          <section>
            <h2>修改密码</h2>
            <input type="email" placeholder="邮箱地址" v-model="recoverInfo.email"/>
            <input type="text" placeholder="验证码" v-model="recoverInfo.code"/>
            <button class="getCodeBtn" @click="getCode()">
              <span v-if="getCodeStatus">发送验证码</span>
              <span v-else>重新发送验证码（{{ codeTime }}）</span>
            </button>
            <input
                type="password"
                placeholder="请输入新密码"
                v-model="recoverInfo.pwd"
                @focus="infoStatus = true"
                @blur="infoStatus = false"/>
            <div v-show="infoStatus">6-18位,包括数字和字母</div>
          </section>
          <button @click="recoverPwd">重置密码</button>
        </div>
      </div>
    </teleport>
  </div>
</template>
<script setup lang="ts">
import {ElMessage} from "element-plus";
import {ref} from "vue";
// 信息提示
// 弹出错误信息
const errMsg = (msg: string) => {
  ElMessage({
    showClose: true,
    message: msg,
    type: "error",
  });
};
// 弹出正确提示
const successMsg = (msg: string) => {
  ElMessage({
    showClose: true,
    message: msg,
    type: "success",
  });
};
// 控制界面
let isActive = ref(false);

// 邮箱格式正则
let emailReg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/;
// 密码格式验证
// 至少八个字符，至少一个字母和一个数字：
let passReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
// 用户名格式验证
// 用户名 2-20位，只能包含汉字/数字/字母和下划线
let username = /^[\w\u4E00-\u9FA5]{4,20}$/;
//
let infoStatus = ref(false);
// 切换登录注册
// 点击登录
function signIn() {
  isActive.value = false;
}
// 点击注册
function signUp() {
  isActive.value = true;
}
</script>
<style lang="less" scoped>
h1 {
  font-weight: bold;
  margin: 0;
}

h2 {
  text-align: center;
}

p {
  font-size: 14px;
  font-weight: 100;
  line-height: 20px;
  letter-spacing: 0.5px;
  margin: 20px 0 30px;
}

span {
  font-size: 12px;
}

a {
  color: #333;
  font-size: 14px;
  text-decoration: none;
  margin: 15px 0;
}

button {
  border-radius: 20px;
  border: 1px solid #0ab5f3;
  background-color: #0ab5f3;
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

button.ghost {
  background-color: transparent;
  border-color: #ffffff;
}

.form {
  background-color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  text-align: center;
}

.form div {
  font-size: 12px;
  color: rgb(162, 162, 162);
}

input {
  background-color: #eee;
  border: none;
  padding: 12px 15px;
  margin: 8px 0;
  width: 100%;
}

.container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in-container {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.sign-up-container {
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}

.overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
}

.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.overlay {
  background: #ff4b2b;
  background: -webkit-linear-gradient(to right, #ff4b2b, #ff416c);
  background: linear-gradient(to right, #ff4b2b, #ff416c);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #ffffff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay-panel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  text-align: center;
  top: 0;
  height: 100%;
  width: 50%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.overlay-left {
  transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
  transform: translateX(0);
}

.overlay-right {
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay-right {
  transform: translateX(20%);
}

.social-container {
  margin: 20px 0;
}

.social-container a {
  border: 1px solid #dddddd;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 5px;
  height: 40px;
  width: 40px;
}

/* 弹窗样式 */
.modal {
  z-index: 999;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal > div {
  display: flex;
  flex-direction: column;
  align-items: center;
  // justify-content: center;
  background-color: white;
  width: 384px;
  height: 480px;
  padding: 5px;
  border-radius: 10px;

  .hd {
    width: 100%;
    height: 30px;
    padding-right: 10px;

    i {
      float: right;
      height: 30px;
      cursor: pointer;
      color: red;
    }
  }

  section {
    height: 65%;
    width: 100%;
    padding: 0px 38px;
    text-align: center;

    div {
      font-size: 12px;
      color: rgb(140, 140, 140);
    }

    input {
      width: 83%;
    }

    .getCodeBtn {
      width: 90%;
      height: 29px;
      border-radius: 0px;
      padding: 0px;
    }
  }
}
</style>