<template>
  <div>
    <BarChart ref="bar1" :id="bar1.id" :location="bar1.projects" :title="bar1.title" :data="bar1.data"></BarChart>
    <PieChart ref="pie1" :id="pie1.id" :name="pie1.name" :data="pie1.data"></PieChart>
    <BarChart ref="bar2" :id="bar2.id" :location="bar2.projects" :title=" bar2.title" :data="bar2.data"></BarChart>
    <LineChart ref="line1" :id="line1.id" :name="line1.name" :xData="line1.xData" :yData="line1.yData"></LineChart>
    <vue-seamless-scroll :data="listData" class="seamless-warp" :class-option="optionHover">
      <ul class="item">
        <li v-for="item in listData" v-bind:key="item">
          <span class="title" v-text="item.title"></span><span class="date" v-text="item.date"></span>
        </li>
      </ul>
    </vue-seamless-scroll>
  </div>
</template>

<script>
import BarChart from './BarChart'
import PieChart from './PieChart'
import LineChart from './LineChart'
export default {
  name: 'AppIndex',
  components: {LineChart, BarChart, PieChart},
  data () {
    return {
      listData: [{
        'title': '无缝滚动第一行无缝滚动第一行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第二行无缝滚动第二行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第三行无缝滚动第三行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第四行无缝滚动第四行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第五行无缝滚动第五行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第六行无缝滚动第六行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第七行无缝滚动第七行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第八行无缝滚动第八行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第九行无缝滚动第九行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第十行无缝滚动第九行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第十一行无缝滚动第九行',
        'date': '2017-12-16'
      }, {
        'title': '无缝滚动第十二行无缝滚动第九行',
        'date': '2017-12-16'
      }],
      bar1: {
        id: 'bar1',
        projects: ['z', 'b', 'c', 'd'],
        title: 'tuxue',
        data: [1, 2, 3, 4, 5]
      },
      bar2: {
        id: 'bar2',
        projects: ['z', 'b', 'c', 'd'],
        title: 'qide',
        data: [5, 2, 1, 4, 5]
      },
      pie1: {
        id: 'pie1',
        name: 'bingtu',
        data: [
          {value: 235, name: 'XXXX广告'},
          {value: 274, name: '联盟广告'},
          {value: 310, name: '邮件营销'},
          {value: 335, name: '直接访问'},
          {value: 400, name: '搜索引擎'}
        ]
      },
      line1: {
        id: 'line1',
        name: 'Pinghuaqushitu',
        xData: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        yData: [820, 932, 901, 934, 1290, 1330, 1320]
      }
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
    // this.init()
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
  .seamless-warp {
    height: 160px;
    overflow: hidden;
  }
</style>
