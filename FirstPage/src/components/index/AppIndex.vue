<template>
  <el-container style="width: 100%; height: 100%; position: absolute">
    <el-header style="height: 100px">
      <Header ref="header"></Header>
    </el-header>
    <el-container  style="height: 30%">
      <el-aside style="width: 20%">
        <PieChart ref="pie1" id="provincePie" style="position: center"></PieChart>
      </el-aside>
      <el-main style="width: 60%">
        <FormChart ref="form1" id="formchart"></FormChart>
      </el-main>
      <el-aside style="width: 20%">
        <PieChart ref="pie2" id="cityPie" style="position: center"></PieChart>
      </el-aside>
    </el-container>
    <el-container style="height: 70%">
      <el-container style="width: 35%">
        <el-header style="width: 100%; height: 50%">
          <BarChart ref="bar1" id="provinceBar"></BarChart>
        </el-header>
        <el-footer style="width: 100%; height: 50%">
          <LineChart ref="line1" id="provinceLine"></LineChart>
        </el-footer>
      </el-container>
      <el-main style="width: 30%">
        <ShandongMap ref="map"></ShandongMap>
      </el-main>
      <el-container style="width: 35%">
        <el-header style="width: 100%; height: 50%">
          <BarChart ref="bar2" id="cityBar"></BarChart>
        </el-header>
        <el-footer style="width: 100%; height: 50%">
          <LineChart ref="line2" id="cityLine"></LineChart>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import Header from './Header'
