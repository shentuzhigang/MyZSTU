<view class="header">
</view>
<view class="container" wx:if="{{phone}}">
  <view class="category">生活类</view>
  <view wx:for="{{life}}" class="item"  bindtap='call' data-item="{{item}}">
    <view class="name">{{item.name}}</view>
    <view class="number"  >{{item.number}}</view>
  </view>
  <view class="category">办公类</view>
  <view wx:for="{{work}}" class="item" bindtap='call' data-item="{{item}}">
    <view class="name">{{item.name}}</view>
    <view class="number">{{item.number}}</view>
  </view>
</view>