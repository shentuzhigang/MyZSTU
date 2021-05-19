<template>
  <view>
    <view class="header">
    </view>
    <view class="container">
      <view class="category" v-if="life.length != 0">生活类</view>
      <view v-for="item in life" :key="item.number" class="item" @tap='call'>
        <view class="name">{{item.name}}</view>
        <view class="number">{{item.number}}</view>
      </view>
      <view class="category" v-if="work.length != 0">办公类</view>
      <view v-for="item in work" :key="item.number" class="item" @tap='call'>
        <view class="name">{{item.name}}</view>
        <view class="number">{{item.number}}</view>
      </view>
      <view class="category" v-if="other.length != 0">其他</view>
      <view v-for="item in other" :key="item.number" class="item" @tap='call'>
        <view class="name">{{item.name}}</view>
        <view class="number">{{item.number}}</view>
      </view>
    </view>
  </view>
</template>

<script>
  const app = getApp()
  export default {
    data() {
      return {
        life: [],
        work: [],
        other: []
      }
    },
    mounted() {
      try {
        var phones = uni.getStorageSync('phones')
        if (phones != null && phones != '') {
          this.showNumber(phones)
        } else {
          uniCloud.callFunction({
            name: 'phones'
          }).then(res => {
            if (res.result.code == 0 && res.result.phones != null) {
              console.log(res);
              uni.setStorageSync('phones', res.result.phones)
              this.showNumber(res.result.phones)
            } else {
              uni.showToast({
                title: '加载失败',
                icon: 'none',
                duration: 1500
              })
            }
          }).catch((err) => {
            uni.showToast({
              title: '加载失败',
              icon: 'none',
              duration: 1500
            })
          })
        }
      } catch (e) {
        console.log(e);
      }
    },
    methods: {
      /**
       * 自定义函数
       */
      showNumber(data) {
        this.life = data.life
        this.work = data.work
        this.other = data.other
      },
      call(e) {
        var number = e.currentTarget.dataset.item.number
        uni.phoneNumber = number
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

<style lang="scss">
  page {
    background: #FFF;
    padding-bottom: 15rpx;
  }

  .header {
    height: 15rpx;
  }

  .container {
    width: 100%;

    .category {
      font-size: 45rpx;
      text-align: left;
      padding: 20rpx;
      color: #000;
    }

    .item {
      width: 90%;
      color: #000;
      display: flex;
      text-align: center;
      background: #fff;
      border: 1px solid #31c27c;
      border-radius: 10rpx;
      padding: 20rpx;
      margin-left: 4rpx;
      margin-bottom: 16rpx;

      .name {
        margin-right: auto;
      }
    }
  }
</style>
