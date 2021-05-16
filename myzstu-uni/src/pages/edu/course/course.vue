<template>
  <view>
    <scroll-view scroll-y="true" class="scroll">
      <view class="top">
        <view v-for="item in ['一','二','三','四','五','六','日']" class="top-text">周{{item}}</view>
      </view>
      <view class="main">
        <view class="period">
          <view v-for="item in [1,2,3,4,5,6,7,8,9,10,11,12]" class="left">{{item}}</view>
        </view>
        <!--课表-->
        <view v-for="item in wlist" :key="item.name">
          <view class="flex-item kcb-item" 
            bindtap="showCardView" 
            data-statu="open" 
            data-index="index"
            :style="{marginLeft:(item.day-1)*100+'rpx',marginTop:(item.period-1)*100+5+'rpx',height:item.length*100-5 + 'rpx',backgroundColor:colorArrays[index%9]}">
            <view class="smalltext">{{item.name+'@'+item.room}}</view>
          </view>
        </view>
      </view>
    </scroll-view>

    <movable-area>
      <movable-view :x="x" :y="y" direction="all" @tap="showsjCardView">实践课</movable-view>
    </movable-area>
    <modal title="我的实践课" 
      :hidden="hidesjCardView" 
      :no-cancel="noCancel" 
      @confirm="confirmsjCard">
      <scroll-view scroll-y="true" style="max-height: 500rpx;">
        <view class="sjmodalcontent" v-for="item in plist" :key="item.name">
          <view>课程名称： {{item.name}}</view>
          <view>上课教师： {{item.teacher}}</view>
        </view>
        <view class="tip" v-if="plist.length < 1">本学期没有实践课哦</view>
      </scroll-view>
    </modal>

    <modal title="课程信息" 
      :hidden="hideCardView"  
      :no-cancel="noCancel" 
      @confirm="confirmCard">
      <view class="modalcontent">
        <view>课程名称： {{courseName}}</view>
        <view>上课时间： {{courseTime}}</view>
        <view>上课教师： {{courseTeacher}}</view>
        <view>上课地点： {{coursePlace}}</view>
      </view>
    </modal>
  </view>
</template>

