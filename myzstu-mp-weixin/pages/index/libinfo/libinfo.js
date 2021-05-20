// pages/index/libinfo/libinfo.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    search: true,
    focus: true,
    key: '',
    booklist: [],
    tip: '最多可显示20本相关书籍哦',
    noborrow: false,
    nohistory: false,
    borrowlist: [],
    historylist: []
  },
  setKey: function (e) {
    this.setData({
      key: e.detail.value
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
  searchBind: function(){
    if (!this.data.search){
      this.setData({
        search: true
      })
    }
  },
  mineBind: function (e) {
    if(wx.getStorageSync("sid") == ""){
      wx.showToast({
        title: '请绑定你的学号信息',
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
      return
    }
    if (this.data.search) {
      this.setData({
        search: false
      })
      this.getBorrowList()
    }
  },
  searchBook: function () {
    var that = this
    var key = this.data.key
    if (key.trim() == '') {
      wx.showToast({
        title: '请输入关键词',
        icon: 'none',
        duration: 1500
      })
      return
    }
    wx.showLoading({
      title: '拼命检索中...',
    })
    wx.request({
      url: app.globalData.serverUrl+'/wx/book',
      method: 'GET',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
      },
      data: {
        key: key
      },
      success: function (res) {
        wx.hideLoading()
        if (res.data.code == '200') {
          if (res.data.data.length == 0) {
            wx.showToast({
              title: '没有检索到相关书籍!',
              icon: 'none',
              duration: 2000
            })
            that.setData({
              booklist: res.data.data,
              tip: '没有检索到相关书籍'
            })
          }
          else{
            var resnum = res.data.data.length
            var num = resnum > 20 ? 20 : resnum 
            that.setData({
              booklist: res.data.data,
              tip: '共检索到' + num + '本相关书籍'
            })
          }
        }
      },
      fail:function(err){
        wx.hideLoading()
        wx.showToast({
          title: '检索失败',
          icon:'none',
          duration:1500
        })
      }
    })
  },
  getBorrowList: function () {
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
        url: app.globalData.serverUrl + '/wx/borrows',
        method: 'GET',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          openid: openid
        },
        success: function (res) {
          wx.hideLoading()
          console.log(res);
          if(res.data.code == '201'){
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
          else if (res.data.code == '200') {
            wx.setStorageSync('borrowlist', res.data.data)
            if (res.data.data.borrowlist.length == 0) {
              that.setData({
                noborrow: true
              })
            }
            if (res.data.data.historylist.length == 0) {
              that.setData({
                nohistory: true
              })
            } 
            that.setData({
              borrowlist: res.data.data.borrowlist,
              historylist: res.data.data.historylist
            })
          }
          else {
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
  getDataFromStorage() {
    wx.showLoading({
      title: '当前查询人数过多，将尝试读取本地缓存',
    })
    var that = this
    wx.getStorage({
      key: 'borrowlist',
      success: function (res) {
        wx.hideLoading()
        if (res.data.borrowlist.length == 0) {
          that.setData({
            noborrow: true
          })
        }
        if (res.data.historylist.length == 0) {
          that.setData({
            nohistory: true
          })
        } 
        that.setData({
          borrowlist: res.data.borrowlist,
          historylist: res.data.historylist
        })
      },
      fail: function () {
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