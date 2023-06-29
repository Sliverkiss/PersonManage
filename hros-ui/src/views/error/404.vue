<template>
  <el-result
      icon="warning"
      title="404"
      sub-title="很抱歉，你访问的页面走丢了~"
  >
    <template #extra>
      <el-button type="primary" @click="$event=>$router.push(`/`)">返回首页</el-button>
    </template>
  </el-result>
</template>

<script>
export default {
  name: "404"
}
</script>

<style scoped>

</style>