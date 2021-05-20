<template>
  <view>
    <view class="header">
      <image src="/static/images/zstu.jpg"></image>
    </view>
    <view class="container">
      <view class='info-a'>
        <text space="emsp"> 你的学号：</text>
        <input type="number" length="13" v-model="sid" focus="ture" 
          placeholder="请输入你的学号" placeholder-class='font-size: 34rpx;' />
      </view>
      <view class='info-a'>
        <text space="emsp">教务系统密码：</text>
        <input type="text" password="true" v-model="edupw" 
          placeholder="默认为身份证号后六位" placeholder-class='font-size: 34rpx;' />
      </view>
<!--      <view class='info-a'>
        <text space="emsp"> 校园卡密码：</text>
        <input type="number" password="true" v-model="ecardpw" length="6"
          placeholder="默认为身份证号后六位" placeholder-class='font-size: 34rpx;'  />
      </view>
      <view class='info-a'>
        <text space="emsp">图书馆密码：</text>
        <input type="text" password="true" v-model="libpw" 
          placeholder="默认为身份证号后六位" placeholder-class='font-size: 34rpx;' />
      </view>
      <view class='info-b'>
        <text space="emsp">认证中心密码：</text>
        <input type="text" password="true" v-model="ssopw" 
          placeholder="默认为身份证号后六位" placeholder-class='font-size: 34rpx;' />
      </view> -->
      <button @tap="saveInfo" id="savebtn" type="primary">
        <text space="emsp">确 认</text>
      </button>
<!--      <view class="tip">
        <p>温馨提示：</p>
        <p>1、校园卡密码中的"X"用"0"代替</p>
        <p>2、认证中心指浙江理工大学统一身份认证网站（https://sso.zstu.edu.cn）</p>
      </view> -->
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        sid: '',
        edupw: '',
        ecardpw: '',
        libpw: '',
        ssopw: ''
      }
    },
    methods: {
      saveInfo: function() {
        var openid = uni.getStorageSync('openid')
        if (openid == "") {
          uni.showToast({
            title: '微信登录失败',
            icon: "none",
            duration: 1500
          })
        } else if (this.sid.length != 13) {
          uni.showToast({
            title: '请填写正确的学号',
            icon: "none",
            mask: true,
            duration: 1500
          })
        } else { 
          try {
            uni.setStorageSync('sid', this.sid);
            uni.setStorageSync('edupw', this.edupw);
            uni.setStorageSync('ecardpw', this.ecardpw);
            uni.setStorageSync('libpw', this.libpw);
            uni.setStorageSync('ssopw', this.ssopw);
            uni.showToast({
              title: '绑定成功',
              icon: "success",
              duration: 1000,
              success: function() {
                setTimeout(function() {
                  uni.reLaunch({
                    url: '../home/home',
                  })
                }, 1000)
              }
            })
          } catch (e) {
            console.log(e);
          }
        }
      },
      uploadInfo(){
        uni.request({
          url: app.globalData.serverUrl + '/wx/student',
          method: 'POST',
          header: {
            'content-type': 'application/x-www-form-urlencoded'
          },
          data: {
            openid: this.openid,
            sid: this.sid,
            edupw: this.edupw,
            ecardpw: this.ecardpw,
            libppw: this.libpw,
            ssopw: this.ssopw
          },
          success: function(res) {
           
          }
        })
      }
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
      try {
        this.sid = uni.getStorageSync('sid')
        this.edupw = uni.getStorageSync('edupw')
        this.ecardpw = uni.getStorageSync('ecardpw')
        this.libpw = uni.getStorageSync('libpw')
        this.ssopw = uni.getStorageSync('ssopw')
      } catch (e) {
        console.log(e)
        uni.showToast({
          title: '信息读取失败',
          icon: 'none',
          duration: 1500
        })
      }
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
  }
</script>

<style>
  page {
    background: #FFF;
    padding-bottom: 15rpx;
  }

  .header {
    display: flex;
    justify-content: center;
    margin: 15px auto;
  }

  image {
    width: 520rpx;
    height: 120rpx;
  }

  .container {
    width: 100%;
    display: flex;
    flex-direction: column;
    font-size: 34rpx;
  }

  .info-a {
    width: 95%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin: 15rpx auto;
  }

  .info-a text {
    float: left;
  }

  .info-a input {
    height: 90rpx;
    width: 400rpx;
    line-height: 100rpx;
    background: #FFF;
    border: #31c27c 1px solid;
    border-radius: 12rpx;
    padding-left: 20rpx;
  }

  .info-b {
    width: 95%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    margin: 15rpx 0;
  }

  .info-b text {
    float: left;
  }

  .info-b input {
    width: 400rpx;
    height: 90rpx;
    border: #31c27c 1px solid;
    border-radius: 12rpx;
    padding-left: 20rpx;
  }

  #savebtn {
    margin-top: 40rpx;
    width: 50%;
    height: 90rpx;
    line-height: 90rpx;
    background-color: #31c27c;
    font-size: 36rpx;
  }

  .tip {
    margin-top: 50rpx;
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }

  .tip p {
    height: 70rpx;
    line-height: 70rpx;
    color: rgb(112, 110, 110);
    font-size: 30rpx;
  }
</style>
