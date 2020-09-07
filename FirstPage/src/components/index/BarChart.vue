<template>
  <div :id="id" class="bar"></div>
</template>

<script>
export default {
  name: 'BarChart',
  data () {
    return {
      id: '',
      title: '',
      location: [],
      data: []
    }
  },
  methods: {
    draw () {
      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById(this.id))
      // 绘制图表
      let option = {
        title: {
          text: this.title,
          left: 'center'
        },
        tooltip: {},
        xAxis: {
          data: this.location,
          axisLabel: {
            interval: 0
          }
        },
        yAxis: {
          min: 0,
          max: 50
        },
        series: [{
          name: '采购数',
          type: 'bar',
          data: this.data
        }]
      }
      this.myChart.setOption(option)
    }
  }
}
</script>
<style>
.bar {
  width: 100%;
  height: 100%;
}
</style>
