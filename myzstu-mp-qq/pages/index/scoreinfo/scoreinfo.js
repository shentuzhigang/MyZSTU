const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    years: wx.getStorageSync('years'),
    yearindex: 0,
    scorelist:[],
    credit_semester: 0,
    avggpa_semester: 0.00.toFixed(2),
    credit_year: 0,
    avggpa_year: 0.00.toFixed(2),
    credit_all: 0,
    avggpa_all: 0.00.toFixed(2),
  },
  bindValueChange: function (e) {
    this.setData({
      yearindex: e.detail.value
    })
    var scores=wx.getStorageSync('scores')
    if(scores != null){
      this.showScores(scores)
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
    var openid=wx.getStorageSync('openid')
    if (openid=='') {
      wx.showToast({
        title: '微信登陆失败',
        icon: 'none',
        duration: 1500
      })
    } else{
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
          openid:openid
        },
        success: function (res) {
          wx.hideLoading()
          if (res.data.code == "201") {
            wx.showToast({
              title: '学号错误,请重新绑定',
              icon: 'none',
              duration: 1000,
              success: function () {
                setTimeout(function () {
                  wx.navigateTo({
                    url: '../../mine/userform/userform'
                  })
                }, 1500)
              }
            })
          }
          else if (res.data.code == "202") {
            wx.showToast({
              title: '教务系统密码错误,请重新绑定',
              icon: 'none',
              duration: 1000,
              success: function () {
                setTimeout(function () {
                  wx.navigateTo({
                    url: '../../mine/userform/userform'
                  })
                }, 1500)
              }
            })
          }
          else if (res.data.code == '200') {
            if(res.data.data.length > 0){
              var years = res.data.years.reverse()
              that.setData({
                years: years
              })
              wx.setStorageSync('years', years)
              wx.setStorageSync('scores', res.data)
              that.showScores(res.data)
            }
          }
          else{
            that.getDataFromStorage()
          }
        },
        fail: function (err) {
          wx.hideLoading()
          that.getDataFromStorage()
        }
      })
    }

  },
  getDataFromStorage(){
    wx.showLoading({
      title: '当前查询人数过多，将尝试读取本地缓存',
    })
    var that=this
    wx.getStorage({
      key: 'scores',
      success: function (res) {
        wx.hideLoading()
        that.setData({
          yearindex: 0,
          years: res.data.years
        })
        that.showScores(res.data)
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
  showScores:function(res){
    var picker = res.years[this.data.yearindex]
    var year = picker.split('-')[0] + '-' + picker.split('-')[1]
    var term = picker.split('-')[2]
    var scorelist = []
    var data = res.data
    var credit_semester = 0, avggpa_semester = 0, credit_year = 0, avggpa_year = 0, credit_all = 0, avggpa_all = 0
    var credit = 0, gpa = 0, credit_and_pga_semester = 0, credit_and_pga_year = 0, credit_and_pga_all = 0
    for (var i = 0; i < data.length; i++) {
      if (data[i].credit.trim()) {
        credit = parseFloat(data[i].credit)
      }
      if (data[i].gpa.trim()) {
        gpa = parseFloat(data[i].gpa)
      }
      credit_all +=  credit
      credit_and_pga_all += gpa * credit
      if (data[i].year == year){
        credit_year += credit
        credit_and_pga_year += gpa * credit
        if (data[i].term == term){
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
})