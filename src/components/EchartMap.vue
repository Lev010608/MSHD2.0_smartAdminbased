<template>
    <div id="mapChart" class="chart"></div>
</template>

<script>
import echarts from "echarts";
import axios from 'axios'

export default {
    props: {
        mapData: {
            type: Array, // 根据新的数据格式进行调整
            default: [],
        },
        mapOption: {
            type: Object,
        }
    },
    computed: {
        chartOption() {
            return {
                'title': '地图',
                'titleColor': '#8796B0',
                'backgroundColor': '#2B3648',
                'areaColor': '#262C38',
                'borderColor': '#678EE3',
                'hoverAreaColor': '#16467B',
                'lineColor': 'rgb(192, 158, 255)',
                'trailColor': '#fff',
                'endColor': 'rgb(192, 158, 255)',
                ...this.mapOption
            }
        }
    },
    data() {
        return {
        infoPanelVisible: false,
        info: {}
        };
    },
    methods: {
        mapChart() {
            var myChart = echarts.init(document.getElementById('mapChart'));

            // 添加点击事件监听
            myChart.on('click', (param) => {
                if (param.seriesType === 'scatter' && param.componentType === 'series') {
                    // 处理点击事件，显示信息面板
                    this.showInfoPanel(param.data);
                }
            });

            axios.get("./static/json/map/100000.json", {}).then(response => {
                var chinaJson = response.data;
                echarts.registerMap("china", chinaJson);

                var series = [{
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    zlevel: 2,
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: (param) => {
                                // 在这里自定义显示的文本内容
                                var data = param.data;
                                return `地点：${data.name}`;
                            }
                        }
                    },
                    symbolSize: function (val) {
                        // 根据数据设置符号大小
                        return val[2]/5; // 使用info中的震级信息
                    },

                    itemStyle: {
                        normal: {
                            color: this.chartOption.endColor
                        }
                    },
                    data: this.initData(this.mapData)
                }];

                myChart.setOption({
                    backgroundColor: this.chartOption.backgroundColor,
                    title: {
                        text: this.chartOption.title,
                        left: 20,
                        top: 10,
                        textStyle: {
                            fontSize: 16,
                            fontFamily: 'PingFangSC-Regular',
                            fontWeight: 'lighter',
                            color: this.chartOption.titleColor
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: (param) => {
                            var data = param.data;
                            return data.name + '<br>' + ' 震级: ' + 
                            data.info.Magnitude + '<br>' +
                            '经度：'+ data.info.Longitude + '°<br>' +
                            '纬度：'+ data.info.Latitude + '°<br>' +
                            '深度：'+ data.info.Depth + 'KM<br>';
                        }
                    },
                    
                    geo: {
                        map: 'china',
                        label: {
                            emphasis: {
                                show: false
                            }
                        },
                        roam: true,
                        itemStyle: {
                            normal: {
                                areaColor: this.chartOption.areaColor,
                                borderColor: this.chartOption.borderColor
                            },
                            emphasis: {
                                areaColor: this.chartOption.hoverAreaColor
                            }
                        }
                    },
                    series: series
                });
            });
        },
        showInfoPanel(data) {
            // 根据 data.info 中的信息显示信息面板，你可以自定义实现该方法
            console.log('Clicked Data:', data.info);
            console.log(this.infoPanelVisible);
            // 这里可以调用外部方法或触发组件内部状态，显示灾情信息面板
            // 例如：this.$emit('showInfoPanel', data.info);
            // 或：this.infoPanelData = data.info;
            this.info = data.info;
            this.infoPanelVisible = true;
window.location.href = data.info.Details;
//window.open(data.info.Details, '_blank');

        },
        initData(data) {
            var result = [];
            for (var i = 0; i < data.length; i++) {
                var el = data[i];
                var val = `${el['Longitude']},${el['Latitude']},30`.split(','); // 将经纬度和30连接为字符串数组
                result.push({
                    name: el['Location'],
                    value: val,
                    info: { // 添加灾情信息
                        Magnitude: el['Magnitude'],
                        Time: el['Time'],
                        Latitude: el['Latitude'],
                        Longitude: el['Longitude'],
                        Depth: el['Depth'],
                        Location: el['Location'],
                        Details: el['Details']
                    }
                });
            }
            return result;
        }
    },

    mounted() {
        this.mapChart();
    }
}
</script>

<style scoped>
/* 可以在这里添加样式 */
</style>