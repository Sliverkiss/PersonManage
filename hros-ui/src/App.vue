<script>
import Header from '@/components/Header.vue'
export default {
  name: 'Layout',
  components: {
    Header
  }
}
</script>

<template>
  <div class="">
      <router-view/>
  </div>

</template>

<style scoped>
.main {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  width: 100%;
}
</style>
