<template>
  <view class="container">
    <view class="selector-container">
      <view class="selector-item">
        <view>学年</view>
        <view>
          <picker :value="yearIndex" :range="xnms" @change="bindYearChange" range-key=xnmmc>
            <view class="picker">
              <text>{{xnms[yearIndex].xnmmc}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>学年</view>
        <view>
          <picker :value="termIndex" :range="terms" @change="bindTermChange" range-key=xqname>
            <view class="picker">
              <text>{{terms[termIndex].xqname}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>校区</view>
        <view>
          <picker :value="xqIndex" :range="xqs" @change="bindXQChange" range-key=name>
            <view class="picker">
              <text>{{xqs[xqIndex].name}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>年级</view>
        <view>
          <picker :value="njIndex" :range="njs" @change="bindnjChange">
            <view class="picker">
              <text>{{njs[njIndex]}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>学院</view>
        <view>
          <picker :value="xyIndex" :range="xyList" @change="bindxyChange" range-key=jgmc>
            <view class="picker">
              <text>{{xyList[xyIndex].jgmc}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>专业</view>
        <view>
          <picker :value="zyIndex" :range="zyList" @change="bindzyChange" range-key=zymc>
            <view class="picker">
              <text>{{zyList[zyIndex].zymc}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>专业方向</view>
        <view>
          <picker :value="zyfxIndex" :range="zyfxList" @change="bindzyfxChange" range-key=zyfxmc>
            <view class="picker">
              <text>{{zyfxList[zyfxIndex].zyfxmc}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
      <view class="selector-item">
        <view>班级</view>
        <view>
          <picker :value="bjIndex" :range="bjList" @change="bindbjChange" range-key=bj>
            <view class="picker">
              <text>{{bjList[bjIndex].bj}}</text>
              <image class="down" src="/static/images/down.svg"></image>
            </view>
          </picker>
        </view>
      </view>
    </view>
    <button @tap="submit" id="btn" type="primary">
      <text space="emsp">查 询</text>
    </button>
  </view>

</template>

<script>
  import {
    nowSchoolYearAndTerm
  } from '@/utils/edu.js'
  import {
    getJgList,
    getZyList,
    getZyfxList,
    getBjList
  } from '@/api/edu.js'
  export default {
    data() {
      return {
        yearIndex: -1,
        termIndex: -1,
        xqIndex: 0,
        njIndex: -1,
        xyIndex: -1,
        zyIndex: -1,
        zyfxIndex: -1,
        bjIndex: -1,
        xyList: [],
        zyList: [],
        zyfxList: [],
        bjList: [],
        xqs: [{
          name: '下沙校区',
          xqh_id: 1
        }],
        now: nowSchoolYearAndTerm()
      };
    },
    computed: {
      /**
       * 计算学年列表
       */
      xnms() {
        let xnms = []
        let xnm = parseInt(this.now.xnm);
        for (let i = xnm; i >= xnm - 5; i--) {
          xnms.push({
            xnm: '' + i,
            xnmmc: i + '-' + (i + 1)
          })
        }
        this.yearIndex = 0
        return xnms
      },
      /**
       * 计算学期列表
       */
      terms() {
        let xn = this.xnms[this.yearIndex]
        if (xn.xnm == this.now.xnm && this.now.xqmmc == 1) {
          this.termIndex = 0
          return [{
            xqm: 3,
            xqmmc: 1,
            xqname: '第一学期'
          }]
        }
        if (xn.xnm == this.now.xnm) {
          this.termIndex = 1
        } else {
          this.termIndex = 0
        }
        return [{
            xqm: 3,
            xqmmc: 1,
            xqname: '第一学期'
          },
          {
            xqm: 12,
            xqmmc: 2,
            xqname: '第二学期'
          }
        ]
      },
      /**
       * 年级列表
       */
      njs() {
        let njs = []
        let xnm = parseInt(this.now.xnm);
        for (let i = xnm; i >= xnm - 5; i--) {
          njs.push(i)
        }
        this.njIndex = 0
        return njs
      }
    },
    mounted() {
      uni.showLoading({
        title: '拼命加载中...'
      })
      getJgList().then(data => {
        uni.hideLoading()
        this.xyList = data
        if (data == null || data.length <= 0) {
          this.xyIndex = -1
        } else {
          this.xyIndex = 0
        }
        this.loadzyList()

      }).catch(err => {
        console.log(err)
        uni.hideLoading()
      })
    },
    methods: {
      bindYearChange(e) {
        this.yearIndex = e.detail.value
      },
      bindTermChange(e) {
        this.termIndex = e.detail.value
      },
      bindXQChange(e) {
        this.xqIndex = e.detail.value
      },
      bindnjChange(e) {
        this.njIndex = e.detail.value
        this.loadzyList()
      },
      bindxyChange(e) {
        this.xyIndex = e.detail.value
        this.loadzyList()
      },
      bindzyChange(e) {
        this.zyIndex = e.detail.value
        this.loadzyfxList()
      },
      bindzyfxChange(e) {
        this.zyfxIndex = e.detail.value
        this.loadbjList()
      },
      bindbjChange(e) {
        this.bjIndex = e.detail.value
      },
      loadzyList() {
        uni.showLoading({
          title: '拼命加载中...'
        })
        getZyList({
          njdm_id: this.njIndex == -1 ? '' : this.njs[this.njIndex],
          jg_id: this.xyIndex == -1 ? '' : this.xyList[this.xyIndex].jg_id
        }).then(data => {
          uni.hideLoading()
          this.zyList = data
          if (data == null || data.length <= 0) {
            this.zyIndex = -1
          } else {
            this.zyIndex = 0
          }
          this.loadzyfxList()
        }).catch(err => {
          console.log(err)
          uni.hideLoading()
        })
      },
      loadzyfxList() {
        uni.showLoading({
          title: '拼命加载中...'
        })
        getZyfxList({
          njdm_id: this.njIndex == -1 ? '' : this.njs[this.njIndex],
          jg_id: this.xyIndex == -1 ? '' : this.xyList[this.xyIndex].jg_id,
          zyh_id: this.zyIndex == -1 ? '' : this.zyList[this.zyIndex].zyh_id
        }).then(data => {
          uni.hideLoading()
          this.zyfxList = data
          if (data == null || data.length <= 0) {
            this.zyfxIndex = -1
          } else {
            this.zyfxIndex = 0
          }
          this.loadbjList()
        }).catch(err => {
          console.log(err)
          uni.hideLoading()
          this.loadbjList()
        })
      },
      loadbjList() {
        uni.showLoading({
          title: '拼命加载中...'
        })
        getBjList({
          njdm_id: this.njIndex == -1 ? '' : this.njs[this.njIndex],
          jg_id: this.xyIndex == -1 ? '' : this.xyList[this.xyIndex].jg_id,
          zyh_id: this.zyIndex == -1 ? '' : this.zyList[this.zyIndex].zyh_id,
          zyfx_id: this.zyfxIndex == -1 ? '' : this.zyfxList[this.zyfxIndex].zyfxdm
        }).then(data => {
          uni.hideLoading()
          this.bjList = data
          if (data == null || data.length <= 0) {
            this.bjIndex = -1
          } else {
            this.bjIndex = 0
          }
        }).catch(err => {
          console.log(err)
          uni.hideLoading()
        })
      },
      submit() {
        uni.navigateTo({
          url: 'recommend-detail?query=' + encodeURIComponent(JSON.stringify({
            xnm: this.xnms[this.yearIndex].xnm,
            xnmc: this.xnms[this.yearIndex].xnmmc,
            xqm: this.terms[this.termIndex].xqm,
            xqmmc: this.terms[this.termIndex].xqmmc,
            xqh_id: this.xqs[this.xqIndex].xqh_id,
            njdm_id: this.njs[this.njIndex],
            zyh_id: this.zyList[this.zyIndex].zyh_id,
            bh_id: this.bjList[this.bjIndex].bh_id
          })),
        })
        console.log({
          xnm: this.xnms[this.yearIndex].xnm,
          xnmmc: this.xnms[this.yearIndex].xnmmc,
          xqm: this.terms[this.termIndex].xqm,
          xqmmc: this.terms[this.termIndex].xqmmc,
          xqh_id: this.xqs[this.xqIndex].xqh_id,
          njdm_id: this.njs[this.njIndex],
          zyh_id: this.zyList[this.zyIndex].zyh_id,
          bh_id: this.bjList[this.bjIndex].bh_id
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .container {
    .selector-container {
      border: 1px solid #31c27c;
      border-radius: 12rpx;
      margin: 10rpx;

      .selector-item {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        height: 80rpx;
        line-height: 80rpx;
        text-align: center;
        padding: 0 20rpx;
        letter-spacing: 5rpx;

        .picker {
          display: flex;
          flex-direction: row;
          align-items: center;
          justify-content: flex-end;
          height: 80rpx;
          line-height: 80rpx;
          text-align: center;
          padding: 0 20rpx;
          letter-spacing: 5rpx;

          text {
            max-width: 400rpx;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
            word-break: break-all;
          }

          .down {
            margin-left: 20rpx;
            width: 40rpx;
            height: 40rpx;
          }
        }
      }
    }

    #btn {
      margin-top: 40rpx;
      width: 50%;
      height: 90rpx;
      line-height: 90rpx;
      background-color: #31c27c;
      font-size: 36rpx;
    }
  }
</style>
