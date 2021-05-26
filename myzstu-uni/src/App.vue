<script>
  export default {
    data(){
      return {
        platform: uni.getSystemInfoSync().platform
      }
    },
    methods: {
      /**
       * 检查更新
       */
      onCheckForUpdate() {
        const updateManager = uni.getUpdateManager()
        updateManager.onCheckForUpdate(function(res) {
          console.log(res.hasUpdate)
        })
        updateManager.onUpdateReady(function() {
          uni.showModal({
            title: '更新提示',
            content: '新版本已经准备好，是否重启应用？',
            success(res) {
              if (res.confirm) {
                updateManager.applyUpdate()
              }
            }
          })
        })
      },
      login() {
        this.oauth().then((code) => {
        	return uniCloud.callFunction({
        		name: 'code2Session',
        		data: {code}
        	})
        }).then((res) => {
        	if (res.result.code === 0) {
            let openid = res.result.data.openid;
            uni.setStorageSync('openid', openid)
        	}
        }).catch((e) => {
        	console.error(e)
        })
      },
      oauth() {
        return new Promise((resolve, reject) => {
          // #ifdef MP-WEIXIN
        	uni.login({
        		provider: 'weixin',
            success(res) {
            	resolve(res.code)
            },
            fail(err) {
            	console.error('授权登录失败：' + JSON.stringify(err));
            	reject(new Error('微信登录失败'))
            }
          })  
          // #endif
          // #ifdef MP-QQ
          uni.login({  
            provider: 'qq',
        		success(res) {
        			resolve(res.code)
        		},
        		fail(err) {
        			console.error('授权QQ失败：' + JSON.stringify(err));
        			reject(new Error('授权QQ失败'))
        		}
        	})
          // #endif
        })
      }
    },
    onLaunch() {
      console.log('App Launch')
      this.onCheckForUpdate()
      console.log(this.platform)
      this.login()
      this.globalData.userInfo = uni.getStorageSync('UserProfile');
    },
    onShow() {
      console.log('App Show')
    },
    onHide() {
      console.log('App Hide')
    },
    globalData: {
      userInfo: null,
      server:{
        local: 'http://127.0.0.1/',
        romate: 'https://myzstu.zstuca.club',
        edu: 'https://www.shentuzhigang.top',
        sports: 'https://www.shentuzhigang.top',
        lib: '',
        ecard: '',
        ezstu: '',
        sso: '',
        zlty: '',
      },
      timeTable: {
        1: '8:55',
        2: '9:45',
        3: '10:45',
        4: '11:35',
        5: '12:25',
        6: '14:15',
        7: '15:05',
        8: '15:55',
        9: '16:45',
        10: '19:15',
        11: '20:05',
        12: '20:55'
      }
    }
  }
</script>

<style>
  /*每个页面公共css */
</style>

<style lang="scss">
  /* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
  @import "uview-ui/index.scss";
</style>
