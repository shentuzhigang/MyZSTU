package club.zstuca.myzstu.spyder.edu.parser;


import club.zstuca.myzstu.spyder.Constants;
import club.zstuca.myzstu.spyder.edu.entity.Course;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 19:19
 */
@Component
public class CourseParser {
    public List<Course> parse(String courseResponse) {
        List<Course> res = new LinkedList<>();
        if (courseResponse == null || courseResponse.equals("") || !JSON.isValid(courseResponse)) {
            return res;
        }
        JSONObject jsonObject = new JSONObject(courseResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("kbList");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject item = (JSONObject) jsonArray.get(i);
            res.add(makeCourse(item));
        }
        jsonArray = jsonObject.getJSONArray("sjkList");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject item = (JSONObject) jsonArray.get(i);
            Course course = new Course();
            course.setName(item.getString("sjkcgs"));
            course.setType("实践课");
            course.setTeacher(item.getString("sjkcgs"));
            res.add(course);
        }

        return res;
    }

    private Course makeCourse(JSONObject item) {
        Course course = new Course();
        Map<String, String> map = Constants.weekMap;

        course.setDay(map.get(item.getString("xqjmc").substring(2)));
        course.setPeriod(item.getString("jc"));
        String[] str = item.getString("jcor").split("-");
        course.setLength("" + (Integer.parseInt(str[1]) - Integer.parseInt(str[0]) + 1));
        course.setName(item.getString("kcmc"));
        course.setType("一般课");
        if (item.has("xm")) {
            course.setTeacher(item.getString("xm"));
        } else {
            course.setTeacher("未安排");
        }
        course.setRoom(item.getString("cdmc"));
        course.setWeek(item.getString("zcd"));
        return course;
    }
}
