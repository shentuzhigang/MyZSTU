<template>
	<view class="container">
	  <view class="userinfo">
      <button class="buttonstyle" 
        v-if="!hasUserProfile" 
        open-type="getUserProfile" 
        @tap="getUserProfile" 
        lang="zh_CN">
        授权登录
      </button>
      <block v-else>
        <image @tap="bindViewTap" 
          class="userinfo_avatar"
          :src="userInfo.avatarUrl" 
          background-size="cover">
        </image>
        <view class="information">
          <text class="userinfo_nickname">
            {{userInfo.nickName}}
          </text>
          <text class="userinfo_address">
          {{userInfo.country}} · {{userInfo.province}} · {{userInfo.city}}
          </text>
        </view>
      </block> 
      </view>
      <view class="btn_group">
        <navigator class="btn" url="/pages/userform/userform" hover-class="navigator-hover">
          <image class="btn_icon" src="/static/images/binding.svg"></image>
          <view class="btn_info">信息绑定</view>
          <image class="btn_to" src="/static/images/arrow.svg"></image>
        </navigator>
        <navigator class="btn" url="/pages/schcalendar/schcalendar">
          <image class="btn_icon" src="/static/images/calendar.svg"></image>
          <view class="btn_info">浙理校历</view>
          <image class="btn_to" src="/static/images/arrow.svg"></image>
        </navigator>
        <navigator class="btn" url="/pages/phone/phone">
          <image class="btn_icon" src="/static/images/phone.svg"></image>
          <view class="btn_info">常用电话</view>
          <image class="btn_to" src="/static/images/arrow.svg"></image>
        </navigator>
        <view class="btn" @click="clean">
          <image class="btn_icon" src="/static/images/clean.svg"></image>
          <view class="btn_info">清除缓存</view>
          <image class="btn_to" src="/static/images/arrow.svg"></image>
        </view>
        <navigator style="margin-bottom:30rpx;" class="btn" url="/pages/about/about">
          <image class="btn_icon" src="/static/images/aboutus.svg"></image>
          <view class="btn_info">关于我们</view>
          <image class="btn_to" src="/static/images/arrow.svg"></image>
        </navigator>
      </view>  
	  </view>
	</view>
</template>

<script>
  const app = getApp()
	export default {
		data() {
			return {
				userInfo: {},
				hasUserProfile: false
			}
		},
		methods: {
      getUserProfile(e) {
        // https://developers.weixin.qq.com/community/develop/doc/000cacfa20ce88df04cb468bc52801?idescene=6
        uni.getUserProfile({
          lang: 'zh_CN',
          desc: '获取用户信息',
          success: res => {
            // 可以将 res 发送给后台解码出 unionId
            console.log(res)
            uni.setStorageSync('UserProfile',res.userInfo);
            app.globalData.userInfo = res.userInfo
            this.userInfo = res.userInfo
            this.hasUserProfile = true
          }
        })
      },
			clean(){
			  var openid = uni.getStorageSync('openid');
			  uni.clearStorageSync()
			  uni.setStorageSync('openid', openid);
			  uni.showToast({
			    title: '清理完成',
			    icon: "none",
			    mask: true,
			    duration: 1500,
			    success: function () {
			      setTimeout(function () {
			        uni.reLaunch({
			          url: '/pages/index/index',
			        })
			      }, 1000)
			    }
			  })
			}
		},
    /**
     * 生命周期函数--监听页面加载
     */
    onShow: function (options) {
      if (app.globalData.userInfo) {
       this.userInfo = app.globalData.userInfo
       this.hasUserProfile = true
      } else if (this.canIUse) {
        app.userInfoReadyCallback = res => {
          this.userInfo = res.userInfo
          this.hasUserProfile = true
        }
      } else {
        uni.getUserProfile({
          lang: 'zh_CN',
          success: res => {
            app.globalData.userInfo = res.userInfo
            this.userInfo = res.userInfo
            this.hasUserProfile = true
          }
        })
      }
    },
    
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
    
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
	.userinfo {
		background:#31c27c;
		width:100%;
		display:flex;
		flex-direction:row;
		align-items:center;
	}
	.buttonstyle{
		width:220rpx;
		height:95rpx;
		line-height:95rpx;
		margin:30rpx auto;
		font-size:36rpx;
		background:#fff;
	}
	.userinfo_avatar {
		margin:35rpx 30rpx 35rpx 40rpx;
		width: 150rpx;
		height: 150rpx;
		border-radius: 50%;
		border:2px solid #fff;
	}
	.information {
		display:flex;
		flex-direction:column;
		align-items:flex-start;
	}
	.userinfo_nickname {
		margin-bottom:24rpx;
		color: #fff;
		font-size:40rpx;
	}
	.userinfo_address {
		color: #fff;
		font-size:32rpx;
	}
	.btn_group {
		display:flex;
		flex-direction: column;
		width:96%;
	}
	.btn{
		display:flex;
		flex-direction:row;
		align-items: center;
		background:#fff;
		margin-top:25rpx;
		border-radius: 10rpx;
		border: 1px solid #F5F5F5;
		box-shadow: 5px 5px 5px #E8E8E8;
		height:130rpx;
	}
	.btn_icon{
		width:75rpx;
		height:75rpx;
		margin-left:30rpx;
	}
	.btn_info{
		margin-left:26rpx;
		font-size: 34rpx;
	}
	.btn_to{
		margin-top:4rpx;
		width:40rpx;
		height:40rpx;
		position:absolute;
		right:55rpx;
	}
</style>
