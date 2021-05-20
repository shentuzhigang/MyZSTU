const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    success: false,
    examlist:[]
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
    var that = this
    var openid = wx.getStorageSync('openid')
    if (openid == '') {
      wx.showToast({
        title: '微信登陆失败',
        icon: 'none',
        duration: 1500
      })
    } else {
      wx.showLoading({
        title: '拼命加载中...',
      })
      wx.request({
        url: app.globalData.serverUrl + '/wx/exams',
        method: 'GET',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        },
        data: {
          openid: openid
        },
        success: function (res) {
          wx.hideLoading()
          if (res.data.code == "201"){
            wx.showToast({
              title: '学号错误,请重新绑定',
              icon: 'none',
              duration: 2000,
              success: function () {
                setTimeout(function () {
                  wx.navigateTo({
                    url: '../../mine/userform/userform'
                  })
                }, 1500)
              }
            })
          }
          else if(res.data.code == "202") {
            wx.showToast({
              title: '教务系统密码错误,请重新绑定',
              icon: 'none',
              duration: 2000,
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
            if(res.data.data != null){

              var examlist=res.data.data;
              for (let i in Array.from(examlist)){
                  let bk=0.0;
                  let datas=examlist[i].courseDatas
                  for(let j in Array.from(datas)){
                    bk+=Number(datas[j].bkrs)/Number(datas[j].skrs);
                  }
                  
                  if(examlist[i].courseDatas.length!==0){
                      examlist[i].bkl=(Math.round(bk/examlist[i].courseDatas.length * 10000) / 100.00 + "%");
                  }else{
                    examlist[i].bkl=bk;
                  } 
              }
              that.setData({
                examlist: examlist
              })
              wx.setStorageSync('examlist', examlist)
            }
          }
          else {
            wx.showLoading({
              title: '当前查询人数过多，将尝试读取本地缓存',
            })
            wx.getStorage({
              key: 'examlist',
              success: function (res) {
                wx.hideLoading()
                that.setData({
                  examlist: res.data
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
        },
        fail: function (err) {
          wx.hideLoading()
          wx.showLoading({
            title: '当前查询人数过多，将尝试读取本地缓存',
          })
          wx.getStorage({
            key: 'examlist',
            success: function (res) {
              wx.hideLoading()
              that.setData({
                examlist: res.data
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