<view class="container">
  <view class="card">
    <view class="cardname">校园卡</view>
    <image src="../../../images/zstu.png"></image>
    <view class="name">账户余额</view>
    <view class="balance">￥{{balance}}</view>
    <view class="sid">{{sid}}</view>
    <view class="sname">{{sname}}</view>
  </view>
  <view class="consumptionheader">消费统计</view>
  <picker mode="selector" bindchange="bindValueChange" value="{{yearindex}}" range="{{years}}">
    <view class="picker">
      {{years[yearindex]}}<image class="down" src="../../../images/down.svg"></image>
    </view>
  </picker>
  <view class="consumption">
    <view class="tip"><view class="left">餐厅消费</view><view class="right">￥{{food}}</view></view>
    <view class="tip"><view class="left">寝室电费</view><view class="right">￥{{electric}}</view></view>
    <view class="tip"><view class="left">洗澡费用</view><view class="right">￥{{shower}}</view></view>
    <view class="tip"><view class="left">锐捷缴费</view><view class="right">￥{{network}}</view></view>
    <view class="tip"><view class="left">其他缴费(医保等)</view><view class="right">￥{{other}}</view></view>
    <view class="tip"><view class="left">消费总计</view><view class="right">￥{{total}}</view></view>
  </view>
  <view class="notice">
    <p>说明：在爬取校园卡消费信息的过程中利用了智能识别验证码的技术，如果碰到'验证码识别错误'的提示信息，可再次尝试获取</p>
  </view>
</view>
