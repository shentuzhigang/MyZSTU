Page({
  data:{
    name:"",
    telephone:"",
    qq:"",
    address:"",
    description:""
  },
  submitInfo:function(){

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
        libpw: wx.getStorageSync('libpw'),
        ssopw: wx.getStorageSync('ssopw')
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