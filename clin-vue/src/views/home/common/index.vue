<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <br>
      <img :src="architecturePng" class="architecturePng">
      <br>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CommonEditor',
  data() {
    return {
      // architecturePng: 'https://img.cc0.cn/pixabay/2019102121144319242.jpg!860.jpg'
      architecturePng: '../data/workbench.jpg'
    }
  }
}
</script>

<style lang="scss" scoped>
  .architecturePng {
    max-width: 100%;
  }

  .dashboard-container {
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    min-height: 100vh;
    padding: 16px;
    text-align: center;
  }

  .dashboard-editor-container {
    background-color: white;
    padding: 20px;
    display: inline-block;
    text-align: left;
  }

  a {
    color: #1890FF;
  }

  a:hover {
    color: #40a9ff;
  }
</style>
