<template>
  <view>
    <scroll-view scroll-y="true" class="scroll">
      <view class="header">
      </view>
      <view class="container">
        <view class="text-box">
          <text>{{text}}</text>
        </view>
        <view class="team-card">
          <view class="team-card-head">Coded By MyZSTU团队 </view>
          <image alt='LOGO暂无' class="slide-image" :mode='mod[1]' @load='imageLoad'>
          </image>
          <view class="tip-text">官方微信公众号: 暂无</view>
          <view class="tip-text">工作室: 暂无</view>
        </view>
        <view class="team-card">
          <view class="team-card-head">Designed by 浙江理工大学全媒体中心(ZSTUMC)</view>
          <image src="/static/images/QMTCenter.jpg" class="slide-imageqmt" :mode='mod[1]' @load='imageLoad'></image>
          <view class="tip-text">官方微信公众号: zstumc</view>
          <view class="tip-text">工作室: 一号楼北楼531/545</view>
        </view>
        <view class="team-card">
          <view class="team-card-head">Tested by 浙江理工大学计算机协会(ZSTUCA)</view>
          <image src="/static/images/ZSTUCA_LOGO.png" class="slide-imagezstuca" :mode='mod[1]' @load='imageLoad'>
          </image>
          <view class="tip-text">官方微信公众号: 浙理计协</view>
          <view class="tip-text">工作室: 信息学院</view>
        </view>
        <view style="width:100%;position:absolute;bottom:0;">
          <official-account></official-account>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script>
  const texts = [
    '嗨！很高兴你可以看到这里',
    '我们在这里将会告诉你MyZTSU的前世今生',
    '......'
  ]
  export default {
    data() {
      return {
        text: '',
        mod: [
          'aspectFit',
          'widthFix',
        ]
      }
    },
    mounted() {
      this.text = texts.join('\n')
    },
    methods: {
      imageLoad(e) {
        var res = wx.getSystemInfoSync();
        var imgwidth = e.detail.width,
          imgheight = e.detail.height,
          ratio = imgwidth / imgheight;
        this.bannerHeight = res.windowWidth / ratio
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
    padding-bottom: 15rpx;
  }

  .scroll {
    height: 1800rpx;
    z-index: 100;
  }

  .header {
    height: 15rpx;
  }

  .container {
    background: #FFF;
    text-align: center;
    width: 100%;
    display: flex;
    flex-direction: column;
  }

  .text-box {
    margin-bottom: 35px;
    padding: 20px 0;
    display: flex;
    min-height: 150px;
    background-color: #FFFFFF;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-size: 15px;
    color: #353535;
    line-height: 2em;
  }

  .slide-imageqmt {
    width: 90%;
    margin: 20rpx auto;
  }

  .slide-imagezstuca {
    width: 50%;
    margin: 20rpx auto;
  }
</style>

<style lang="scss">
  .team-card {
    font-size: 36rpx;
    margin-bottom: 50rpx;

    .team-card-head {
      text-align: left;
      margin-left: 4rpx;
    }

    .tip-text {
      font-size: 32rpx;
      padding: 15rpx;
    }
  }
</style>
