<view class="container">
  <view class="userinfo">
    <button class="buttonstyle" wx:if="{{!hasUserInfo && canIUse}}" open-type="getUserInfo" bindgetuserinfo="getUserInfo" lang="zh_CN">授权登录</button>
    <block wx:else>
      <image bindtap="bindViewTap" class="userinfo_avatar" src="{{userInfo.avatarUrl}}" background-size="cover"></image>
      <view class="information">
        <text class="userinfo_nickname">{{userInfo.nickName}}</text>
        <text class="userinfo_address">{{userInfo.country}} · {{userInfo.province}} · {{userInfo.city}}</text>
      </view>
    </block> 
  </view>
  <view class="btn_group">
    <navigator class="btn" url="userform/userform" hover-class="navigator-hover">
      <image class="btn_icon" src="../../images/binding.svg"></image>
      <view class="btn_info">信息绑定</view>
      <image class="btn_to" src="../../images/arrow.svg"></image>
    </navigator>
    <navigator class="btn" url="schcalendar/schcalendar">
      <image class="btn_icon" src="../../images/calendar.svg"></image>
      <view class="btn_info">浙理校历</view>
      <image class="btn_to" src="../../images/arrow.svg"></image>
    </navigator>
    <navigator class="btn" url="phone/phone">
      <image class="btn_icon" src="../../images/phone.svg"></image>
      <view class="btn_info">常用电话</view>
      <image class="btn_to" src="../../images/arrow.svg"></image>
    </navigator>
    <view class="btn" bindtap="clean">
      <image class="btn_icon" src="../../images/clean.svg"></image>
      <view class="btn_info">清除缓存</view>
      <image class="btn_to" src="../../images/arrow.svg"></image>
    </view>
    <navigator style="margin-bottom:30rpx;" class="btn" url="aboutus/aboutus">
      <image class="btn_icon" src="../../images/aboutus.svg"></image>
      <view class="btn_info">关于我们</view>
      <image class="btn_to" src="../../images/arrow.svg"></image>
    </navigator>
  </view>
</view>