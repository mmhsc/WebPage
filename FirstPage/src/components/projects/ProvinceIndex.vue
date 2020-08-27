<template>
  <el-container>
      <el-aside style="width: 200px; margin-top: 20px">
        <sideMenu @indexSelect="listByCategory" ref="sideMenu"></sideMenu>
      </el-aside>
      <el-main>
        <province class="projectsArea" ref="projectsArea"></province>
      </el-main>
    </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Province from './Province'
export default{
  name: 'ProvinceIndex',
  components: {SideMenu, Province},
  methods: {
    listByCategory () {
      var category = this.$refs.sideMenu.category
      this.$axios
        .post('/province/projects/searchByCategory', {'category': category})
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
