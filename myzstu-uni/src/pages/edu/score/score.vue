<template>
  <view class="container">
    <picker mode="selector" v-model="yearindex" :range="years">
      <view class="picker">
        {{years[yearindex]}}学期<image class="down" src="/static/images/down.svg"></image>
      </view>
    </picker>
    <view class="scoreinfo">
      <view class="title">成绩单</view>
      <view class="header">
        <view class="name">课程名</view>
        <view class="credit">学分</view>
        <view class="grade">成绩</view>
        <view class="gpa">绩点</view>
      </view>
      <view class="noscore" v-if="scorelist.length < 1">暂无成绩信息</view>
      <view class="content" v-for="item in scorelist" :key="item.courseName">
        <view class="name">{{item.courseName}}</view>
        <view class="credit">{{item.credit}}</view>
        <view class="grade">{{item.grade}}</view>
        <view class="gpa">{{item.gpa}}</view>
      </view>
    </view>
    <view class="scoresum">
      <view class="title">成绩统计</view>
      <view class="content">
        <view class="name">学期总学分</view>
        <view>{{credit_semester}}</view>
      </view>
      <view class="content">
        <view class="name">学期平均绩点</view>
        <view>{{avggpa_semester}}</view>
      </view>
      <view class="content">
        <view class="name">学年总学分</view>
        <view>{{credit_year}}</view>
      </view>
      <view class="content">
        <view class="name">学年平均绩点</view>
        <view>{{avggpa_year}}</view>
      </view>
      <view class="content">
        <view class="name">所有课程总学分</view>
        <view>{{credit_all}}</view>
      </view>
      <view class="content">
        <view class="name">所有课程平均绩点</view>
        <view>{{avggpa_all}}</view>
      </view>
    </view>
  </view>

</template>

<script>
  const app = getApp()
  export default {
    data() {
      return {
        years: wx.getStorageSync('years'),
        yearindex: 0,
        scorelist: [],
        credit_semester: 0,
        avggpa_semester: 0.00.toFixed(2),
        credit_year: 0,
        avggpa_year: 0.00.toFixed(2),
        credit_all: 0,
        avggpa_all: 0.00.toFixed(2),
      }
    },
    methods: {

    },
    bindValueChange: function(e) {
      this.setData({
        yearindex: e.detail.value
      })
      var scores = wx.getStorageSync('scores')
      if (scores != null) {
        this.showScores(scores)
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
          mask: true
        })
        wx.request({
          url: app.globalData.serverUrl + '/wx/grades',
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
                duration: 1000,
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
                duration: 1000,
                success: function() {
                  setTimeout(function() {
                    wx.navigateTo({
                      url: '../../mine/userform/userform'
                    })
                  }, 1500)
                }
              })
            } else if (res.data.code == '200') {
              if (res.data.data.length > 0) {
                var years = res.data.years.reverse()
                that.setData({
                  years: years
                })
                wx.setStorageSync('years', years)
                wx.setStorageSync('scores', res.data)
                that.showScores(res.data)
              }
            } else {
              that.getDataFromStorage()
            }
          },
          fail: function(err) {
            wx.hideLoading()
            that.getDataFromStorage()
          }
        })
      }

    },
    getDataFromStorage() {
      wx.showLoading({
        title: '当前查询人数过多，将尝试读取本地缓存',
      })
      var that = this
      wx.getStorage({
        key: 'scores',
        success: function(res) {
          wx.hideLoading()
          that.setData({
            yearindex: 0,
            years: res.data.years
          })
          that.showScores(res.data)
        },
        fail: function(err) {
          wx.hideLoading()
          wx.showToast({
            title: '本地暂无缓存，请稍后再试',
            icon: 'none',
            duration: 1500
          })
        }
      })
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

    },
    showScores: function(res) {
      var picker = res.years[this.data.yearindex]
      var year = picker.split('-')[0] + '-' + picker.split('-')[1]
      var term = picker.split('-')[2]
      var scorelist = []
      var data = res.data
      var credit_semester = 0,
        avggpa_semester = 0,
        credit_year = 0,
        avggpa_year = 0,
        credit_all = 0,
        avggpa_all = 0
      var credit = 0,
        gpa = 0,
        credit_and_pga_semester = 0,
        credit_and_pga_year = 0,
        credit_and_pga_all = 0
      for (var i = 0; i < data.length; i++) {
        if (data[i].credit.trim()) {
          credit = parseFloat(data[i].credit)
        }
        if (data[i].gpa.trim()) {
          gpa = parseFloat(data[i].gpa)
        }
        credit_all += credit
        credit_and_pga_all += gpa * credit
        if (data[i].year == year) {
          credit_year += credit
          credit_and_pga_year += gpa * credit
          if (data[i].term == term) {
            credit_semester += credit
            credit_and_pga_semester += gpa * credit
            scorelist.push(data[i])
          }
        }
      }
      avggpa_semester = credit_and_pga_semester / credit_semester
      avggpa_year = credit_and_pga_year / credit_year
      avggpa_all = credit_and_pga_all / credit_all
      this.setData({
        scorelist: scorelist,
        credit_semester: credit_semester.toFixed(0),
        avggpa_semester: avggpa_semester.toFixed(2),
        credit_year: credit_year.toFixed(0),
        avggpa_year: avggpa_year.toFixed(2),
        credit_all: credit_all.toFixed(0),
        avggpa_all: avggpa_all.toFixed(2),
      })
    }
  }
