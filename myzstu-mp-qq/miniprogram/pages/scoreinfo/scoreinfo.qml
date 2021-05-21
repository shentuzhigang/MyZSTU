<view class="container">
  <picker mode="selector" bindchange="bindValueChange" value="{{yearindex}}" range="{{years}}">
    <view class="picker">
      {{years[yearindex]}}学期<image class="down" src="../../../images/down.svg"></image>
    </view>
  </picker>
  <view class="scoreinfo">
    <view class="title">成绩单</view>
    <view class="header">
      <view class="name">课程名</view>
      <view class="credit">学分</view>
      <view class="grade">成绩</view>
      <view class="gpa">绩点</view>
    </view>
    <view class="noscore" wx:if="{{scorelist.length < 1}}">暂无成绩信息</view>
    <view class="content" wx:for="{{scorelist}}">
      <view class="name">{{item.courseName}}</view>
      <view class="credit">{{item.credit}}</view>
      <view class="grade">{{item.grade}}</view>
      <view class="gpa">{{item.gpa}}</view>
    </view>
  </view>
  <view class="scoresum">
    <view class="title">成绩统计</view>
    <view class="content">
      <view class="name">学期总学分</view>
      <view>{{credit_semester}}</view>
    </view>
    <view class="content">
      <view class="name">学期平均绩点</view>
      <view>{{avggpa_semester}}</view>
    </view>
    <view class="content">
      <view class="name">学年总学分</view>
      <view>{{credit_year}}</view>
    </view>
    <view class="content">
      <view class="name">学年平均绩点</view>
      <view>{{avggpa_year}}</view>
    </view>
    <view class="content">
      <view class="name">所有课程总学分</view>
      <view>{{credit_all}}</view>
    </view>
    <view class="content">
      <view class="name">所有课程平均绩点</view>
      <view>{{avggpa_all}}</view>
    </view>
  </view>
</view>
