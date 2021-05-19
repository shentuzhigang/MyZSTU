<template>
  <view>
    <scroll-view scroll-y="true" class="scroll">
      <view class="top">
        <view v-for="item in ['一','二','三','四','五','六','日']" :key="item" class="top-text">
          周{{item}}
        </view>
      </view>
      <view class="main">
        <view class="period">
          <view v-for="item in [1,2,3,4,5,6,7,8,9,10,11,12]" :key="item" class="left">{{item}}</view>
        </view>
        <!--课表-->
        <view v-for="(item,index) in wlist" :key="item.kcmc">
          <view class="flex-item kcb-item" @tap="showCardView(item)" 
            :style="{marginLeft: (parseInt(item.xqj) - 1) * 100 + 'rpx',marginTop: (parseInt(item.jcs.split('-')[0]) - 1) * 100 + 5 + 'rpx', height: (parseInt(item.jcs.split('-')[1]) - parseInt(item.jcs.split('-')[0]) + 1) * 100 - 5 + 'rpx',backgroundColor: colorArrays[index % 9]}">
            <view class="smalltext">{{item.kcmc+'@'+item.cdmc}}</view>
          </view>
        </view>
      </view>
    </scroll-view>

    <movable-area>
      <movable-view :x="x" :y="y" direction="all" @tap="showsjCardView">实践课</movable-view>
    </movable-area>
    <modal title="我的实践课" :hidden="hidesjCardView" :no-cancel="noCancel" @confirm="confirmsjCard">
      <scroll-view scroll-y="true" style="max-height: 500rpx;">
        <view class="sjmodalcontent" v-for="item in plist" :key="item.name">
          <view>课程名称： {{item.kcmc}}</view>
          <view>上课教师： {{item.jsxm}}</view>
        </view>
        <view class="tip" v-if="plist.length < 1">本学期没有实践课哦</view>
      </scroll-view>
    </modal>

    <modal title="课程信息" :hidden="hideCardView" :no-cancel="noCancel" @confirm="confirmCard">
      <view class="modalcontent">
        <view>课程名称： {{courseName}}</view>
        <view>上课时间： {{courseTime}}</view>
        <view>上课教师： {{courseTeacher}}</view>
        <view>上课地点： {{coursePlace}}</view>
      </view>
    </modal>
  </view>
</template>

<script>
  const app = getApp()

  import util from '@/utils/util.js'

  import {
    getCourses
  } from '@/api/edu.js'

  export default {
    data() {
      return {
        x: 650,
        y: 450,
        colorArrays: ['#ef5b9c', '#f15b6c', '#f26522', '#ffd400', '#8552a1',
          '#7fb80e', '#65c294', '#78cdd1', '#33a3dc'
        ],
        wlist: [],
        plist: [],
        noCancel: true,
        hidesjCardView: true,
        hideCardView: true,
        courseName: '',
        courseTime: '',
        courseTeacher: '',
        coursePlace: '',
      }
    },
    computed: {

    },
    mounted() {
      this.showCourses()
    },
    methods: {
      showsjCardView: function(event) {
        this.hidesjCardView = false
      },
      confirmsjCard: function() {
        this.hidesjCardView = true
      },
      showCardView: function(course) {
        var times = []
        var jcs =  course.jcs.split('-')
        var start = jcs[0]
        var end = jcs[1]
        for (var i = start; i <= end; i++) {
          times.push(i)
        }
        this.hideCardView = false
        this.courseName = course.kcmc
        this.courseTime = course.zcd + '第' + times.join(',') + '节'
        this.courseTeacher = course.xm ? course.xm : "未安排"
        this.coursePlace = course.cdmc ? course.cdmc : "未安排"
      },
      confirmCard: function() {
        this.hideCardView = true
      },
      getCurrentWeek: function() {
        return new Promise((resolve, reject) => {
          uniCloud.callFunction({
            name: 'currentWeek'
          }).then(res => {
            if (res.result.code == 0) {
              this.currentWeek = res.result.data
              resolve(res.result.data)
            } else {
              // TODO 考虑手动收入周
              this.showTip('数据获取失败,请稍后再试')
              reject('数据获取失败,请稍后再试')
            }
          }).catch(err => {
            console.log(err)
            this.showTip('数据获取失败,请稍后再试')
            reject(err)
          })
        })
      },
      showCourses: function() {
        uni.showLoading({
          title: '拼命加载中...'
        })
        getCourses()
          .then(courses => {
            this.getCurrentWeek()
              .then(week => {
                console.log("本周是第" + week + "周")
                uni.setNavigationBarTitle({
                  title: '第' + week + '周课表'
                })
                var {
                  w,
                  p
                } = this.getCurrentWeekCourses(courses, week)
                this.wlist = w
                this.plist = p
                uni.hideLoading()
              })
          })
          .catch(err => {
            uni.hideLoading()
            this.showTip('数据获取失败,请稍后再试')
            uni.showToast({
              title: '无法获取课程信息，请稍后再试',
              icon: 'none',
              duration: 1500
            })
          })
      },
      getCurrentWeekCourses: function(courses, week) {
        var usual = [] //一般课
        var practices = courses.sjkList //实践课

        for (var i = 0; i < courses.kbList.length; i++) {
          var weeks = courses.kbList[i].zcd.split('-')
          var start = parseInt(weeks[0])
          var end = parseInt(weeks[1])
          if (week >= start && week <= end) {
            usual.push(courses.kbList[i])
          }
        }

        return {
          w: usual,
          p: practices
        }
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
    onPullDownRefresh: function() {},

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
  .top {
    display: flex;
    flex-direction: row;
    padding-left: 35rpx;
    background-color: #31c27c;
    color: #fff;
  }

  .top-text {
    width: 100rpx;
    height: 35rpx;
    font-size: 26rpx;
    justify-content: center;
    display: flex;
    align-items: center;
  }

  .main {
    height: 1200rpx;
    width: 730rpx;
    display: flex;
  }

  .flex-item {
    width: 95rpx;
    height: 200rpx;
  }

  .kcb-item {
    position: absolute;
    justify-content: center;
    display: flex;
    align-items: center;
    border-radius: 12rpx;
    overflow: hidden;
  }

  .period {
    background-color: #31c27c;
    color: #fff;
  }

  .smalltext {
    font-size: 22rpx;
    color: #fff;
    text-align: center;
  }

  .scroll {
    height: 1205rpx;
    z-index: 100;
    position: fixed;
  }

  .left {
    width: 35rpx;
    height: 100rpx;
    font-size: 26rpx;
    justify-content: center;
    display: flex;
    align-items: center;
  }

  movable-area {
    position: absolute;
    z-index: 110;
    top: 35rpx;
    left: 35rpx;
    height: 1170rpx;
    width: 715rpx;
    pointer-events: none;
  }

  movable-view {
    width: 120rpx;
    height: 120rpx;
    line-height: 120rpx;
    background: #31c27c;
    border-radius: 60rpx;
    font-size: 28rpx;
    text-align: center;
    color: #fff;
    pointer-events: auto;
    opacity: 0.8;
  }

  modal .sjmodalcontent {
    color: #000;
    line-height: 60rpx;
    margin-top: 15rpx;
    border-bottom: 1px solid rgb(221, 217, 217);
  }

  modal .tip {
    color: #000;
    line-height: 60rpx;
    text-align: center;
  }

  modal .modalcontent {
    color: #000;
    line-height: 60rpx;

  }
</style>
