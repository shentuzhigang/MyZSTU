<template>
    <view class="content">
        <image class="logo" src="/static/logo.png"></image>
        <view>
            <text class="title">{{title}}</text>
        </view>
    </view>
</template>

<script>
  export default {
    setup() {
      return {
        title: 'Hello MyZSTU'
      }
    },
    onShow:function(){
      setTimeout(function () {
        uni.reLaunch({
          url: '../home/home',
        }) 
      },1000)
    }
  }
</script>

<style>
    .content {
        text-align: center;
        height: 400upx;
    }

    .logo {
        height: 200upx;
        width: 200upx;
        margin: 200rpx auto 50rpx auto;
    }

    .title {
        font-size: 36upx;
        color: #8f8f94;
    }
</style>
