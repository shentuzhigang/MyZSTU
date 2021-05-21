<view class="examlist" >
  <view class="exam" qq:for="{{examlist}}">
    <p class="examname">{{item.courseName}}</p>
    <view class="examinfo">
      <p>考试时间 :  {{item.examTime}}<p qq:if="{{item.examTime.length == 0}}">暂未安排</p></p>
      <p>考试地点 : {{item.examPlace}}<p qq:if="{{item.examPlace.length  == 0}}">暂未安排</p></p>
      <p>考试座位 : {{item.seatNumber}}<p qq:if="{{item.seatNumber.length == 0}}">暂未安排</p></p>
      <p>综合补考率 : <p qq:if="{{item.bkl == 0.0}}">暂无数据</p><p qq:else>{{item.bkl}}</p></p>
    </view>
  </view>
</view>

