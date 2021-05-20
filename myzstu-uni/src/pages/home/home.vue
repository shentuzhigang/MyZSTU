<template>
  <view class="container">
    <view class="title">即时课表</view>
    <navigator class="coursecard" :url="courseurl">
      <view v-if="hasCourse">
        <view class="top">
          <view class="content">
            <view class="course-item">课程名称: {{currentCourse.name}}</view>
            <view class="course-item">上课时间: {{currentCourse.time}}</view>
            <view class="course-item">上课教师: {{currentCourse.teacher}}</view>
            <view class="course-item">上课地点: {{currentCourse.place}}</view>
          </view>
        </view>
        <view class="bottom">点击查看全部课表</view>
      </view>
      <view class="tip" v-else>
          <text>{{tip}}</text>
          <text style="font-size: small;" v-if="binding">(点击查看全部课表)</text>
      </view>
    </navigator>
    <view class="title">教务查询</view>
    <view class="btn_group">
      <navigator class="btn exam" :url="examurl">
        <image class="btn_icon" src="/static/images/exam.svg"></image>
        <view class="btn_info">考试查询</view>
      </navigator>
      <navigator class="btn score" :url="scoreurl">
        <image class="btn_icon" src="/static/images/score.svg"></image>
        <view class="btn_info">成绩查询</view>
      </navigator>
    </view>
<!--    <view class="title">更多功能</view>
    <view class="btn_group">
      <navigator class="btn ecard" :url="ecardurl">
        <image class="btn_icon" src="/static/images/ecard.svg"></image>
        <view class="btn_info">校园一卡通</view>
      </navigator>
      <navigator class="btn library" :url="liburl">
        <image class="btn_icon" src="/static/images/library.svg"></image>
        <view class="btn_info">掌上图书馆</view>
      </navigator>
    </view> -->
  </view>
</template>

