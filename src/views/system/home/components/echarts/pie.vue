<template>
  <default-home-card icon="PieChartTwoTone" title="震情信息">
    <div class="echarts-box">
      <div class="pie-main" id="pie-main"></div>
    </div>
  </default-home-card>
</template>
<script setup>
import DefaultHomeCard from "/@/views/system/home/components/default-home-card.vue";
import * as echarts from 'echarts';
import {onMounted} from "vue";
import { earthquakeApi } from '/@/api/system/earthquake/earthquake-api';

onMounted(() => {
  init();
});


function init(){
  let option = {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '1%',
      left: 'center'
    },
    series: [
      {
        name: '数量',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '40',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 27, name: '人员伤亡与失踪' },
          { value: 152, name: '房屋破坏' },
          { value: 72, name: '生命线工程' },
          { value: 54, name: '次生灾害' },
        ]
      }
    ]
  };
  let chartDom = document.getElementById("pie-main");
  if (chartDom) {
    let myChart = echarts.init(chartDom);
    option && myChart.setOption(option);
  }
}
</script>
<style lang='less' scoped>
.echarts-box {
  display: flex;
  align-items: center;
  justify-content: center;
  .pie-main {
    width: 260px;
    height: 260px;
    background: #fff;
  }
}
</style>