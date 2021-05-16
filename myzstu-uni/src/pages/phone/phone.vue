<template>
	<view>
		<view class="header">
		</view>
		<view class="container" v-if="phone">
		  <view class="category">生活类</view>
		  <view v-for="item in life" :key="item.name" class="item" @tap='call'>
		    <view class="name">{{item.name}}</view>
		    <view class="number"  >{{item.number}}</view>
		  </view>
		  <view class="category">办公类</view>
		  <view v-for="item in work" :key="item.name" class="item" @tap='call'>
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
				phone: false,
				life: [],
				work: [],
				other: []
			}
		},
		methods: {
			/**
			 * 自定义函数
			 */
			showNumber(data) {
			  var life = []
			  var work = []
			  //var other = []
			  for (var i = 0; i < data.length; i++) {
			    if (data[i].type == 'life') {
			      life.push(data[i])
			    }
			    else if (data[i].type == 'work') {
			      work.push(data[i])
			    }
			    /*else {
			      other.push(data[i])
			    }*/
			  }
			  wx.setStorageSync('life', life)
			  wx.setStorageSync('work', work)
			  //wx.setStorageSync('other', other)
			  this.setData({
			    phone: wx.getStorageSync('phone'),
			    life: life,
			    work: work,
			    //other: other
			  })
			},
			call (e) {
			  var number = e.currentTarget.dataset.item.number
			  wx.makePhoneCall({
			    phoneNumber: number,
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
      var that=this
      try {
        var phone = wx.getStorageSync('phone')
        if (phone == true) {
          this.setData({
            phone: phone,
            life: wx.getStorageSync('life'),
            work: wx.getStorageSync('work'),
            //other: wx.getStorageSync('other')
          })
        }else{
          wx.request({
            url: app.globalData.serverUrl + '/wx/phones',
            method: 'GET',
            success: function (res) {
              if(res.statusCode == 200 && res.data.data != null){
                console.log(app.globalData.serverUrl + '/phones'),
                console.log(res);
                wx.setStorageSync('phone', true)
                that.showNumber(res.data.data)
              }else{
                wx.setStorageSync('phone', false)
                wx.showToast({
                  title: '加载失败',
                  icon: 'none',
                  duration: 1500
                })
              }
            },
            fail: function (err) {
              wx.setStorageSync('phone', false)
              wx.showToast({
                title: '加载失败',
                icon: 'none',
                duration: 1500
              })
    
            }
          })
        }
      } catch (e) {
        console.log(e);
      }
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
    width:100%; 
  }
  .category{
    font-size:45rpx;
    text-align:left;
    padding:20rpx;
    color:#000;
  }
  .item{
    width:90%;
    color:#000;
    display: flex;
    text-align:center;
    background:#fff;
    border:1px solid #31c27c;
    border-radius:10rpx;
    padding:20rpx;
    margin-bottom:15rpx;
  }
  .item .name{
    margin-right:auto;
  }
</style>
