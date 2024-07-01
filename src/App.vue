<template>
    <div id="app">
        <EchartMap :mapData="mapData" style="width: 100%;height: 100vh" :mapOption="{
            'title': '多源灾情数据管理服务系统',
            'titleColor': 'pink'
        }"></EchartMap>
    </div>
</template>

<script>
import EchartMap from './components/EchartMap'
export default {
    components: {
        EchartMap
    },
    data() {
        return {
            mapData: [
                {"Magnitude": 3.4,
                "Time": "2023/12/20 0:32",
                "Latitude": 35.76,
                "Longitude": 102.78,
                "Depth": 9,
                "Location": "甘肃临夏州积石山县", 
                "Details": "https://news.ceic.ac.cn/CD20231220003253.html"
                },
                {
                "Magnitude": 6.2,
                "Time": "2023/12/18 23:59",
                "Latitude": 35.7,
                "Longitude": 102.79,
                "Depth": 10,
                "Location": "甘肃临夏州积石山县",
                "Details":"https://news.ceic.ac.cn/CC20231218235930.html"
                },
                {
                "Magnitude": 3.1,
                "Time": "2023/12/19 21:49",
                "Latitude": 28.34,
                "Longitude": 104.94,
                "Depth": 8,
                "Location": "四川宜宾市长宁县",
                "Details":"https://news.ceic.ac.cn/CD20231219214948.html"
                },
                {
                "Magnitude": 3.4,
                "Time": "2023-11-28 07:30:13",
                "Latitude": 25.46,
                "Longitude": 105.75,
                "Depth": 10,
                "Location": "贵州黔西南州贞丰县",
                "Details":"https://news.ceic.ac.cn/CD20231128073014.html"
                },
                {
                "Magnitude": 3.1,
                "Time": "2023-12-17 12:17:19",
                "Latitude": 37.01,
                "Longitude": 76.23,
                "Depth": 80,
                "Location": "新疆喀什地区叶城县",
                "Details":"https://news.ceic.ac.cn/CD20231217121720.html"
                },
                {
                "Magnitude": 3.7,
                "Time": "2023/11/28 6:15:47",
                "Latitude": 28.6,
                "Longitude": 87.82,
                "Depth": 10,
                "Location": "西藏日喀则市萨迦县",
                "Details":"https://news.ceic.ac.cn/CD20231128061547.html"
                },
                {
                "Magnitude": 3,
                "Time": "2023-11-26 23:33:59",
                "Latitude": 30.27,
                "Longitude": 96.96,
                "Depth": 10,
                "Location": "西藏昌都市八宿县",
                "Details":"https://news.ceic.ac.cn/CD20231126233359.html"
                },
                {
                "Magnitude": 4,
                "Time": "2023/11/24 13:33:11",
                "Latitude": 38.01,
                "Longitude": 106.2,
                "Depth": 13,
                "Location": "宁夏银川市灵武市",
                "Details":"https://news.ceic.ac.cn/CC20231124133311.html"
                },
                {
                "Magnitude": 4,
                "Time": "2023/11/23 0:13:13",
                "Latitude": 39.51,
                "Longitude": 77.23,
                "Depth": 17,
                "Location": "新疆喀什地区伽师县",
                "Details":"https://news.ceic.ac.cn/CD20231123001313.html"
                },
                {
                "Magnitude": 3.7,
                "Time": "2023-11-30 03:44:10",
                "Latitude": 37.53,
                "Longitude": 112.47,
                "Depth": 28,
                "Location": "山西太原市清徐县",
                "Details":"https://news.ceic.ac.cn/CC20231130034410.html"
                },
                {
                "Magnitude": 4.1,
                "Time": "2023-11-30 19:25:40",
                "Latitude": 40.20,
                "Longitude": 106.13,
                "Depth": 21,
                "Location": "内蒙古阿拉善盟阿拉善左旗",
                "Details":"https://news.ceic.ac.cn/CD20231130192540.html"
                },
                {
                "Magnitude": 3.6,
                "Time": "2023-12-02 02:16:23",
                "Latitude": 	24.25,
                "Longitude": 98.0,
                "Depth": 10,
                "Location": "云南德宏州芒市",
                "Details":"https://news.ceic.ac.cn/CD20231202021624.html"
                },
                {
                "Magnitude": 3.8,
                "Time": "2023-12-07 23:01:45",
                "Latitude": 34.9,
                "Longitude": 119.86,
                "Depth": 8,
                "Location": "江苏连云港市赣榆区海域",
                "Details":"https://news.ceic.ac.cn/CC20231207230145.html"
                },
                {
                "Magnitude": 3.0,
                "Time": "2023-12-14 05:21:10",
                "Latitude": 39.98,
                "Longitude": 118.04,
                "Depth": 9,
                "Location": "河北唐山市遵化市",
                "Details":"https://news.ceic.ac.cn/CC20231214052111.html"
                },

            ]
        }
    }
}
</script>

<style>
* {
    margin: 0;
    padding: 0;
}
</style>
