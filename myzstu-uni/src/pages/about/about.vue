<template>
  <view>
    <view class="header">
    </view>
    <view class="container">
      <image src="/static/images/QMTCenter.jpg" 
        class="slide-imageqmt" 
        :mode='mod[1]' 
        @load='imageLoad'></image>
        <view id="tip">官方微信公众号: zstumc</view>
        <view id="tip">工作室: 一号楼北楼531/545</view>
      <image src="/static/images/ZSTUCA_LOGO.png" 
        class="slide-imagezstuca" 
        :mode='mod[1]' 
        @load='imageLoad'></image>
      <view id="tip">官方微信公众号: 浙理计协</view>
      <view id="tip">工作室: 信息学院</view>
      <view style="width:100%;position:absolute;bottom:0;">
        <official-account></official-account>
      </view> 
    </view>
  </view>
</template>

<script>
	export default {
		data() {
			return {
				mod: [
				  'aspectFit',
				  'widthFix',
				]
			}
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
    onLoad: function (options) {
      
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
      
    },
    
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
      
    },
    
    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
      
    },
    
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {
      
    },
    
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {
      
    },
    
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
      
    },
    
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {
      
    }
	}
</script>

<style>
  page{
    background:#FFF;
    padding-bottom: 15rpx;
  }
  .header{
    height: 15rpx;
  }
  .container{
    position:fixed;
    background:#FFF;
    text-align: center;
    width:100%;
    display:flex;
    flex-direction:column;
  }
  .slide-imageqmt{ 
    width:90%; 
    margin:20rpx auto;
  }
  .slide-imagezstuca{ 
    width:50%; 
    margin:20rpx auto;
  }
  #tip{
    font-size:40rpx;
    padding:15rpx;
  }
</style>
