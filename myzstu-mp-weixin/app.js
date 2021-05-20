//app.js
App({
  onLaunch: function () {
    const updateManager = wx.getUpdateManager()
    updateManager.onCheckForUpdate(function (res) {
      console.log(res.hasUpdate)
    })

    updateManager.onUpdateReady(function () {
      wx.showModal({
        title: '更新提示',
        content: '新版本已经准备好，是否重启应用？',
        success(res) {
          if (res.confirm) {
            updateManager.applyUpdate()
          }
        }
      })
    })
    
    var that=this
    wx.login({
      success: res => {
        if(res.code){
          // 发送 res.code 到后台换取 openId, sessionKey, unionId
          wx.request({
            url: that.globalData.serverUrl+'/wx/openid',
            method:'GET',
            data:{
              code:res.code
            },
            success:function(res){
              wx.setStorageSync('openid', res.data.data)
              wx.getSetting({
                success: res => {
                  if (res.authSetting['scope.userInfo']) {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                    wx.getUserInfo({
                      lang:'zh_CN',
                      success: res => {
                        // 可以将 res 发送给后台解码出 unionId
                        that.globalData.userInfo = res.userInfo

                        // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                        // 所以此处加入 callback 以防止这种情况
                        if (that.userInfoReadyCallback) {
                          that.userInfoReadyCallback(res)
                        }
                      }
                    })
                  }
                  else{

                  }
                }
              })
            }
          })
        }
        else{
          wx.showToast({
            title: '微信登陆失败',
            icon: 'none'
          })          
        }
      },
      fail:err=>{
        wx.showToast({
          title: '微信登陆失败',
          icon:'none'
        })
      }
    })
  },
  globalData: {
    userInfo: null,
    //serverUrl:'https://www.dysaniacynic.cn/ZstuHelperTransfer',
    serverUrl: 'https://www.shentuzhigang.top:8443/MyZSTU',
    //serverUrl: 'http://127.0.0.1/MyZSTU',
    timeTable:{
      1:'8.55',
      2:'9.45',
      3:'10.45',
      4:'11.35',
      5:'12.25',
      6:'14.15',
      7:'15.05',
      8:'15.55',
      9:'16.45',
      10:'19.15',
      11:'20.05',
      12:'20.55'
    }
  }
})