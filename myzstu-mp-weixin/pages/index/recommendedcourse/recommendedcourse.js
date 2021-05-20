const app = getApp()
var util = require('../../../utils/util.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    x: 650,
    y: 450,
    colorArrays: ['#ef5b9c', '#f15b6c', '#f26522', '#ffd400', '#8552a1', '#7fb80e', '#65c294', '#78cdd1', '#33a3dc'],
    wlist: [],
    plist: [],
    noCancel: true,
    hidesjCardView: true,
    hideCardView: true,
    courseName: '',
    courseTime: '',
    courseTeacher: '',
    coursePlace: '',
  },
  showsjCardView: function (event) {
    this.setData({
      hidesjCardView: false
    })
  },
  showCardView: function (event) {
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
  confirmsjCard: function () {
    this.setData({
      hidesjCardView: true
    })
  },
  confirmCard: function () {
    this.setData({
      hideCardView: true
    })
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
    this.main(this.getCourses)
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

  },

  main: function (callback) {
    //先得出今天是第几教学周
    var that = this
    console.log("trying...")
    wx.request({
      url: app.globalData.serverUrl + '/wx/firstday',
      method: 'GET',
      success: function (res) {
        wx.hideLoading()
        console.log(res)
        if (res.data.code == "200") {
          var week = util.calcuateWeek(res.data.data)
          console.log("本周是第" + week + "周")
          wx.setNavigationBarTitle({
            title: '推荐课表'
          })
          callback(week, that.showCourses)
        }
        else {
          if (that.data.inputWeek == '') {
            that.modalinput()
          }
        }
      },
      fail: function (err) {
        wx.hideLoading()
      }
    })
  },
  getCourses: function (week) {
    var courses = null
    var that = this
    var openid = wx.getStorageSync('openid')
    if (openid == '') {
      wx.showToast({
        title: '微信登陆失败',
        icon: 'none'
      })
    }
    else {
      wx.showLoading({
        title: '拼命加载中...',
      })
      wx.request({
        url: app.globalData.serverUrl + '/wx/recommendedcourses',
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
                    url: '../../mine/userform/userform'
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
                    url: '../../mine/userform/userform'
                  })
                }, 1000)
              }
            })
          }
          else if (res.data.code == "200") {

            if (res.data.data != null && res.data.data.length > 0) {
              var courses = res.data.data
              that.showCourses(courses, week)
              wx.setStorageSync('courses', courses)
            }
            else{
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
  },
  /**
   * 显示课表
   * courses 
   * week 指当前是第几教学周
   */
  showCourses: function (courses, week) {
    var usual = []      //一般课
    var practices = []  //实践课
    for (var i = 0; i < courses.length; i++) {
      if (courses[i].type == "一般课") {
        courses[i].day = parseInt(courses[i].day)
        courses[i].period = parseInt(courses[i].period)
        courses[i].length = parseInt(courses[i].length)
        usual.push(courses[i])
      }
      else {
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
})