<script>
  const app = getApp()
  import util from '@/utils/util.js'
  
  import {getCourses} from '@/api/edu.js'
  
  export default {
    data() {
      return {
        tip: '',
        courseurl: '/pages/edu/course/course',
        examurl: '/pages/edu/exam/exam',
        scoreurl: '/pages/edu/score/score',
        ecardurl: '/pages/ecard/ecard',
        liburl: '/pages/lib/lib',
        currentWeek: 1,
        currentCourse: null,
        binding: false
      }
    },
    computed: {
      hasCourse() {
        return this.currentCourse != null
      }
    },
    created() {
      this.binding = uni.getStorageSync("binding") == true
    },
    mounted() {
      var sid = uni.getStorageSync('sid')
      if (sid == null || sid == '') {
        this.courseurl = '../userform/userform'
        this.examurl = '../userform/userform'
        this.scoreurl = '../userform/userform'
        this.ecardurl = '../userform/userform'
        this.liburl = '../userform/userform'
        this.showTip('点击绑定个人信息')
      } else {
        var edupw = uni.getStorageSync('edupw')
        if (edupw == null || edupw == '') {
          this.courseurl = '../userform/userform'
          this.examurl = '../userform/userform'
          this.scoreurl = '../userform/userform'
          this.showTip('点击绑定教务信息')
        } else {
          this.showCurrentCourse()
        }

        var ecardpw = uni.getStorageSync('ecardpw')
        if (ecardpw == null || ecardpw == '') {
          this.ecardurl = '../userform/userform'
        }

        var libpw = uni.getStorageSync('libpw')
        if (libpw == null || libpw == '') {
          this.liburl = '../userform/userform'
        }
      }
    },
    methods: {
      getCurrentWeek: function() {
        return new Promise((resolve, reject) => {
          uniCloud.callFunction({
            name: 'currentWeek'
          }).then(res => {
            if (res.result.code == 0) {
              this.currentWeek = res.result.data
              resolve(res.result.data)
            } else {
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
      showTip: function(msg) {
        this.currentCourse = null
        this.tip = msg
      },
      showCurrentCourse: function() {
        uni.showLoading({
          title: '拼命加载中...'
        })
        getCourses()
          .then(courses => {
            this.getCurrentWeek()
              .then(week => {
                this.currentCourse = this.getCurrentCourse(courses,week)
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
      /**
       * 显示课表
       * courses 
       * week 指当前是第几教学周
       */
      getCurrentCourse: function(courses, week) {
        var that = this
        var kb = courses.kbList
        var timeTable = app.globalData.timeTable
        var date = new Date()
        var today = date.getDay() //今天星期几
        var data = [] //存放今天该上的课
        if (today == 0) {
          today = 7;
        }
        for (var i = 0; i < kb.length; i++) {
          var weeks =kb[i].zcd.split('-')
          var start = parseInt(weeks[0])
          var end = parseInt(weeks[1])
          if (kb[i].xqj == today && (week >= start && week <= end)) {
            data.push(kb[i])
          }
        }
        if (data.length == 0) {
          this.showTip('今日没课，好好休息哦!')
          return null
        }
        //对今天要上的课按时间排序
        data.sort(function(a, b) {
          return a.jcs - b.jcs;
        })
        var show = null
        //找到下一节要上或者正在上的课
        for (var i = 0; i < data.length; i++) {
          var now = new Date()
          var year = now.getFullYear()
          var month = now.getMonth()
          var day = now.getDate()
          var period = timeTable[parseInt(data[i].jcs.split('-')[1])]
          var hour = period.split(':')[0]
          var min = period.split(':')[1]
          var time = new Date(year, month, day, hour, min, 0)
          console.log(now)
          console.log(time)
          
          if (now - time < 0) {
            show = data[i]
            break;
          }
        }
        console.log(show)
        if (show == null) {
          this.showTip('今天的课上完喽!')
          return null
        } else {
          var times = []
          var jcs = data[i].jcs.split('-')
          var start = jcs[0]
          var end = jcs[1]
          for (var i = start; i <= end; i++) {
            times.push(i)
          }
          return {
            name: show.kcmc,
            time: show.zcd + '第' + times.join(',') + '节',
            teacher: show.xm ? show.xm : "未安排",
            place: show.cdmc ? show.cdmc : "未安排"
          }
        }
      }
    },
    onLoad: function(options) {

    },
    onShow: function() {

    },
    onPullDownRefresh: function() {

    }
  }
</script>

<style>
  page {
    background: #FFF;
    padding-bottom: 15rpx;
  }

  .container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 34rpx;
  }

  .title {
    width: 700rpx;
    color: #31c27c;
    padding-left: 10rpx;
    border-left: 8rpx solid #31c27c;
    margin: 10rpx 0 15rpx 40rpx;
    font-weight: bold;
  }



  .btn_group {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    width: 730rpx;
    margin-bottom: 40rpx;
  }

  .btn {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 15rpx;
    margin-bottom: 10rpx;
    border-radius: 12rpx;
    height: 100rpx;
    width: 300rpx;
    background: #fff;
    box-shadow: 5px 5px 5px #E8E8E8;
    border: 1px solid #F5F5F5;
  }

  .btn_icon {
    width: 60rpx;
    height: 60rpx;
    margin-left: 20rpx;
  }

  .btn_info {
    margin-left: 20rpx;
    color: #000;
  }
</style>

<style lang="scss" scoped>
  .coursecard {
    width: 90%;
    background: #31c27c;
    color: #FFF;
    margin-bottom: 40rpx;
    border-radius: 12rpx;
    box-shadow: 8px 8px 5px rgb(223, 223, 223);
    text-align: center;

    .top {
      height: 300rpx;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;

      .content {
        display: flex;
        flex-direction: column;
        padding: 20rpx;
        text-align: left;
        view {
          font-size: 32rpx;
          height: 60rpx;
          line-height: 60rpx;
        }
      }
    }

    .bottom {
      height: 80rpx;
      line-height: 80rpx;
      border-top: 1px dashed #fff;
    }

    .tip {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-self: center;
      font-size: 32rpx;
      height: 380rpx;
      line-height: 60rpx;
    }
  }
</style>
