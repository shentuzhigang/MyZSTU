package club.zstuca.myzstu.spyder.edu.parser;

import club.zstuca.myzstu.spyder.edu.entity.Exam;
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
 * @Date 2020-02-10 20:22
 */
@Component
public class ExamParser {
    public List<Exam> parse(String examResponse) throws UnsupportedEncodingException {
        List<Exam> res = new LinkedList<Exam>();
        if (examResponse == null || examResponse.equals("") || !JSON.isValid(examResponse)) {
            return res;
        }
        JSONObject jsonObject = new JSONObject(examResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); ++i) {
            Exam exam = new Exam();
            JSONObject item = (JSONObject) jsonArray.get(i);
            exam.setCourseName(item.getString("kcmc"));
            exam.setSeatNumber(item.getString("zwh"));
            exam.setExamPlace(item.getString("cdmc"));
            exam.setExamTime(item.getString("kssj"));
            exam.setCourseId(item.getString("kch"));
            res.add(exam);
        }
        return res;
    }

}
