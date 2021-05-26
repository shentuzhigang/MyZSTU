<template>
  <view class="container">
    <picker mode="selector" :value="eventno" :range="eventList" @change="pickerChange" range-key=eventname>
      <view class="picker" v-if="eventList[eventno] !=  undefined ">
        {{eventList[eventno].eventname}}
        <image class="down" src="/static/images/down.svg"></image>
      </view>
    </picker>
    <view class="record-container" v-if="eventno!=-1">
      <view style="text-align: center;"><text>当前总公里数：{{sum==null?0:sum}}\n当前有效公里数：{{result==null?0:result}}</text></view>
      <view>
        <view class="record-item" v-for="item in ApprunData" :key="item.id">
          <text>日期：{{item.dates}}\n距离：{{item.distances + ' km'}}\n速度：{{item.mysql_speed + ' km/min'}}\n状态：{{item.servresp == 'success'?'有效':item.servresp}}</text>
          <button class="btn" @tap="submit(item.id)" type="primary">
            <text space="emsp">回 放</text>
          </button>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  import {
    stuInfoAndEvent,
    stuSelExerciseInfo
  } from '@/api/sports.js'
  export default {
    data() {
      return {
        eventno: -1,
        eventList: [],
        ApprunData: [],
        sum: 0,
        result: 0
      }
    },
    mounted() {
      stuInfoAndEvent().then((data) => {
        this.eventList = data.eventInfo
        this.eventno = 0
      })
    },
    methods: {
      pickerChange: function(e) {
        this.eventno = e.detail.value
      },
      submit:function(id){
        uni.navigateTo({
          url: 'exercise-record-detail?runid=' + id
        })
      }
    },
    watch: {
      eventno: {
        handler(val, oldVal) {
          stuSelExerciseInfo(this.eventList[val].eventno).then(data => {
            this.sum = data.stuExerciseCount[0].sum
            this.result = data.stuExerciseCount[0].result
            this.ApprunData = data.stuApprunData
          })
        }
      }
    }
  }
</script>

<style lang="scss">
  .container {
    width: 100%;
    color: #000;
    font-size: 32rpx;

    .picker {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      border: 1px solid #31c27c;
      background: #fff;
      height: 80rpx;
      line-height: 80rpx;
      border-radius: 12rpx;
      text-align: center;
      padding: 0 20rpx;
      letter-spacing: 5rpx;

      .down {
        margin-left: 20rpx;
        width: 40rpx;
        height: 40rpx;
      }
    }

    .record-container {
      .record-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-direction: row;
        border-top: 1rpx solid #31c27c;
        margin-top: 4rpx;
        padding: 10rpx;
        
        .btn {
          background-color: #31c27c;
          font-size: 24rpx;
          margin-right: 10rpx;
        }
      }
    }
  }
</style>
