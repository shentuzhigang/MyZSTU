const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sid:'',
    edupw:'',
    ecardpw:'',
    //lib: '',
    //ssopw:'',
  },
  sidInput:function(e){
    this.setData({     
      sid:e.detail.value
    })
  },
  edupwInput: function (e) {
    this.setData({
      edupw: e.detail.value
    })
  },
  ecardpwInput: function (e) {
    this.setData({
      ecardpw: e.detail.value
    })
  },
  /*libpwInput: function (e) {
    this.setData({
      libpw: e.detail.value
    })
  },
  ssopwInput: function (e) {
    this.setData({
      ssopw: e.detail.value
    })
  },*/
  saveInfo: function(){
    var that=this
    var openid=wx.getStorageSync('openid')
    if(openid == ""){
      wx.showToast({
        title: '微信登录失败',
        icon: "none",
        duration: 1500
      })
    } else if (that.data.sid.length != 13) {
      wx.showToast({
        title: '请填写正确的学号',
        icon: "none",
        mask: true,
        duration: 1500
      })
    } else{
      wx.request({
        url: app.globalData.serverUrl + '/wx/student',
        method: 'POST',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          openid: openid,
          sid: that.data.sid,
          edupw: that.data.edupw,
          ecardpw: that.data.ecardpw,
          //libppw: that.data.libpw,
          //ssopw: that.data.ssopw
        },
        success: function (res) {
          wx.showToast({
            title: '绑定成功',
            icon: "success",
            duration: 1000,
            success: function(){
              setTimeout(function(){
                wx.reLaunch({
                  url: '../../index/index',
                })
              }, 1000)
            }
          })
          try {
            wx.setStorageSync('sid', that.data.sid);
            wx.setStorageSync('edupw', that.data.edupw);
            wx.setStorageSync('ecardpw', that.data.ecardpw);
            //wx.setStorageSync('libpw', that.data.libpw);
            //wx.setStorageSync('ssopw', that.data.ssopw);
          }
          catch (e) {
            console.log(e);
          }
          
        }
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
    try{
      this.setData({
        sid: wx.getStorageSync('sid'),
        edupw: wx.getStorageSync('edupw'),
        ecardpw: wx.getStorageSync('ecardpw'),
        //libpw: wx.getStorageSync('libpw'),
        //ssopw: wx.getStorageSync('ssopw')
      })
    }catch(e){
      console.log(e)
      wx.showToast({
        title: '信息读取失败',
        icon: 'none',
        duration: 1500
      })
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
})