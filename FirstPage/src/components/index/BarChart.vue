<template>
  <div :id="id" style="width: 300px; height: 300px"></div>
</template>

<script>
export default {
  name: 'BarChart',
  data () {
    return {
    }
  },
  props: ['id', 'location', 'title', 'data'],
  mounted () {
    this.drawLine()
  },
  methods: {
    drawLine () {
      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById(this.id))
      // 绘制图表
      this.option = ({
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
      })
      this.myChart.setOption(this.option)
    }
  }
}
</script>
