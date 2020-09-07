<template>
  <el-table :data="recentData" style="width: 100%" height="250" stripe border>
    <el-table-column prop="purchaser" label="采购方"></el-table-column>
    <el-table-column prop="category" label="类别"></el-table-column>
    <el-table-column prop="budget" label="预算金额"></el-table-column>
    <el-table-column prop="releasedate" label="发布日期"></el-table-column>
  </el-table>
</template>

<script>
export default {
  name: 'Form',
  data () {
    return {
      recentData: []
    }
  }
}
</script>

<style scoped>

</style>