<script>
  const app = getApp()
  var util = require('../../../utils/util.js');
  export default {
    data() {
      return {
        x: 650,
        y: 450,
        colorArrays: ['#ef5b9c', '#f15b6c', '#f26522', '#ffd400', '#8552a1', '#7fb80e', '#65c294', '#78cdd1',
          '#33a3dc'
        ],
        wlist: [],
        plist: [],
        noCancel: true,
        hidesjCardView: true,
        hideCardView: true,
        courseName: '',
        courseTime: '',
        courseTeacher: '',
        coursePlace: '',
      }
    },
    computed: {
      
    },
    methods: {

    },
    showsjCardView: function(event) {
      this.setData({
        hidesjCardView: false
      })
    },
    showCardView: function(event) {
      var index = event.currentTarget.dataset.index
      var course = this.data.wlist[index]
      var times = []
      for (var i = course.period; i < course.period + course.length; i++) {
        times.push(i)
      }
      this.setData({
        hideCardView: false,
        courseName: course.name,
        courseTime: course.week + '周 ' + '第' + times.join(',') + '节',
        courseTeacher: course.teacher,
        coursePlace: course.room
      })
    },
    confirmsjCard: function() {
      this.setData({
        hidesjCardView: true
      })
    },
    confirmCard: function() {
      this.setData({
        hideCardView: true
      })
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
      this.main(this.getCourses)
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
    onPullDownRefresh: function() {},
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

    main: function(callback) {
      //先得出今天是第几教学周
      var that = this
      console.log("trying...")
      wx.request({
        url: app.globalData.serverUrl + '/wx/firstday',
        method: 'GET',
        success: function(res) {
          wx.hideLoading()
          console.log(res)
          if (res.data.code == "200") {
            var week = util.calcuateWeek(res.data.data)
            console.log("本周是第" + week + "周")
            wx.setNavigationBarTitle({
              title: '第' + week + '周课表'
            })
            callback(week, that.showCourses)
          } else {
            if (that.data.inputWeek == '') {
              that.modalinput()
            }
          }
        },
        fail: function(err) {
          wx.hideLoading()
        }
      })
    },
    getCourses: function(week) {
      var courses = null
      var that = this
      var openid = wx.getStorageSync('openid')
      if (openid == '') {
        wx.showToast({
          title: '微信登陆失败',
          icon: 'none'
        })
      } else {
        wx.showLoading({
          title: '拼命加载中...',
        })
        wx.request({
          url: app.globalData.serverUrl + '/wx/courses',
          method: 'GET',
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            openid: openid
          },
          success: function(res) {
            wx.hideLoading()
            console.log(res.data)
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
                  }, 1000)
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
                  }, 1000)
                }
              })
            } else if (res.data.code == "200") {

              if (res.data.data != null && res.data.data.length > 0) {
                var courses = res.data.data
                that.showCourses(courses, week)
                wx.setStorageSync('courses', courses)
              } else {
                wx.showLoading({
                  title: '当前查询人数过多，将尝试读取本地缓存',
                })
                wx.getStorage({
                  key: 'courses',
                  success: function(res) {
                    wx.hideLoading()
                    var courses = res.data
                    that.showCourses(courses, week)
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
              }
            } else {
              wx.showLoading({
                title: '当前查询人数过多，将尝试读取本地缓存',
              })
              wx.getStorage({
                key: 'courses',
                success: function(res) {
                  wx.hideLoading()
                  var courses = res.data
                  that.showCourses(courses, week)
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
            }
          },
          fail: function(err) {
            wx.hideLoading()
            wx.showLoading({
              title: '当前查询人数过多，将尝试读取本地缓存',
            })
            wx.getStorage({
              key: 'courses',
              success: function(res) {
                wx.hideLoading()
                var courses = res.data
                that.showCourses(courses, week)
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
          }
        })
      }
    },
    /**
     * 显示课表
     * courses 
     * week 指当前是第几教学周
     */
    showCourses: function(courses, week) {
      var usual = [] //一般课
      var practices = [] //实践课
      for (var i = 0; i < courses.length; i++) {
        if (courses[i].type == "一般课") {
          courses[i].day = parseInt(courses[i].day)
          courses[i].period = parseInt(courses[i].period)
          courses[i].length = parseInt(courses[i].length)
          if (util.isCourseValid(courses[i], week)) {
            usual.push(courses[i])
          }
        } else {
          practices.push(courses[i])
        }
      }
      console.log("wwwww");
      console.log(practices);
      this.setData({
        wlist: usual,
        plist: practices
      })
    }
  }
</script>

<style>
  .top {
    display: flex;
    flex-direction: row;
    padding-left: 35rpx;
    background-color: #31c27c;
    color: #fff;
  }

  .top-text {
    width: 100rpx;
    height: 35rpx;
    font-size: 26rpx;
    justify-content: center;
    display: flex;
    align-items: center;
  }

  .main {
    height: 1200rpx;
    width: 730rpx;
    display: flex;
  }

  .flex-item {
    width: 95rpx;
    height: 200rpx;
  }

  .kcb-item {
    position: absolute;
    justify-content: center;
    display: flex;
    align-items: center;
    border-radius: 12rpx;
    overflow: hidden;
  }

  .period {
    background-color: #31c27c;
    color: #fff;
  }

  .smalltext {
    font-size: 22rpx;
    color: #fff;
    text-align: center;
  }

  .scroll {
    height: 1205rpx;
    z-index: 100;
    position: fixed;
  }

  .left {
    width: 35rpx;
    height: 100rpx;
    font-size: 26rpx;
    justify-content: center;
    display: flex;
    align-items: center;
  }

  movable-area {
    position: absolute;
    z-index: 110;
    top: 35rpx;
    left: 35rpx;
    height: 1170rpx;
    width: 715rpx;
    pointer-events: none;
  }

  movable-view {
    width: 120rpx;
    height: 120rpx;
    line-height: 120rpx;
    background: #31c27c;
    border-radius: 60rpx;
    font-size: 28rpx;
    text-align: center;
    color: #fff;
    pointer-events: auto;
    opacity: 0.8;
  }

  modal .sjmodalcontent {
    color: #000;
    line-height: 60rpx;
    margin-top: 15rpx;
    border-bottom: 1px solid rgb(221, 217, 217);
  }

  modal .tip {
    color: #000;
    line-height: 60rpx;
    text-align: center;
  }

  modal .modalcontent {
    color: #000;
    line-height: 60rpx;

  }
</style>
