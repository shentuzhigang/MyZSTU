<template>
	<view>
		<view class="header">
		</view>
		<view class="container">
		  <image :src="imgUrls[0]" class="slide-image" :mode='mod[1]' bindtap="previewImage"></image>
		  <image :src="imgUrls[1]" style="margin-bottom:30rpx;" class="slide-image" :mode='mod[1]' bindtap="previewImage"></image>
		</view>
		 
	</view>
</template>

<script>
  const app = getApp()
	export default {
		data() {
			return {
				imgUrls: [
				  app.globalData.serverUrl + '/static/images/schcalendar-1.jpg', 
				  app.globalData.serverUrl + '/static/images/schcalendar-2.jpg'
				],
				src1: app.globalData.serverUrl +'/static/images/schcalendar-1.jpg',
				src2: app.globalData.serverUrl + '/static/images/schcalendar-2.jpg',
				mod: [
				  'aspectFit',
				  'widthFix',
				]
			}
		},
		methods: {
			previewImage: function (e) {
			  wx.previewImage({
			    urls: this.data.imgUrls
			  })
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
    background: #fff;
    padding-bottom: 15rpx;
  }
  .container{
    width:100%;
  }
  .header{
    height: 15rpx;
  }
  .slide-image{ 
    width:94%; 
    margin:20rpx auto;
  }
  .slide-image{ 
    width:96%; 
    margin:20rpx auto 0;
  }
</style>
