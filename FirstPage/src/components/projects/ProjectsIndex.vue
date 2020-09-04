<template>
  <el-container>
    <el-aside style="width: 200px; margin-top: 20px">
      <sideMenu @init="flushProjects" @categorySelect="categoryOn" ref="sideMenu"></sideMenu>
    </el-aside>
    <el-main>
      <projects class="projectsArea" @pageSelect="pageOn" @searchByDate="dateOn" ref="projectsArea"></projects>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Projects from './Projects'
export default{
  name: 'ProjectsIndex',
  components: {SideMenu, Projects},
  watch: {
    '$route': 'flushProjects'
  },
  methods: {
    count () {
      var condition = {categories: this.$refs.sideMenu.category, start: this.$refs.projectsArea.$refs.searchBar.date[0], end: this.$refs.projectsArea.$refs.searchBar.date[1]}
      this.$axios
        .post(this.$route.path + '/num', condition)
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$refs.projectsArea.total = resp.data.data
          }
        })
        .catch(resp => {
          console.log('刷新项目数失败')
        })
    },
    flushProjects () {
      this.$refs.sideMenu.category = this.$refs.sideMenu.categoryOptions
      this.$refs.projectsArea.$refs.searchBar.date = this.$refs.projectsArea.$refs.searchBar.defaultDate
      this.$refs.projectsArea.currentPage = 1
      this.count()
      this.loadProjects()
    },
    loadProjects () {
      var condition = {categories: this.$refs.sideMenu.category, start: this.$refs.projectsArea.$refs.searchBar.date[0], end: this.$refs.projectsArea.$refs.searchBar.date[1], currentPage: this.$refs.projectsArea.currentPage, pagesize: this.$refs.projectsArea.pagesize}
      this.$axios
        .post(this.$route.path, condition)
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$refs.projectsArea.projects = resp.data.data
          }
        })
        .catch(resp => {
          console.log('刷新项目列表失败')
        })
    },
    categoryOn () {
      this.category = this.$refs.sideMenu.category
      this.$refs.projectsArea.currentPage = 1
      this.count()
      this.loadProjects()
    },
    dateOn () {
      this.count()
      this.loadProjects()
    },
    pageOn () {
      this.loadProjects()
    }
  }
}
</script>

<style>
</style>
