<template>
	<view>
		<view class="header">
		</view>
		<view class="container">
      <view class="category" v-for="i in list" :key="i.title" v-if="i.enable">
        <view class="title">{{i.title}}</view>
        <view class="btn_group">
          <navigator class="btn" v-for="j in i.items" :key="j.url" v-if="j.enable" :url="j.url">
            <image class="btn_icon" :src="j.icon"></image>
            <view class="btn_info">{{j.text}}</view>
          </navigator>
        </view>
      </view>
    </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [
          {
            title: "常用功能",
            enable: true,
            items:[
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/course.svg',
                text: '课表查询',
                enable: true
              },
              {
                url:'/pages/schcalendar/schcalendar',
                icon: '/static/images/calendar.svg',
                text: '浙理校历',
                enable: true
              },
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/dumbbells.svg',
                text: '锻炼查询',
                enable: false
              }
            ]
          },
          {
            title: "教务查询",
            enable: true,
            items: [
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/course.svg',
                text: '课表查询',
                enable: true
              },
              {
                url:'/pages/edu/recommend/recommend',
                icon: '/static/images/recommend.svg',
                text: '推荐课表',
                enable: true
              },
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/exam.svg',
                text: '考试查询',
                enable: true
              },
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/score.svg',
                text: '成绩查询',
                enable: true
              }
            ]
          },
          {
            title:"体育查询",
            enable: false,
            items:[
              {
                url:'/pages/edu/course/course',
                icon: '/static/images/dumbbells.svg',
                text: '锻炼查询',
                enable: false
              }
            ]
          }
        ]
			}
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
  .container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 34rpx;
    .category {
      .title {
        width: 700rpx;
        color: #31c27c;
        padding-left: 10rpx;
        border-left: 8rpx solid #31c27c;
        margin: 10rpx 0 15rpx 20rpx;
        font-weight: bold;
      }
      .btn_group {
        display: flex;
        flex-direction: row;
        justify-content: start;
        flex-wrap: wrap;
        width: 700rpx;
        margin: 15rpx 20rpx;
        .btn {
          margin-top: 15rpx;
          margin-bottom: 10rpx;
          margin-left: 10rpx;
          text-align: center;
          width: 130rpx;
          .btn_icon {
            width: 70rpx;
            height: 70rpx;
            padding: 10rpx;
            border-radius: 45rpx;
            background: #fff;
          }
          
          .btn_info {
            color: #000;
            font-size: 28rpx;
          }
        }
      }
    }
  }
</style>
