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
		<modal title="实践课" :hidden="hidesjCardView" :no-cancel="noCancel" @confirm="confirmsjCard">
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
	export default {
		name:"course-table",
		data() {
			return {
			  x: 650,
			  y: 450,
			  colorArrays: ['#ef5b9c', '#f15b6c', '#f26522', '#ffd400', '#8552a1',
			    '#7fb80e', '#65c294', '#78cdd1', '#33a3dc'
			  ],
			  noCancel: true,
			  hidesjCardView: true,
			  hideCardView: true,
			  courseName: '',
			  courseTime: '',
			  courseTeacher: '',
			  coursePlace: '',
			}
		},
    props: {
      wlist: {
        type:Array,
        default:[]
      },
      plist: {
        type:Array,
        default:[]
      }  
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
      }
    }  
	}
</script>

<style lang="scss">
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
