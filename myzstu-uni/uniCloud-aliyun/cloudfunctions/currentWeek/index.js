'use strict';
exports.main = async (event, context) => {
	//event为客户端上传的参数
	console.log('event : ', event)
  // 学期第一周星期一
	var year = 2021
  var month = 3
  var day = 1
	var fd = new Date(year, month-1, day, 0, 0, 0)
	var now = new Date()
	var diff = now - fd
	var diffday = Math.floor(diff / (24 * 60 * 60 * 1000))
	var week = Math.floor(diffday / 7) + 1
	if (week > 18) week = 18
	//返回数据给客户端
	return {code:0,data:week}
};
