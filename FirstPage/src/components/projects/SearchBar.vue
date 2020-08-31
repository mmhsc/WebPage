<template>
   <div class="searchBar">
      <el-date-picker
        v-model="date"
        type="daterange"
        align="right"
        value-format="yyyy-MM-dd"
        range-separator="-"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :picker-options="pickerOptions">
      </el-date-picker>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      defaultDate: [],
      date: []
    }
  },
  mounted: function () {
    this.initDate()
  },
  methods: {
    initDate () {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      this.defaultDate[0] = start.toISOString()
      this.defaultDate[1] = end.toISOString()
      this.date = this.defaultDate
    },
    search () {
      this.$emit('searchOn')
    }
  }
}
</script>

<style>
</style>
