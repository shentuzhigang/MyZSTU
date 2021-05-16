<template>
  <view class="container">
    <view class="title">即时课表</view>
    <navigator class="coursecard" :url="courseurl">
      <view v-if="hasCourse">
        <view class="top">
          <view class="content" >
            <view class="course-item">课程名称: {{courseName}}</view>
            <view class="course-item">上课时间: {{courseTime}}</view>
            <view class="course-item">上课教师: {{courseTeacher}}</view>
            <view class="course-item">上课地点: {{coursePlace}}</view>
          </view>
        </view>
        <view class="bottom">点击查看全部课表</view>
      </view>
      <view class="tip" v-else>{{tip}}</view>
    </navigator>
    <view class="title">教务查询</view>
    <view class="btn_group">
      <navigator class="btn exam" :url="examurl">
        <image class="btn_icon" src="/static/images/exam.svg"></image>
        <view class="btn_info">考试查询</view>
      </navigator>
      <navigator class="btn score" :url="scoreurl">
        <image class="btn_icon" src="/static/images/score.svg"></image>
        <view class="btn_info">成绩查询</view>
      </navigator>
    </view>
    <view class="title">更多功能</view>
    <view class="btn_group">
      <navigator class="btn ecard" :url="ecardurl">
        <image class="btn_icon" src="/static/images/ecard.svg"></image>
        <view class="btn_info">校园一卡通</view>
      </navigator>
      <navigator class="btn library" :url="liburl">
        <image class="btn_icon" src="/static/images/library.svg"></image>
        <view class="btn_info">掌上图书馆</view>
      </navigator>
    </view>
  </view>
</template>

<script>
  const app = getApp()
  import util from '@/utils/util.js'
  export default {
    data() {
      return {
        tip: '',
        courseurl: '/pages/edu/course/course',
        examurl: '/pages/edu/exam/exam',
        scoreurl: '/pages/edu/score/score',
        ecardurl: '/pages/ecard/ecard',
        liburl: '/pages/lib/lib',
        courseName: '',
        courseTime: '',
        courseTeacher: '',
        coursePlace: ''
      }
    },
    methods: {
      checkInfo() {
        var sid = wx.getStorageSync('sid')
        var edupw = wx.getStorageSync('edupw')
        var ecardpw = wx.getStorageSync('ecardpw')
        var ssopw = wx.getStorageSync('ssopw')
        if (sid == null || sid == '' || edupw == null || edupw == '') {
          this.setData({
            courseurl: '../mine/userform/userform',
            examurl: '../mine/userform/userform',
            scoreurl: '../mine/userform/userform'
          })
          wx.setStorageSync('binding', false);
        } else {
          wx.setStorageSync('binding', true);
        }
        if (sid == null || sid == '' || ecardpw == null || ecardpw == '') {
          this.setData({
            ecardurl: '../mine/userform/userform'
          })
        }
      },
      showFailedTip: function() {
        this.setData({
          hasCourse: false,
          tip: '数据获取失败,请稍后再试',
        })
      },
      main: function(callback) {
        //先得出今天是第几教学周
        wx.showLoading({
          title: '拼命加载中...',
        })
        var that = this
        wx.request({
          url: app.globalData.serverUrl + '/wx/firstday',
          method: 'GET',
          success: function(res) {
            wx.hideLoading()
            if (res.data.code == "200") {
              var week = util.calcuateWeek(res.data.data)
              callback(week)
            } else {
              that.showFailedTip()
            }
          },
          fail: function(err) {
            console.log(err)
            wx.hideLoading()
            that.showFailedTip()
          }
        })
      },
      getCourses: function(week) {
        var courses = null
        var that = this
        wx.getStorage({
          key: 'courses',
          success: function(res) {
            console.log("从缓存中成功读取到了courses")
            courses = res.data
            that.showCourses(courses, week)
          },
          fail: function(err) {
            console.log("尝试从服务器获取courses")
            var openid = wx.getStorageSync('openid')
            if (openid == '') {
              wx.showToast({
                title: '微信登陆失败',
                icon: 'none',
                duration: 2000
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
                            url: '../mine/userform/userform'
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
                            url: '../mine/userform/userform'
                          })
                        }, 1000)
                      }
                    })
                  } else if (res.data.code == "200") {
                    if (res.data.data != null) {
                      var courses = res.data.data
                      console.log("ccccccc");
                      console.log(res);
                      that.showCourses(courses, week)
                      wx.setStorageSync('courses', courses)
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

          }
        })
      },
      /**
       * 显示课表
       * courses 
       * week 指当前是第几教学周
       */
      showCourses: function(courses, week) {
        var that = this
        var timeTable = app.globalData.timeTable
        var date = new Date()
        var today = date.getDay() //今天星期几
        var data = [] //存放今天该上的课
        if (today == 0) {
          today = 7;
        }
        for (var i = 0; i < courses.length; i++) {
          if (courses[i].day == today && util.isCourseValid(courses[i], week)) {
            data.push(courses[i])
          }
          if (courses[i].day == today && util.isCourseValid(courses[i], week)) {
            data.push(courses[i])
          }
        }
        //对今天要上的课按时间排序
        data.sort(function(a, b) {
          return a.period - b.period;
        })
        var show = null
        //找到下一节要上或者正在上的课
        for (var i = 0; i < data.length; i++) {
          var now = new Date()
          var year = now.getFullYear()
          var month = now.getMonth()
          var day = now.getDate()
          var period = timeTable[parseInt(data[i].period) + parseInt(data[i].length) - 1]
          var hour = period.split('.')[0]
          var min = period.split('.')[1]
          var time = new Date(year, month, day, hour, min, 0)
          if (now - time < 0) {
            show = data[i]
            break;
          }
        }
        if (data == '') {
          var info = '今日没课，好好休息哦!'
          this.setData({
            hasCourse: false,
            tip: info
          })
        } else {
          if (show == null) {
            var info = '今天的课上完喽!'
            this.setData({
              hasCourse: false,
              tip: info
            })
          } else {
            var times = []
            for (var i = parseInt(show.period); i < parseInt(show.period) + parseInt(show.length); i++) {
              times.push(i)
            }
            this.setData({
              hasCourse: true,
              courseName: show.name,
              courseTime: show.week + '周 ' + '第' + times.join(',') + '节',
              courseTeacher: show.teacher,
              coursePlace: show.room
            })
          }
        }
      }
    },
    onLoad: function(options) {
      this.checkInfo()
    },
    onShow: function() {
      //this.checkInfo()
      var binding = wx.getStorageSync('binding')
      if (binding) {
        this.main(this.getCourses)
      } else {
        this.tip = '点击绑定个人信息'
      }
    },
    onPullDownRefresh: function() {

    }
  }  