</script>

<style>
  page {
    background: #FFF;
    padding-bottom: 20rpx;
  }

  .container {
    width: 100%;
    color: #000;
    font-size: 32rpx;
  }

  .picker {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    border: 1px solid #31c27c;
    background: #fff;
    height: 80rpx;
    line-height: 80rpx;
    border-radius: 12rpx;
    text-align: center;
    padding: 0 20rpx;
    letter-spacing: 5rpx;
  }

  .picker .down {
    margin-left: 20rpx;
    width: 40rpx;
    height: 40rpx;
  }

  .scoreinfo {
    width: 95%;
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    border-radius: 12rpx;
    background: #fff;
    margin: 20rpx auto;
    border-bottom: 0;
  }

  .scoreinfo .title {
    text-align: center;
    background: #31c27c;
    height: 70rpx;
    line-height: 70rpx;
    color: #FFF;
    border-bottom: 1px #fff solid;
  }

  .scoreinfo .header {
    display: flex;
    flex-direction: row;
    background: #31c27c;
    color: #FFF;
  }

  .scoreinfo .noscore {
    text-align: center;
    height: 70rpx;
    line-height: 70rpx;
    color: #000;
    border-bottom: 1px #31c27c solid;
  }

  .scoreinfo .content {
    display: flex;
    flex-direction: row;
  }

  .scoreinfo .name {
    width: 400rpx;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-bottom: 1px solid #31c27c;
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    white-space: nowrap;
  }

  .scoreinfo .credit,
  .scoreinfo .grade,
  .scoreinfo .gpa {
    width: 110rpx;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-left: 1px solid #31c27c;
    border-bottom: 1px solid #31c27c;
  }

  .scoreinfo .header .credit,
  .scoreinfo .header .grade,
  .scoreinfo .header .gpa {
    border-left: 1px solid #FFF;
  }

  .scoresum {
    width: 95%;
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    border-radius: 12rpx;
    background: #fff;
    margin: 20rpx auto;
    border-bottom: 0;
  }

  .scoresum .title {
    text-align: center;
    background: #31c27c;
    height: 70rpx;
    line-height: 70rpx;
    color: #FFF;
    border-bottom: 1px #fff solid;
  }

  .scoresum .content {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    height: 70rpx;
    line-height: 70rpx;
    text-align: center;
    border-bottom: 1px solid #31c27c;
  }

  .scoresum .content .name {
    border-right: 1px solid #31c27c;
  }

  .scoresum .content view {
    width: 50%;
  }
</style>
