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
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow:0 0 10px black;
}
</style>