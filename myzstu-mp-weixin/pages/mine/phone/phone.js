const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    phone: false,
    life: [],
    work: [],
    other: []
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
    try {
      var phone = wx.getStorageSync('phone')
      if (phone == true) {
        this.setData({
          phone: phone,
          life: wx.getStorageSync('life'),
          work: wx.getStorageSync('work'),
          //other: wx.getStorageSync('other')
        })
      }else{
        wx.request({
          url: app.globalData.serverUrl + '/wx/phones',
          method: 'GET',
          success: function (res) {
            if(res.statusCode == 200 && res.data.data != null){
              console.log(app.globalData.serverUrl + '/phones'),
              console.log(res);
              wx.setStorageSync('phone', true)
              that.showNumber(res.data.data)
            }else{
              wx.setStorageSync('phone', false)
              wx.showToast({
                title: '加载失败',
                icon: 'none',
                duration: 1500
              })
            }
          },
          fail: function (err) {
            wx.setStorageSync('phone', false)
            wx.showToast({
              title: '加载失败',
              icon: 'none',
              duration: 1500
            })

          }
        })
      }
    } catch (e) {
      console.log(e);
    }
  },
  /**
   * 自定义函数
   */
  showNumber: function (data) {
    var life = []
    var work = []
    //var other = []
    for (var i = 0; i < data.length; i++) {
      if (data[i].type == 'life') {
        life.push(data[i])
      }
      else if (data[i].type == 'work') {
        work.push(data[i])
      }
      /*else {
        other.push(data[i])
      }*/
    }
    wx.setStorageSync('life', life)
    wx.setStorageSync('work', work)
    //wx.setStorageSync('other', other)
    this.setData({
      phone: wx.getStorageSync('phone'),
      life: life,
      work: work,
      //other: other
    })
  },
  call: function (e) {
    var number = e.currentTarget.dataset.item.number
    wx.makePhoneCall({
      phoneNumber: number,
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
    
  }
})