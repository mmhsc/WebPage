<template>
  <el-container>
    <el-aside style="width: 200px; margin-top: 20px">
      <sideMenu @indexSelect="listByCategory" ref="sideMenu"></sideMenu>
    </el-aside>
    <el-main>
      <city class="projectsArea" ref="projectsArea"></city>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import City from './City'
export default{
  name: 'CityIndex',
  components: {SideMenu, City},
  methods: {
    listByCategory () {
      var cid = this.$refs.sideMenu.cid
      var url = '/city/categories/' + cid + '/projects'
      this.$axios
        .get(url)
        .then(successResponse => {
          if (successResponse.status === 200) {
            this.$refs.projectsArea.projects = successResponse.data
          }
        })
        .catch(failResponse => {
          console.log('刷新失败')
        })
    }
  }
}
</script>

<style>
</style>
