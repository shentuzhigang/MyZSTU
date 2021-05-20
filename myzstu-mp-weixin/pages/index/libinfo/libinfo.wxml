<view class="container">
  <view class="nav">
    <view class="search-selectd" wx:if="{{search}}" bindtap="searchBind">图书检索</view>
    <view class="search" wx:if="{{!search}}" bindtap="searchBind">图书检索</view>
    <view class="mine" wx:if="{{search}}" bindtap="mineBind">我的借阅</view>
    <view class="mine-selected" wx:if="{{!search}}" bindtap="mineBind">我的借阅</view>
  </view>
  <view class="search-contaniner" wx:if="{{search}}">
    <view class="searchbar">
      <input placeholder="请输入关键词" placeholder-style="color:#999" type="text" bindinput='setKey' bindconfirm="searchBook" value="{{key}}" />
      <p bindtap="searchBook">搜一下</p>
    </view>
    <view class="tip">{{tip}}</view>
    <view class="res">
      <view class="book" wx:for="{{booklist}}">
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
  <view class="mine-container" wx:if="{{!search}}">
    <view class="res">
      <view class="title">当前借阅</view>
      <view class="tip" wx:if="{{noborrow}}">当前没有借阅记录哦</view>
      <view class="book" wx:for="{{borrowlist}}">
        <p class="bookname">{{item.bookName}}</p>
        <view class="bookinfo">
          <p>借书日期: {{item.borrowDate}}</p>
        </view>
      </view>
      <view class="title history">历史借阅</view>
      <view class="tip" wx:if="{{nohistory}}">没有历史借阅记录哦</view>
      <view class="book" wx:for="{{historylist}}">
        <p class="bookname">{{item.bookName}}</p>
        <view class="bookinfo">
          <p>借书日期: {{item.borrowDate}}</p>
        </view>
      </view>
    </view>
  </view>
</view>
