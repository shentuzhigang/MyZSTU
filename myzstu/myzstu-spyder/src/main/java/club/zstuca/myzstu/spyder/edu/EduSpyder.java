package club.zstuca.myzstu.spyder.edu;

import club.zstuca.myzstu.spyder.edu.entity.Course;
import club.zstuca.myzstu.spyder.edu.entity.Exam;
import club.zstuca.myzstu.spyder.edu.entity.Grade;
import club.zstuca.myzstu.utils.http.HttpContext;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 13:51
 */
@Component
public class EduSpyder {
    //课程URL
    private final String CHECK_COURSE_URL = "http://jwglxt.zstu.edu.cn/jwglxt/kbcx/xskbcx_cxXsKb.html?doType=query&gnmkdm=N2151";
    //推荐课程URL
    private final String CHECK_RECOMMENDED_COURSE_URL = "http://jwglxt.zstu.edu.cn/jwglxt/kbdy/bjkbdy_cxBjKb.html?gnmkdm=N214505&su=";
    //成绩URL
    private final String CHECK_GRADE_URL = "http://jwglxt.zstu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdm=N305005";
    //考试URL
    private final String CHECK_EXAM_URL = "http://jwglxt.zstu.edu.cn/jwglxt/kwgl/kscx_cxXsksxxIndex.html?doType=query&gnmkdm=N358105";
    @Autowired
    private EduSSOLoginer eduLoginer;
    @Autowired
    private GradeParser gradeParser;
    @Autowired
    private CourseParser courseParser;
    @Autowired
    private ExamParser examParser;

    //获取课程信息
    public List<Course> getCourses(String username, String password) {
        List<Course> data = null;
        try {
            if (eduLoginer.login(username, password)) {
                data = courseParser.parse(crawlCourses());
                eduLoginer.logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //抓取课程信息页面
    private String crawlCourses() throws Exception {
        Map<String, String> courseParams = new HashMap<>();
        courseParams.put("xnm", "2020");
        courseParams.put("xqm", "3");
        String courseResponse = HttpUtil.doPost(CHECK_COURSE_URL, courseParams).getContent();
        return courseResponse;
    }

    //获取推荐课程信息
    public List<Course> getRecommendedCourses(String username, String password) {
        List<Course> data = null;
        try {
            HttpContext httpContext = new HttpContext();
            if (eduLoginer.login(username,password, httpContext)) {
                data = courseParser.parse(crawlRecommendedCourses(httpContext));
                eduLoginer.logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //抓取推荐课程信息页面
    private String crawlRecommendedCourses(HttpContext httpContext) throws Exception {
        Map<String, String> courseParams = new HashMap<>();
        courseParams.put("xnm", "2020");
        courseParams.put("xqm", "3");
        courseParams.put("xnmc", "2020-2021");
        courseParams.put("xxqmmc", "2");
        courseParams.put("xqh_id", "1");
        courseParams.put("njdm_id", "2018");
        courseParams.put("zyh_id", "3620");
        courseParams.put("bh_id", "E186203");
        courseParams.put("tjkbzdm", "1");
        courseParams.put("tjkbzxsdm", "0");
        courseParams.put("zymc", "计算机科学与技术");
        courseParams.put("zxszjjs", "false");
        String courseResponse = httpContext.doPost(CHECK_RECOMMENDED_COURSE_URL,
                courseParams).getContent();
        return courseResponse;

    }

    //获取成绩信息
    public List<Grade> getGrades(String username, String password){
        List<Grade> data = null;
        try {
            if (eduLoginer.login(username, password)) {
                data = gradeParser.parse(crawlGrades());
                eduLoginer.logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //获取成绩信息页面
    private String crawlGrades() throws Exception {
        Map<String, String> scoreParams = new HashMap<>();
        scoreParams.put("xnm", "");
        scoreParams.put("xqm", "");
        scoreParams.put("_search", "false");
        scoreParams.put("nd", "" + new Date().getTime());
        scoreParams.put("queryModel.showCount", "100");
        scoreParams.put("queryModel.currentPage", "1");
        scoreParams.put("queryModel.sortName", "");
        scoreParams.put("queryModel.sortOrder", "asc");
        scoreParams.put("time", "1");
        String scoreResponse = HttpUtil.doPost(CHECK_GRADE_URL, scoreParams).getContent();
        return scoreResponse;
    }

    //获取成绩信息页面
    public String crawlGrades(HttpContext httpContext) throws Exception {
        Map<String, String> scoreParams = new HashMap<>();
        scoreParams.put("xnm", "");
        scoreParams.put("xqm", "");
        scoreParams.put("_search", "false");
        scoreParams.put("nd", "" + System.currentTimeMillis());
        scoreParams.put("queryModel.showCount", "100");
        scoreParams.put("queryModel.currentPage", "1");
        scoreParams.put("queryModel.sortName", "");
        scoreParams.put("queryModel.sortOrder", "asc");
        scoreParams.put("time", "1");
        String scoreResponse = httpContext.doPost(CHECK_GRADE_URL, scoreParams).getContent();
        return scoreResponse;
    }

    //获取考试信息
    public List<Exam> getExams(String username, String password) {
        List<Exam> data = null;
        try {
            if (eduLoginer.login(username,password)) {
                data = examParser.parse(crawlExams());
                eduLoginer.logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //抓取考试信息页面
    private String crawlExams() throws Exception {
        Map<String, String> examParams = new HashMap<>();
        examParams.put("xnm", "2020");
        examParams.put("xqm", "3");
        examParams.put("_search", "false");
        examParams.put("ksmcdmb_id", "");
        examParams.put("kch", "");
        examParams.put("kc", "");
        examParams.put("ksrq", "");
        examParams.put("nd", "" + new Date().getTime());
        examParams.put("queryModel.showCount", "100");
        examParams.put("queryModel.currentPage", "1");
        examParams.put("queryModel.sortName", "");
        examParams.put("queryModel.sortOrder", "asc");
        examParams.put("time", "1");
        String examResponse = HttpUtil.doPost(CHECK_EXAM_URL, examParams).getContent();
        return examResponse;
    }

}
