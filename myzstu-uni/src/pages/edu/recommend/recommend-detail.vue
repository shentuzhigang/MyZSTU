<template>
	<view>
    
		<course-table v-if="isShow" :wlist="wlist" :plist="plist"></course-table>
    <view v-else class="no-exam-box">
      暂无推荐课表
    </view>
	</view>
</template>

<script>
  import CourseTable  from '@/components/course-table/course-table'
  
  import {
    getRecommendCourses
  } from '@/api/edu.js'
  
	export default {
    components:{
      CourseTable 
    },
		data() {
			return {
        isShow: false,
				wlist: [],
				plist: [],
			};
		},
    onLoad: function (option) { //option为object类型，会序列化上个页面传递的参数
    uni.showLoading({
      title: '拼命加载中...'
    })
     const params = JSON.parse(decodeURIComponent(option.query));
       getRecommendCourses(params)
       .then((data)=>{
         uni.hideLoading()
         for(let i = 0 ;i<data.sjkList.length;i++){
           let mc = data.sjkList[i].sjkcgs.split(/○|●/)
           data.sjkList[i].kcmc = mc[0]
           data.sjkList[i].jsxm = mc[1]
         }
         this.wlist = data.kbList
         this.plist = data.sjkList
         if(this.wlist.length > 0 || this.plist.length > 0){
           this.isShow = true
         }
       })
    }
	}
</script>

<style lang="scss">
 .no-exam-box {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100%;
    }
</style>
