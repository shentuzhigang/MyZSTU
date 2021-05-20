const app = getApp()
var util = require('../../utils/util.js');
Page({
  data: {
    tip: '',
    courseurl: 'courseinfo/courseinfo',
    recommendurl:'recommendedcourse/recommendedcourse',
    examurl: 'examinfo/examinfo',
    scoreurl: 'scoreinfo/scoreinfo',
    ecardurl: 'ecardinfo/ecardinfo',
    courseName: '',
    courseTime: '',
    courseTeacher: '',
    coursePlace: ''
  },
  onLoad: function (options) {
    this.checkInfo()
  },
  checkInfo:function(){
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
    }else{
      wx.setStorageSync('binding', true);
    }
    if (sid == null || sid == '' || ecardpw == null || ecardpw == '') {
      this.setData({
        ecardurl: '../mine/userform/userform'
      })
    }
  },
  onShow: function () {
    //this.checkInfo()
    var binding = wx.getStorageSync('binding')
    if(binding){
      this.main(this.getCourses)
    }
    else{
      this.setData({
        tip:'点击绑定个人信息'
      })
    }
  },
  onPullDownRefresh: function () {

  },
  showFailedTip:function(){
    this.setData({
      hasCourse: false,
      tip: '数据获取失败,请稍后再试',
    })
  },
  main: function (callback) {
    //先得出今天是第几教学周
    wx.showLoading({
      title: '拼命加载中...',
    })
    var that = this
    wx.request({
      url: app.globalData.serverUrl+'/wx/firstday',
      method: 'GET',
      success: function (res) {
        wx.hideLoading()
        if (res.data.code == "200") {
          var week = util.calcuateWeek(res.data.data)
          callback(week)
        }
        else {
          that.showFailedTip()
        }
      },
      fail: function (err) {
        console.log(err)
        wx.hideLoading()
        that.showFailedTip()
      }
    })
  },
  getCourses: function (week) {
    var courses = null
    var that = this
    wx.getStorage({
      key: 'courses',
      success: function (res) {
        console.log("从缓存中成功读取到了courses")
        courses = res.data
        that.showCourses(courses, week)
      },
      fail: function (err) {
        console.log("尝试从服务器获取courses")
        var openid = wx.getStorageSync('openid')
        if (openid == '') {
          wx.showToast({
            title: '微信登陆失败',
            icon: 'none',
            duration:2000
          })
        }
        else {
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
            success: function (res) {
              wx.hideLoading()
              console.log(res.data)
              wx.hideLoading()
              if (res.data.code == "201") {
                wx.showToast({
                  title: '学号错误,请重新绑定',
                  icon: 'none',
                  duration: 2000,
                  success: function () {
                    setTimeout(function () {
                      wx.navigateTo({
                        url: '../mine/userform/userform'
                      })
                    }, 1000)
                  }
                })
              }
              else if (res.data.code == "202") {
                wx.showToast({
                  title: '教务系统密码错误,请重新绑定',
                  icon: 'none',
                  duration: 2000,
                  success: function () {
                    setTimeout(function () {
                      wx.navigateTo({
                        url: '../mine/userform/userform'
                      })
                    }, 1000)
                  }
                })
              }
              else if (res.data.code == "200") {
                if (res.data.data != null) {
                  var courses = res.data.data
                  console.log("ccccccc");
                  console.log(res);
                  that.showCourses(courses, week)
                  wx.setStorageSync('courses', courses)
                }
              }
              else {
                wx.showLoading({
                  title: '当前查询人数过多，将尝试读取本地缓存',
                })
                wx.getStorage({
                  key: 'courses',
                  success: function (res) {
                    wx.hideLoading()
                    var courses = res.data
                    that.showCourses(courses, week)
                  },
                  fail: function (err) {
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
            fail: function (err) {
              wx.hideLoading()
              wx.showLoading({
                title: '当前查询人数过多，将尝试读取本地缓存',
              })
              wx.getStorage({
                key: 'courses',
                success: function (res) {
                  wx.hideLoading()
                  var courses = res.data
                  that.showCourses(courses, week)
                },
                fail: function (err) {
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
  showCourses: function (courses, week) {
    var that = this
    var timeTable = app.globalData.timeTable
    var date = new Date()
    var today = date.getDay()  //今天星期几
    var data = []  //存放今天该上的课
    if(today==0){
      today=7;
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
    data.sort(function (a, b) {
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
    if(data==''){
      var info = '今日没课，好好休息哦!'
      this.setData({
        hasCourse: false,
        tip: info
      })
    }
    else{
      if (show == null) {
        var info = '今天的课上完喽!'
        this.setData({
          hasCourse: false,
          tip: info
        })
      }
      else {
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
})
