<template>
  <div id="main"></div>
</template>

<script>
// 引入地图数据
import SHANDONG from '../../assets/geoJSON/shandong.json'
export default {
  name: 'ShandongMap',
  data () {
    return {
      // mapData: ['城市名','省级项目数', '市级项目数', '合计'],
      mapData: []
    }
  },
  methods: {
    draw () {
      // 注册地图
      this.$echarts.registerMap('山东', SHANDONG)
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById('main'))
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            var pvalue = params.data['1']
            var cvalue = params.data['2']
            var total = pvalue + cvalue
            return params.name + '<br/>' + '省级项目数' + ' : ' + pvalue + '<br/>' + '市级项目数' + ' : ' + cvalue + '<br/>' + '合计' + ' : ' + total
          }
        },
        visualMap: {
          min: 0,
          max: 80,
          text: ['High', 'Low'],
          realtime: false,
          calculable: true,
          inRange: {
            color: ['lightskyblue', 'yellow', 'orangered']
          }
        },
        dataset: {
          source: this.mapData
        },
        series: [
          {
            name: '山东地图',
            type: 'map',
            mapType: '山东',
            label: {show: true}
          }
        ]
      }
      myChart.setOption(option)
    }
  }
}
</script>

<style>
#main {
  position: center;
  width: 100%;
  height: 100%;
}
</style>
