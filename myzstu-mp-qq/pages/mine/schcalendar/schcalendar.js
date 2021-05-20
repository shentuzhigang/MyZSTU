const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls: [
      app.globalData.serverUrl + '/images/schcalendar-1.jpg', 
      app.globalData.serverUrl + '/images/schcalendar-2.jpg'
    ],
    src1: app.globalData.serverUrl +'/images/schcalendar-1.jpg',
    src2: app.globalData.serverUrl + '/images/schcalendar-2.jpg',
    mod: [
      'aspectFit',
      'widthFix',
    ]
  }, 
  previewImage: function (e) {
    wx.previewImage({
      urls: this.data.imgUrls
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
    
  }
})