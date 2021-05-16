<template>
  <view class="examlist">
    <view class="exam" v-for="item in examlist" :key="item.courseName">
      <p class="examname">{{item.courseName}}</p>
      <view class="examinfo">
        <p>考试时间 : {{item.examTime}}
        <p v-if="item.examTime.length == 1">暂未安排</p>
        </p>
        <p>考试地点 : {{item.examPlace}}
        <p v-if="item.examPlace.length  == 1">暂未安排</p>
        </p>
        <p>考试座位 : {{item.seatNumber}}
        <p v-if="item.seatNumber.length == 1">暂未安排</p>
        </p>
      </view>
    </view>
  </view>
</template>

<script>
  const app = getApp()
  export default {
    data() {
      return {
        success: false,
        examlist: []
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
      var that = this
      var openid = wx.getStorageSync('openid')
      if (openid == '') {
        wx.showToast({
          title: '微信登陆失败',
          icon: 'none',
          duration: 1500
        })
      } else {
        wx.showLoading({
          title: '拼命加载中...',
        })
        wx.request({
          url: app.globalData.serverUrl + '/wx/exams',
          method: 'GET',
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            openid: openid
          },
          success: function(res) {
            wx.hideLoading()
            if (res.data.code == "201") {
              wx.showToast({
                title: '学号错误,请重新绑定',
                icon: 'none',
                duration: 2000,
                success: function() {
                  setTimeout(function() {
                    wx.navigateTo({
                      url: '../../mine/userform/userform'
                    })
                  }, 1500)
                }
              })
            } else if (res.data.code == "202") {
              wx.showToast({
                title: '教务系统密码错误,请重新绑定',
                icon: 'none',
                duration: 2000,
                success: function() {
                  setTimeout(function() {
                    wx.navigateTo({
                      url: '../../mine/userform/userform'
                    })
                  }, 1500)
                }
              })
            } else if (res.data.code == '200') {
              if (res.data.data != null) {
                that.setData({
                  examlist: res.data.data
                })
                wx.setStorageSync('examlist', res.data.data)
              }
            } else {
              wx.showLoading({
                title: '当前查询人数过多，将尝试读取本地缓存',
              })
              wx.getStorage({
                key: 'examlist',
                success: function(res) {
                  wx.hideLoading()
                  that.setData({
                    examlist: res.data
                  })
                },
                fail: function() {
                  wx.hideLoading()
                  wx.showToast({
                    title: '本地暂无缓存，请稍后再试',
                    icon: 'none',
                    duration: 1500
                  })
                }
              })
            }
          },
          fail: function(err) {
            wx.hideLoading()
            wx.showLoading({
              title: '当前查询人数过多，将尝试读取本地缓存',
            })
            wx.getStorage({
              key: 'examlist',
              success: function(res) {
                wx.hideLoading()
                that.setData({
                  examlist: res.data
                })
              },
              fail: function() {
                wx.hideLoading()
                wx.showToast({
                  title: '本地暂无缓存，请稍后再试',
                  icon: 'none',
                  duration: 1500
                })
              }
            })
          }
        })
      }

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
    padding-bottom: 30rpx;
  }

  .examlist {
    width: 100%;
    display: flex;
    flex-direction: column;
  }

  .examlist .exam {
    width: 96%;
    display: flex;
    flex-direction: column;
    color: #000;
    font-size: 32rpx;
    margin: 10rpx auto;
  }

  .exam .examname {
    height: 65rpx;
    line-height: 65rpx;
    background: #31c27c;
    border: 1px solid #31c27c;
    border-top-left-radius: 12rpx;
    border-top-right-radius: 12rpx;
    color: #fff;
    padding: 10rpx;
  }

  .exam .examinfo {
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    background: #fff;
    border-bottom-left-radius: 12rpx;
    border-bottom-right-radius: 12rpx;
    padding: 10rpx;
  }

  .exam .examinfo p {
    height: 65rpx;
    line-height: 65rpx;
  }
</style>
