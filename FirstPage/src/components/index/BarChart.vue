<template>
  <div :id="id"></div>
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
        title: { text: this.title },
        tooltip: {},
        xAxis: {
          data: this.location
        },
        yAxis: {},
        series: [{
          name: '销量',
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
div {
  position: center;
  width: 100%;
  height: 100%;
}
</style>
