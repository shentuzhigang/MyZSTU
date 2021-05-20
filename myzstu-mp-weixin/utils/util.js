const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}
//判断某门课第week周上不上
function isCourseValid(course,week){
  //实践课直接返回false
  if(course.type=="实践课"){
    return false
  }
  //双周
  if (week % 2 == 0 && course.week == '双') {
    return true
  }
  else if (week % 2 != 0 && course.week == '单') {
    return true
  }
  else {
    var weeks = course.week.split('-')
    var start = parseInt(weeks[0])
    var end = parseInt(weeks[1])
    if (week >= start && week <= end) {
      return true
    }
  }
  return false 
}
function calcuateWeek(first) {
  var temp = first.split('-')
  var fd = new Date(temp[0], temp[1] - 1, temp[2], 0, 0, 0)
  var now = new Date()
  var diff = now - fd
  var diffday = Math.floor(diff / (24 * 60 * 60 * 1000))
  var week = Math.floor(diffday / 7) + 1
  if(week > 17) week = 17
  return week;
}
module.exports = {
  formatTime: formatTime,
  calcuateWeek: calcuateWeek,
  isCourseValid: isCourseValid

}
