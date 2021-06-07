package club.zstuca.myzstu.spyder.edu;


import club.zstuca.myzstu.spyder.edu.entity.Grade;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 14:56
 */
@Component
public class GradeParser {
    public List<Grade> parse(String gradeResponse) throws UnsupportedEncodingException {
        List<Grade> res = new LinkedList<Grade>();
        if (gradeResponse == null || gradeResponse.equals("") || !JSON.isValid(gradeResponse)) {
            return res;
        }
        JSONObject jsonObject = new JSONObject(gradeResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); ++i) {
            Grade grade = new Grade();
            JSONObject item = (JSONObject) jsonArray.get(i);
            String jd = "0";
            try {
                jd = item.getString("jd");
            } catch (Exception e) {
                e.printStackTrace();
            }
            grade.setYear(item.getString("xnmmc"));
            grade.setTerm(item.getString("xqm"));
            grade.setCourseName(item.getString("kcmc"));
            grade.setCredit(item.getString("xf"));
            String gr = "";
            // 有补考成绩 则算补考成绩
            gr = new String(item.getString("ksxz").getBytes("UTF-8")).replace("?", "");
            if (!gr.equals("")) {
                grade.setGrade(item.getString("cj"));
            } else {
                grade.setGrade(item.getString("cj"));
            }
            grade.setGpa(item.getString("jd"));
            res.add(grade);
        }
        return res;
    }
}
