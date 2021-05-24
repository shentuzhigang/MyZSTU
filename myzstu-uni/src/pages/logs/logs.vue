<template>
	<view>
		
	</view>
</template>

<script>
  const util = require('../../utils/util.js')
	export default {
		data() {
			return {
				logs: []
			}
		},
		methods: {
			
		},
    onLoad: function () {
      this.logs = (wx.getStorageSync('logs') || []).map(log => {
          return util.formatTime(new Date(log))
        })
    }
	}
</script>

<style>

</style>