import BarChart from './BarChart'
import LineChart from './LineChart'
import ShandongMap from './ShandongMap'
import PieChart from './PieChart'
import FormChart from './FormChart'
export default {
  name: 'AppIndex',
  components: {Header, BarChart, LineChart, ShandongMap, PieChart, FormChart},
  data () {
    return {
      cityProjects: [],
      provinceProjects: []
    }
  },
  computed: {
    optionHover () {
      return {
        hoverStop: false,
        step: 1
      }
    }
  },
  mounted: function () {
    this.init()
  },
  methods: {
    // 初始化
    async init () {
      // 获取近一周起始与结束日期
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      // 读取近一周省级招标数据
      await this.$axios
        .post('/visualization/province/projects', {'start': start.toISOString(), 'end': end.toISOString()})
        .then(resp => {
          this.provinceProjects = resp.data.data
        })
        .catch(resp => {
          console.log('获取省级招标数据失败')
        })
      // 读取近一周市级招标数据
      await this.$axios
        .post('/visualization/city/projects', {'start': start.toISOString(), 'end': end.toISOString()})
        .then(resp => {
          this.cityProjects = resp.data.data
        })
        .catch(resp => {
          console.log('获取市级招标数据失败')
        })
      // 初始化子组件
      this.initSubcomponentData()
    },
    // 初始化各个子组件中绘制图表所需数据
    initSubcomponentData () {
      this.initHeader()
      this.initBar()
      this.initLine()
      this.initMapData()
      this.initPie()
      this.initForm()
    },
    // 初始化Header组件中的数据
    initHeader () {
      var totalBudget = 0.0
      for (var i = 0, len = this.cityProjects.length; i < len; i++) {
        totalBudget += this.cityProjects[i]['budget']
      }
      this.$refs.header.cityInfo.num = this.cityProjects.length
      this.$refs.header.cityInfo.totalBudget = totalBudget.toFixed(2)
      for (i = 0, len = this.provinceProjects.length; i < len; i++) {
        totalBudget += this.provinceProjects[i]['budget']
      }
      this.$refs.header.provinceInfo.num = this.provinceProjects.length
      this.$refs.header.provinceInfo.totalBudget = totalBudget.toFixed(2)
    },
    // 初始化省级&市级招标项目区域分布图
    initBar () {
      this.$refs.bar1.id = 'provinceBar'
      this.$refs.bar1.title = '省级招标项目数区域分布图'
      var data = {
        '济南市': 0,
        '青岛市': 0,
        '淄博市': 0,
        '枣庄市': 0,
        '潍坊市': 0,
        '烟台市': 0,
        '临沂市': 0,
        '东营市': 0,
        '德州市': 0,
        '济宁市': 0,
        '菏泽市': 0,
        '聊城市': 0,
        '滨州市': 0,
        '泰安市': 0,
        '日照市': 0,
        '威海市': 0
      }
      var provinceData = JSON.parse(JSON.stringify(data))
      var keys = []
      var values = []
      for (var i = 0, len = this.provinceProjects.length; i < len; i++) {
        var location = this.provinceProjects[i]['location']
        if (location !== '') {
          provinceData[location] += 1
        }
      }
      for (var key in provinceData) {
        if (provinceData[key] !== 0) {
          keys.push(key)
          values.push(provinceData[key])
        }
      }
      this.$refs.bar1.location = keys
      this.$refs.bar1.data = values
      this.$refs.bar1.draw()
      this.$refs.bar2.id = 'cityBar'
      this.$refs.bar2.title = '市级招标项目数区域分布图'
      var cityData = JSON.parse(JSON.stringify(data))
      keys = []
      values = []
      for (i = 0, len = this.cityProjects.length; i < len; i++) {
        location = this.cityProjects[i]['location']
        if (location !== '') {
          cityData[location] += 1
        }
      }
      for (key in cityData) {
        if (cityData[key] !== 0) {
          keys.push(key)
          values.push(cityData[key])
        }
      }
      this.$refs.bar2.location = keys
      this.$refs.bar2.data = values
      this.$refs.bar2.draw()
    },
    // 初始化省级&市级近一周招标金额趋势图
    initLine () {
      this.$refs.line1.id = 'provinceLine'
      this.$refs.line1.title = '近一周省级招标项目金额趋势图'
      var tmp = ''
      var date = []
      var budget = []
      for (var i = 0, len = this.provinceProjects.length; i < len; i++) {
        tmp = this.provinceProjects[i]['releasedate']
        if (date.indexOf(tmp) === -1) {
          date.push(tmp)
          budget[date.indexOf(tmp)] = this.provinceProjects[i]['budget']
        } else {
          budget[date.indexOf(tmp)] += this.provinceProjects[i]['budget']
        }
      }
      for (i = 0; i < budget.length; i++) {
        budget[i] = budget[i].toFixed(2)
      }
      this.$refs.line1.date = date
      this.$refs.line1.budget = budget
      this.$refs.line1.draw()
      date = []
      budget = []
      this.$refs.line2.id = 'cityLine'
      this.$refs.line2.title = '近一周市级招标项目金额趋势图'
      for (i = 0, len = this.cityProjects.length; i < len; i++) {
        tmp = this.cityProjects[i]['releasedate']
        if (date.indexOf(tmp) === -1) {
          date.push(tmp)
          budget[date.indexOf(tmp)] = this.cityProjects[i]['budget']
        } else {
          budget[date.indexOf(tmp)] += this.cityProjects[i]['budget']
        }
      }
      for (i = 0; i < budget.length; i++) {
        budget[i] = budget[i].toFixed(2)
      }
      this.$refs.line2.date = date
      this.$refs.line2.budget = budget
      this.$refs.line2.draw()
    },
    // 初始化地图子组件中的数据
    initMapData () {
      var data = {
        '济南市': ['济南市', 0, 0, 0],
        '青岛市': ['青岛市', 0, 0, 0],
        '淄博市': ['淄博市', 0, 0, 0],
        '枣庄市': ['枣庄市', 0, 0, 0],
        '潍坊市': ['潍坊市', 0, 0, 0],
        '烟台市': ['烟台市', 0, 0, 0],
        '临沂市': ['临沂市', 0, 0, 0],
        '东营市': ['东营市', 0, 0, 0],
        '德州市': ['德州市', 0, 0, 0],
        '济宁市': ['济宁市', 0, 0, 0],
        '菏泽市': ['菏泽市', 0, 0, 0],
        '聊城市': ['聊城市', 0, 0, 0],
        '滨州市': ['滨州市', 0, 0, 0],
        '泰安市': ['泰安市', 0, 0, 0],
        '日照市': ['日照市', 0, 0, 0],
        '威海市': ['威海市', 0, 0, 0]
      }
      for (var i = 0, len = this.provinceProjects.length; i < len; i++) {
        var location = this.provinceProjects[i]['location']
        if (location !== '') {
          data[location][1] += 1
          data[location][3] += 1
        }
      }
      for (i = 0, len = this.cityProjects.length; i < len; i++) {
        location = this.cityProjects[i]['location']
        if (location !== '') {
          data[location][2] += 1
          data[location][3] += 1
        }
      }
      var mapdata = []
      for (var index in data) {
        mapdata.push(data[index])
      }
      this.$refs.map.mapData = mapdata
      this.$refs.map.draw()
    },
    // 初始化饼图子组件中的数据
    async initPie () {
      var categories = []
      await this.$axios
        .get('/categories')
        .then(resp => {
          categories = resp.data
        })
        .catch(resp => {
          console.log('获取关键字列表失败')
        })
      var budget = new Array(categories.length)
      var tmp = ''
      for (var i = 0, len = this.provinceProjects.length; i < len; i++) {
        tmp = this.provinceProjects[i]['category']
        budget[categories.indexOf(tmp)] = (budget[categories.indexOf(tmp)] === undefined) ? 0 : budget[categories.indexOf(tmp)] + this.provinceProjects[i]['budget']
      }
      var data = []
      for (i = 0; i < categories.length; i++) {
        data.push({name: categories[i], value: budget[i]})
      }
      this.$refs.pie1.id = 'provincePie'
      this.$refs.pie1.title = '近一周省级项目采购金额分布图'
      this.$refs.pie1.data = data
      this.$refs.pie1.draw()
      budget = new Array(categories.length)
      tmp = ''
      for (i = 0, len = this.cityProjects.length; i < len; i++) {
        tmp = this.cityProjects[i]['category']
        budget[categories.indexOf(tmp)] = (budget[categories.indexOf(tmp)] === undefined) ? 0 : budget[categories.indexOf(tmp)] + this.cityProjects[i]['budget']
      }
      data = []
      for (i = 0; i < categories.length; i++) {
        data.push({name: categories[i], value: budget[i]})
      }
      this.$refs.pie2.id = 'cityPie'
      this.$refs.pie2.title = '近一周市级项目采购金额分布图'
      this.$refs.pie2.data = data
      this.$refs.pie2.draw()
    },
    // 初始化表格
    initForm () {
      this.$refs.form1.recentData = this.cityProjects
    }
  }
}
</script>

<style>
</style>
