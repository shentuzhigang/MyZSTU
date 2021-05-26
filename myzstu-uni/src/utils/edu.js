export function isCourseValid(course, week) {
	//实践课直接返回false
	if (course.type == "实践课") {
		return false
	}
	//双周
	if (week % 2 == 0 && course.week == '双') {
		return true
	} else if (week % 2 != 0 && course.week == '单') {
		return true
	} else {
		var weeks = course.week.split('-')
		var start = parseInt(weeks[0])
		var end = parseInt(weeks[1])
		if (week >= start && week <= end) {
			return true
		}
	}
	return false
}
/**
 * 计算当前学年和学期
 */
export function nowSchoolYearAndTerm (){
  let date=new Date;	//当前时间
  let year=date.getFullYear();	//现在年份
  let month=date.getMonth()+1;    //getMonth()获取当前月份(0-11,0代表1月)
  return {
    xnm : ''+(year - 1) ,
    xnmmc: (year - 1) + '-' + year,
    xqm: '' + (month<9? 12:3),
    xqmmc: '' + (month<9? 2:1)
  }
}