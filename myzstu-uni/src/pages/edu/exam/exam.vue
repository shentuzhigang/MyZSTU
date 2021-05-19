<template>
  <view class="container">
    <view class="examlist" v-if="examlist.length > 0">
      <view class="exam" v-for="item in examlist" :key="item.kcmc">
        <view class="examname">{{item.kcmc}}</view>
        <view class="examinfo">
          <view>考试时间 : {{item.kssj}}
            <view v-if="item.kssj.length == 1">暂未安排</view>
          </view>
          <view>考试地点 : {{item.cdmc}}
            <view v-if="item.cdmc.length  == 1">暂未安排</view>
          </view>
          <view>考试座位 : {{item.zwh}}
            <view v-if="item.zwh.length == 1">暂未安排</view>
          </view>
        </view>
      </view>
    </view>
    <view v-else class="no-exam-box">
      本学期暂无考试
    </view>
  </view>
</template>

<script>
  const app = getApp()

  import {
    getExams
  } from '@/api/edu.js'

  export default {
    data() {
      return {
        examlist: []
      }
    },
    mounted() {
      uni.showLoading({
        title: '拼命加载中...',
      })
      getExams().then(exams => {
        if (exams.length <= 0) {
          uni.hideLoading()
        } else {
          this.examlist = exams
          uni.hideLoading()
        }
      }).catch(err => {
        uni.hideLoading()
        uni.showToast({
          title: '无法获取考试信息，请稍后再试',
          icon: 'none',
          duration: 1500
        })
      })
    },
    methods: {

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

<style lang="scss">
  page {
    background: #FFF;
    padding-bottom: 30rpx;
  }

  .container {
    .examlist {
      width: 100%;
      display: flex;
      flex-direction: column;

      .exam {
        width: 96%;
        display: flex;
        flex-direction: column;
        color: #000;
        font-size: 32rpx;
        margin: 10rpx auto;

        .examname {
          height: 65rpx;
          line-height: 65rpx;
          background: #31c27c;
          border: 1px solid #31c27c;
          border-top-left-radius: 12rpx;
          border-top-right-radius: 12rpx;
          color: #fff;
          padding: 0 10rpx;
        }

        .examinfo {
          display: flex;
          flex-direction: column;
          border: 1px solid #31c27c;
          background: #fff;
          border-bottom-left-radius: 12rpx;
          border-bottom-right-radius: 12rpx;
          padding: 10rpx;

          view {
            line-height: 65rpx;
          }
        }
      }
    }

    .no-exam-box {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100%;
    }
  }
</style>
