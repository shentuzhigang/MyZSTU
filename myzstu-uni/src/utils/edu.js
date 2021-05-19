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