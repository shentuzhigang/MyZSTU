<template>
  <view class="img_box">
    <map :latitude="latitude" :longitude="longitude" scale="18" :polyline="polyline"></map>
  </view>
</template>

<script>
  import {
    stuExerciseRecordDetail
  } from '@/api/sports.js'
  export default {
    data() {
      return {
        runid: -1,
        latitude: 39.96491,
        longitude: 116.31604,
        polyline: [{
          points: [],
          color: "#31c27c",
          width: 10,
          arrowLine: true,
          borderWidth: 2 //线的边框宽度，还有很多参数，请看文档 
        }]
      };
    },
    mounted() {
      if (this.runid != -1) {
        stuExerciseRecordDetail(this.runid).then((data) => {
          this.latitude = data[0][1]
          this.longitude = data[0][0]
          let points = []
          data.forEach(item => {
            points.push({
              latitude: item[1],
              longitude: item[0]
            })
          })
          this.polyline = [{
            points: points,
            color: "#31c27c",
            width: 10,
            arrowLine: true,
            borderWidth: 2 //线的边框宽度，还有很多参数，请看文档 
          }]
        })
      }
    },
    onLoad: function(option) {
      this.runid = option.runid
    }
  }
</script>

<style lang="scss">
  .img_box {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;

    map {
      width: 100%;
      height: 100%;
    }
  }
</style>
