// pages/index/ecardinfo/ecardinfo.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sid: wx.getStorageSync('sid'),
    sname: wx.getStorageSync('sname'),
    years:[],
    yearindex: 0,
    balance: 0.00.toFixed(2),
    food: 0.00.toFixed(2),
    electric: 0.00.toFixed(2),
    network: 0.00.toFixed(2),
    shower: 0.00.toFixed(2),
    other: 0.00.toFixed(2),
    total: 0.00.toFixed(2)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var today = new Date()
    var years= []
    console.log(today);
    var month = today.getMonth() + 1
    var flag = true
    for(var i = 0; i < 6; i ++){
      if(flag && month > 0){
        years.push(today.getFullYear() + '年' + month + '月')
      }else{
        if(flag) month = 12
        flag = false;
        years.push(today.getFullYear()-1 + '年' + month + '月')
      }
      month--
    }
    this.setData({
      years: years
    })
    wx.setStorageSync('years', years)
  },
  bindValueChange: function (e) {
    var valueindex = e.detail.value
    this.setData({
      yearindex: valueindex
    })
    var years = wx.getStorageSync('years')
    var value = years[valueindex]
    console.log(value);
    var valueyear = value.substring(0, 4)
    var valuemonth = (Array(2).join(0) + value.substring(5, 6)).slice(-2);
    var start = valueyear + '-' + valuemonth + '-' + '01'
    var end = value.substring(0, 4) + '-' + valuemonth + '-'
    if (valuemonth == 1 || valuemonth == 3 || valuemonth == 5 || valuemonth == 7 || valuemonth == 8 || valuemonth == 10 || valuemonth == 12) {
      end += '31'
    } else if (valuemonth == 4 || valuemonth == 6 || valuemonth == 9 || valuemonth == 11) {
      end += '30'
    } else if (valuemonth == 2) {
      if (valueyear % 4 == 0 && valueyear % 100 != 0 || valueyear % 400 == 0) {
        end += '29'
      } else {
        end += '28'
      }
    }
    console.log(start +'---------' + end);
    this.getDataFromRequest(start, end)
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
    var that = this
    var openid=wx.getStorageSync('openid')
    if (openid == '') {
      wx.showToast({
        title: '微信登陆失败',
        icon: 'none',
        duration: 1500
      })
    }
    else{
      var today = new Date()
      var year = today.getFullYear()
      var month = today.getMonth() + 1
      var day = today.getDate()
      var end = year + '-' + month + '-' + day
      var start = year + '-' + month + '-01'
      this.setData({
        start: start,
        end: end
      })
      this.getDataFromRequest(start, end)
    }
  },
  getDataFromRequest: function(start, end){
    var that = this
    wx.showLoading({
      title: '拼命加载中...',
      mask: true,
    })
    console.log(start);
    console.log(end);
    var openid = wx.getStorageSync('openid')
    if (openid == '') {
      wx.showToast({
        title: '微信登陆失败',
        icon: 'none',
        duration: 1500
      })
    }else{
      wx.request({
        url: app.globalData.serverUrl + '/wx/cardinfo',
        method: 'GET',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          openid: openid,
          start: start,
          end: end
        },
        success: function (res) {
          console.log(res);
          wx.hideLoading()
          if (res.data.code == '200') {
            wx.setStorageSync('sid', res.data.data.sid);
            wx.setStorageSync('sname', res.data.data.sname);
            wx.setStorageSync('balance', res.data.data.balance);
            wx.setStorageSync('consumptions', res.data.data.consumptions);
            that.setData({
              sid: res.data.data.sid,
              sname: res.data.data.sname,
              balance: res.data.data.balance,
            })
            that.showInfo(res.data.data.consumptions)
            
          } else if (res.data.code == '201') {
            wx.showToast({
              title: '学号或校园卡密码错误，请重新绑定 ',
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
          else{
            wx.showToast({
              title: '验证码识别错误，请重试 ',
              icon: 'none',
              duration: 2000,
              // success: function () {
              //   setTimeout(function () {
              //     wx.navigateTo({
              //       url: '../ecardinfo/ecardinfo'
              //     })
              //   }, 1500)
              // }
            })
          }
        },
        fail: function (err) {
          wx.hideLoading()
          wx.showLoading({
            title: '当前查询人数过多，将尝试读取本地缓存',
          })
          wx.getStorage({
            key: 'consumptions',
            success: function (res) {
              wx.hideLoading()
              that.setData({
                sid: wx.getStorageSync('sid'),
                sname: wx.getStorageSync('sname'),
                balance: wx.getStorageSync('balance'),
              })
              that.showInfo(res.data.data.consumptions)
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

  /**
   * 自定义函数（数据统计）
   */
  showInfo(data){
    var consumptions = data
    var food=0, electric=0, network=0, shower=0, other=0
    if (consumptions != '' && consumptions != null){
      for (var i = 0; i < consumptions.length; i++){
        if (consumptions[i].content == '餐费支出'){
          food += parseFloat(consumptions[i].money)
        }
        else if (consumptions[i].content=='下沙锐捷缴费'){
          network += parseFloat(consumptions[i].money)
        }
        else if (consumptions[i].content=='购热水支出'){
          shower += parseFloat(consumptions[i].money)
        }
        else if (consumptions[i].content=='购电支出'){
          electric += parseFloat(consumptions[i].money)
        }
        else{
          other += parseFloat(consumptions[i].money)
        }
      }
    }
    this.setData({
      food: parseFloat(food).toFixed(2),
      electric: parseFloat(electric).toFixed(2),
      network: parseFloat(network).toFixed(2),
      shower: parseFloat(shower).toFixed(2),
      other: parseFloat(other).toFixed(2),
      total:parseFloat(food+electric+shower+other+network).toFixed(2)
    })
  }
})