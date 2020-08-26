<template>
  <div>
    <el-row style="height: 840px;">
      <SearchBar @searchOn="search" ref="searchBar"></SearchBar>
      <el-table :data="projects.slice((currentPage-1)*pagesize,currentPage*pagesize)" style="width: 100%" border>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="project-table-expand">
              <el-form-item id="item" label="项目名称">
                <span>{{ props.row.item }}</span>
              </el-form-item>
              <el-form-item label="项目类别">
                <span>{{ props.row.category }}</span>
              </el-form-item>
              <el-form-item label="招标单位">
                <span>{{ props.row.purchaser }}</span>
              </el-form-item>
              <el-form-item label="招标地点">
                <span>{{ props.row.location }}</span>
              </el-form-item>
              <el-form-item label="联系方式">
                <span>{{ props.row.phonenumber }}</span>
              </el-form-item>
              <el-form-item label="预算金额(万)">
                <span>{{ props.row.budget }}</span>
              </el-form-item>
              <el-form-item label="发布日期">
                <span>{{ props.row.releasedate }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="项目名称" prop="item"></el-table-column>
        <el-table-column label="项目类别" prop="category"></el-table-column>
        <el-table-column label="招标单位" prop="purchaser"></el-table-column>
        <el-table-column label="预算金额" prop="budget"></el-table-column>
        <el-table-column label="发布日期" prop="releasedate"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pagesize"
          layout="total, prev, pager, next, jumper"
          :total="projects.length">
        </el-pagination>
      </div>
    </el-row>
  </div>
</template>

<script>
import SearchBar from './SearchBar'
export default {
  name: 'City',
  components: {SearchBar},
  data () {
    return {
      projects: [],
      currentPage: 1,
      pagesize: 10
    }
  },
  mounted: function () {
    this.loadProjects()
  },
  methods: {
    loadProjects () {
      this.$axios
        .get('/city/projects')
        .then(response => {
          if (response && response.status === 200) {
            this.projects = response.data
          }
        })
        .catch(failResponse => {
          console.log('Load city projects can not work correctly...')
        })
    },
    search () {
      this.$axios
        .post('/city/searchByDate', {start: this.$refs.searchBar.date[0], 'end': this.$refs.searchBar.date[1]})
        .then(successResponse => {
          if (successResponse.status === 200) {
            this.projects = successResponse.data
          }
        })
        .catch(failResponse => {
          console.log('日期查询失败')
        })
    },
    handleCurrentChange (val) {
      this.currentPage = val
      console.log(`当前页：${val}`)
    },
    handleSizeChange (val) {
      this.pagesize = val
      console.log(`每页${val}条`)
    }
  }
}
</script>

<style>
 .project-table-expand {
    font-size: 0;
  }
  .project-table-expand label {
    width: 70px;
    color: #99a9bf;
  }
  #item {
    width: 100%;
  }
  .project-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .pagination {
    position: bottom;
    margin-bottom: 0px;
  }
</style>