</script>

<style>
  page {
    background: #FFF;
    padding-bottom: 15rpx;
  }

  .container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 34rpx;
  }

  .title {
    width: 700rpx;
    color: #31c27c;
    padding-left: 10rpx;
    border-left: 8rpx solid #31c27c;
    margin: 10rpx 0 15rpx 40rpx;
    font-weight: bold;
  }

  

  .btn_group {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    width: 730rpx;
    margin-bottom: 40rpx;
  }

  .btn {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 15rpx;
    margin-bottom: 10rpx;
    border-radius: 12rpx;
    height: 100rpx;
    width: 300rpx;
    background: #fff;
    box-shadow: 5px 5px 5px #E8E8E8;
    border: 1px solid #F5F5F5;
  }

  .btn_icon {
    width: 60rpx;
    height: 60rpx;
    margin-left: 20rpx;
  }

  .btn_info {
    margin-left: 20rpx;
    color: #000;
  }
</style>

<style lang="scss" scoped>
  .coursecard {
    width: 90%;
    background: #31c27c;
    color: #FFF;
    margin-bottom: 40rpx;
    border-radius: 12rpx;
    box-shadow: 8px 8px 5px rgb(223, 223, 223);
    text-align: center;
    .top {
      height: 300rpx;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
      .content {
        display: flex;
        flex-direction: column;
        padding: 20rpx;
        view {
          font-size: 32rpx;
          height: 60rpx;
          line-height: 60rpx;
        }
      }
    }
    .bottom {
      height: 80rpx;
      line-height: 80rpx;
      border-top: 1px dashed #fff;
    }
    .tip {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-self: center;
      font-size: 32rpx;
      height: 380rpx;
      line-height: 60rpx;
    }
  }
</style>