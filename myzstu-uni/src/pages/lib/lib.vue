<template>
  <view class="container">
    <view class="nav">
      <view class="search-selectd" v-if="search" @tap="searchBind">图书检索</view>
      <view class="search" v-if="!search" @tap="searchBind">图书检索</view>
      <view class="mine" v-if="search" @tap="mineBind">我的借阅</view>
      <view class="mine-selected" v-if="!search" @tap="mineBind">我的借阅</view>
    </view>
    <view class="search-contaniner" v-if="search">
      <view class="searchbar">
        <input placeholder="请输入关键词" placeholder-style="color:#999" type="text"
          @confirm="searchBook" v-model="key" />
        <p bindtap="searchBook">搜一下</p>
      </view>
      <view class="tip">{{tip}}</view>
      <view class="res">
        <view class="book" v-for="item in booklist" :key="item.bookName">
          <p class="bookname">{{item.bookName}}</p>
          <view class="bookinfo">
            <p>出版社: {{item.press}}</p>
            <p>作者: {{item.author}}</p>
            <p>时间: {{item.time}}</p>
            <p>索书号: {{item.bookKey}}</p>
            <p>馆藏量: {{item.inventory}}</p>
            <p>可借量: {{item.available}}</p>
          </view>
        </view>
      </view>
    </view>
    <view class="mine-container" v-if="!search">
      <view class="res">
        <view class="title">当前借阅</view>
        <view class="tip" v-if="noborrow">当前没有借阅记录哦</view>
        <view class="book" v-for="item in borrowlist" :key="item.bookName">
          <p class="bookname">{{item.bookName}}</p>
          <view class="bookinfo">
            <p>借书日期: {{item.borrowDate}}</p>
          </view>
        </view>
        <view class="title history">历史借阅</view>
        <view class="tip" v-if="nohistory">没有历史借阅记录哦</view>
        <view class="book" v-for="item in historylist" :key="item.bookName">
          <p class="bookname">{{item.bookName}}</p>
          <view class="bookinfo">
            <p>借书日期: {{item.borrowDate}}</p>
          </view>
        </view>
      </view>
    </view>
  </view>

</template>

<script>
  const app = getApp()
  export default {
    data() {
      return {
        search: true,
        focus: true,
        key: '',
        booklist: [],
        tip: '最多可显示20本相关书籍哦',
        noborrow: false,
        nohistory: false,
        borrowlist: [],
        historylist: []
      }
    },
    methods: {

    },
    setKey: function(e) {
      this.setData({
        key: e.detail.value
      })
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

    },
    searchBind: function() {
      if (!this.data.search) {
        this.setData({
          search: true
        })
      }
    },
    mineBind: function(e) {
      if (wx.getStorageSync("sid") == "") {
        wx.showToast({
          title: '请绑定你的学号信息',
          icon: 'none',
          duration: 2000,
          success: function() {
            setTimeout(function() {
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
    searchBook: function() {
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
        url: app.globalData.serverUrl + '/wx/book',
        method: 'GET',
        header: {
          'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
        },
        data: {
          key: key
        },
        success: function(res) {
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
            } else {
              var resnum = res.data.data.length
              var num = resnum > 20 ? 20 : resnum
              that.setData({
                booklist: res.data.data,
                tip: '共检索到' + num + '本相关书籍'
              })
            }
          }
        },
        fail: function(err) {
          wx.hideLoading()
          wx.showToast({
            title: '检索失败',
            icon: 'none',
            duration: 1500
          })
        }
      })
    },
    getBorrowList: function() {
      var that = this
      var openid = wx.getStorageSync('openid')
      if (openid == '') {
        wx.showToast({
          title: '微信登陆失败',
          icon: 'none'
        })
      } else {
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
          success: function(res) {
            wx.hideLoading()
            console.log(res);
            if (res.data.code == '201') {
              wx.showToast({
                title: '学号错误,请重新绑定',
                icon: 'none',
                duration: 2000,
                success: function() {
                  setTimeout(function() {
                    wx.navigateTo({
                      url: '../../mine/userform/userform'
                    })
                  }, 1000)
                }
              })
            } else if (res.data.code == '200') {
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
            } else {
              that.getDataFromStorage()
            }
          },
          fail: function(err) {
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
        success: function(res) {
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
        fail: function() {
          wx.hideLoading()
          wx.showToast({
            title: '本地暂无缓存，请稍后再试',
            icon: 'none',
            duration: 1500
          })
        }
      })
    }
  }
</script>

<style>
  page{
    background:#FFF;
    padding-bottom: 15rpx;
  }
  .container{
    width:100%;
    font-size: 32rpx;
  }
  .nav{
    width: 100%;
    height: 80rpx;
    line-height: 80rpx;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    border-bottom: 1px solid rgb(221, 217, 217);
  }
  .nav view{
    width: 30%;
    text-align: center;
  }
  .nav .search-selectd, .nav .mine-selected{
    color: #31c27c;
    border-bottom: 8rpx solid #31c27c;
  }
  .nav .search, .nav .mine{
    color: #000;
    border-bottom: 0;
  }
  .search-contaniner{
    display: flex;
    flex-direction: column;
  }
  .search-contaniner .searchbar{
    margin: 30rpx auto;
    width: 94%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .search-contaniner .searchbar input{
    width: 550rpx;
    height: 80rpx;
    border: 1px solid #31c27c;
    border-top-left-radius: 40rpx;
    border-bottom-left-radius: 40rpx;
    padding-left: 30rpx;
  }
  .search-contaniner .searchbar p{
    width: 170rpx;
    height: 80rpx;
    line-height: 80rpx;
    background: #31c27c;
    color: #fff;
    text-align: center;
    border: 1px solid #31c27c;
    border-top-right-radius: 40rpx;
    border-bottom-right-radius: 40rpx;
  }
  .tip{
    width: 100%;
    color: #31c27c;
    font-size: 30rpx;
    text-align: center;
    margin: 15rpx 0;
  }
  .res{
    width: 100%;
    display:flex;
    flex-direction: column;
  }
  .res .book{
    width: 96%;
    display:flex;
    flex-direction:column;
    color:#000;
    font-size:32rpx;
    margin: 10rpx auto;
  }
  .book .bookname{
    line-height: 60rpx;
    background: #31c27c;
    border: 1px solid #31c27c;
    border-top-left-radius: 12rpx;
    border-top-right-radius: 12rpx;
    color: #fff;
    padding: 10rpx;
  }
  .book .bookinfo{
    display: flex;
    flex-direction: column;
    border: 1px solid #31c27c;
    background:#fff;
    border-bottom-left-radius: 12rpx;
    border-bottom-right-radius: 12rpx;
    padding: 10rpx;
  }
  .book .bookinfo p{
    height: 65rpx;
    line-height: 65rpx;
  }
  
  .mine-container{
    width: 100%;
    display: flex;
    flex-direction: column;
    padding: 15rpx 0;
  }
  .title{
    width: 96%;
    color: #31c27c;
    padding-left: 10rpx;
    border-left: 8rpx solid #31c27c;
    margin: 10rpx 0 15rpx 20rpx;
    font-weight: bold;
    font-size: 32rpx;
  }
  .hsitory{
    margin-top: 30rpx;
  }
</style>
