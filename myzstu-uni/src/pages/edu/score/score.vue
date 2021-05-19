<template>
  <view class="container">
    <picker mode="selector" :value="yearindex" :range="years" @change="pickerChange">
      <view class="picker" v-if="years[yearindex] !=  undefined ">
        {{years[yearindex]}}学期<image class="down" src="/static/images/down.svg"></image>
      </view>
    </picker>
    <view class="scoreinfo">
      <view class="title">成绩单</view>
      <view class="header">
        <view class="name">课程名</view>
        <view class="credit">学分</view>
        <view class="grade">成绩</view>
        <view class="gpa">绩点</view>
      </view>
      <view class="noscore" v-if="scorelist.length < 1">暂无成绩信息</view>
      <view class="score-item" v-for="item in scorelist" :key="item.courseName">
        <view class="content" v-if="item.xnmmc + '-' + item.xqmmc == years[yearindex]">
          <view class="name">{{item.kcmc}}</view>
          <view class="credit">{{item.xf}}</view>
          <view class="grade">{{item.cj}}</view>
          <view class="gpa">{{item.jd}}</view>
        </view>
      </view>
    </view>
    <view class="scoresum">
      <view class="title">成绩统计</view>
      <view class="content">
        <view class="name">学期总学分</view>
        <view>{{credit_semester}}</view>
      </view>
      <view class="content">
        <view class="name">学期平均绩点</view>
        <view>{{avggpa_semester}}</view>
      </view>
      <view class="content">
        <view class="name">学年总学分</view>
        <view>{{credit_year}}</view>
      </view>
      <view class="content">
        <view class="name">学年平均绩点</view>
        <view>{{avggpa_year}}</view>
      </view>
      <view class="content">
        <view class="name">所有课程总学分</view>
        <view>{{credit_all}}</view>
      </view>
      <view class="content">
        <view class="name">所有课程平均绩点</view>
        <view>{{avggpa_all}}</view>
      </view>
    </view>
  </view>

</template>

<script>
  const app = getApp()

  import {
    getScores
  } from '@/api/edu.js'

  export default {
    data() {
      return {
        yearindex: 0,
        scorelist: []
      }
    },
    computed: {
      years() {
        let y = new Set()
        if (this.scorelist.length > 0) {
          this.scorelist.forEach(item => {
            y.add(item.xnmmc + '-' + item.xqmmc)
          })
        }
        return [...y].sort().reverse()
      },
      credit_all() {
        return this.scorelist.reduce((t, v) =>
          v.ksxzdm == "01" ? t + parseFloat(v.xf) : t, 0)
      },
      avggpa_all() {
        return (this.scorelist.reduce((t, v) =>
          v.ksxzdm == "01" ? t + parseFloat(v.xfjd) : t, 0) / this.credit_all).toFixed(2)
      },
      credit_year() {
        var picker = this.years[this.yearindex]
        if (picker == undefined) {
          return 0.00
        }
        var year = picker.split('-')[0] + '-' + picker.split('-')[1]
        return this.scorelist.reduce((t, v) => {
          if (year == v.xnmmc && v.ksxzdm == "01") {
            return t + parseFloat(v.xf)
          } else {
            return t
          }
        }, 0)

      },
      avggpa_year() {
        var picker = this.years[this.yearindex]
        if (picker == undefined) {
          return 0.00
        }
        var year = picker.split('-')[0] + '-' + picker.split('-')[1]
        var xfjd = this.scorelist.reduce((t, v) => {
          if (year == v.xnmmc && v.ksxzdm == "01") {
            return t + parseFloat(v.xfjd)
          } else {
            return t
          }
        }, 0)
        return (xfjd / this.credit_year).toFixed(2)
      },
      credit_semester() {
        var picker = this.years[this.yearindex]
        if (picker == undefined) {
          return 0.00
        }
        return this.scorelist.reduce((t, v) => {
          if (picker == v.xnmmc + '-' + v.xqmmc && v.ksxzdm == "01") {
            return t + parseFloat(v.xf)
          } else {
            return t
          }
        }, 0)
      },
      avggpa_semester() {
        var picker = this.years[this.yearindex]
        if (picker == undefined) {
          return 0.00
        }
        var xfjd = this.scorelist.reduce((t, v) => {
          if (picker == v.xnmmc + '-' + v.xqmmc && v.ksxzdm == "01") {
            return t + parseFloat(v.xfjd)
          } else {
            return t
          }
        }, 0)
        return (xfjd / this.credit_semester).toFixed(2)
      }
    },
    mounted() {
      uni.showLoading({
        title: '拼命加载中...',
      })
      getScores().then(scores => {
        this.scorelist = scores
        uni.hideLoading()
      }).catch(err => {
        uni.hideLoading()
        uni.showToast({
          title: '无法获取成绩信息，请稍后再试',
          icon: 'none',
          duration: 1500
        })
      })
    },
    methods: {
      pickerChange: function(e) {
        this.yearindex = e.detail.value
      }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
  }
</script>

<style>
  page {
    background: #FFF;
    padding-bottom: 20rpx;
  }

  .container {
    width: 100%;
    color: #000;
    font-size: 32rpx;
  }

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
  }

  .picker .down {
    margin-left: 20rpx;
    width: 40rpx;
    height: 40rpx;
  }

  .scoreinfo {
    width: 95%;
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    border-radius: 12rpx;
    background: #fff;
    margin: 20rpx auto;
    border-bottom: 0;
  }

  .scoreinfo .title {
    text-align: center;
    background: #31c27c;
    height: 70rpx;
    line-height: 70rpx;
    color: #FFF;
    border-bottom: 1px #fff solid;
  }

  .scoreinfo .header {
    display: flex;
    flex-direction: row;
    background: #31c27c;
    color: #FFF;
  }

  .scoreinfo .noscore {
    text-align: center;
    height: 70rpx;
    line-height: 70rpx;
    color: #000;
    border-bottom: 1px #31c27c solid;
  }

  .scoreinfo .content {
    display: flex;
    flex-direction: row;
  }

  .scoreinfo .name {
    width: 400rpx;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-bottom: 1px solid #31c27c;
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    white-space: nowrap;
  }

  .scoreinfo .credit,
  .scoreinfo .grade,
  .scoreinfo .gpa {
    width: 110rpx;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-left: 1px solid #31c27c;
    border-bottom: 1px solid #31c27c;
  }

  .scoreinfo .header .credit,
  .scoreinfo .header .grade,
  .scoreinfo .header .gpa {
    border-left: 1px solid #FFF;
  }

  .scoresum {
    width: 95%;
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    border-radius: 12rpx;
    background: #fff;
    margin: 20rpx auto;
    border-bottom: 0;
  }

  .scoresum .title {
    text-align: center;
    background: #31c27c;
    height: 70rpx;
    line-height: 70rpx;
    color: #FFF;
    border-bottom: 1px #fff solid;
  }

  .scoresum .content {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-bottom: 1px solid #31c27c;
  }

  .scoresum .content .name {
    border-right: 1px solid #31c27c;
  }

  .scoresum .content view {
    width: 50%;
  }
</style>
