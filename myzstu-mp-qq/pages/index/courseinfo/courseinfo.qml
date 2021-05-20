<view>
<scroll-view scroll-y="true" class="scroll">
  <view class="top">
    <view wx:for="{{['一','二','三','四','五','六','日']}}" class="top-text">周{{item}}</view>
  </view>
  <view class="main">
    <view class="period">
      <view wx:for="{{[1,2,3,4,5,6,7,8,9,10,11,12]}}" class="left">{{item}}</view>
    </view>
    <!--课表-->
    <view wx:for="{{wlist}}">
      <view class="flex-item kcb-item" bindtap="showCardView" data-statu="open" data-index="{{index}}" style="margin-left:{{(item.day-1)*100}}rpx;margin-top:{{(item.period-1)*100+5}}rpx;height:{{item.length*100-5}}rpx;background-color:{{colorArrays[index%9]}}">
        <view class="smalltext" >{{item.name+'@'+item.room}}</view>
      </view>
    </view>
  </view>
</scroll-view>

<movable-area>
  <movable-view x="{{x}}" y="{{y}}" direction="all"  bindtap="showsjCardView">实践课</movable-view>
</movable-area>
<modal hidden="{{hidesjCardView}}" title="我的实践课" no-cancel="{{noCancel}}" bindconfirm="confirmsjCard">
  <scroll-view scroll-y="true" style="max-height: 500rpx;">
    <view class="sjmodalcontent" wx:for="{{plist}}">
      <view>课程名称： {{item.name}}</view>
      <view>上课教师： {{item.teacher}}</view>
    </view>
    <view class="tip" wx:if="{{plist.length < 1}}">本学期没有实践课哦</view>
  </scroll-view>
</modal>

<modal hidden="{{hideCardView}}" title="课程信息" no-cancel="{{noCancel}}" bindconfirm="confirmCard">
  <view class="modalcontent">
    <view>课程名称： {{courseName}}</view>
    <view>上课时间： {{courseTime}}</view>
    <view>上课教师： {{courseTeacher}}</view>
    <view>上课地点： {{coursePlace}}</view>
  </view>
</modal>
</view>