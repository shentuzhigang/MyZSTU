package club.zstuca.myzstu.controller.qq;

import club.zstuca.myzstu.common.R;
import club.zstuca.myzstu.entity.Student;
import club.zstuca.myzstu.exception.StudentException;
import club.zstuca.myzstu.provider.QQProvider;
import club.zstuca.myzstu.service.IPhoneService;
import club.zstuca.myzstu.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * QQ小程序接口
 *
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-03-19 12:44
 */
@RestController
@RequestMapping("qq/student")
public class QQStudentController {

    @Autowired
    private QQProvider qqProvider;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IPhoneService iPhoneService;

    @Value("${myzstu.first-day}")
    private String firstDay;

    @RequestMapping(value = "/openid", method = {RequestMethod.GET})
    public R<?> getOpenid(@RequestParam("code") String code) {
        String openid = qqProvider.getOpenId(code);
        if (!(openid == null || "".equals(openid))) {
            return R.success(openid);
        } else {
            return R.error();
        }
    }

    @RequestMapping(value = "/courses", method = {RequestMethod.GET})
    public R<?> getCourses(@RequestParam("openid") String openid) throws StudentException {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getCourses(stu));
    }

    @RequestMapping(value = "/recommendedcourses", method = {RequestMethod.GET})
    public R<?> getRecommendedCourses(@RequestParam("openid") String openid) throws StudentException {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getRecommendedCourses(stu));
    }

    @RequestMapping(value = "/grades", method = {RequestMethod.GET})
    public R<?> getGrades(@RequestParam("openid") String openid) throws StudentException {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getGrades(stu));
    }

    @RequestMapping(value = "/exams", method = {RequestMethod.GET})
    public R<?> getExams(@RequestParam("openid") String openid) throws StudentException {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getExams(stu));
    }

    @RequestMapping(value = "/cardinfo", method = {RequestMethod.GET})
    public R<?> getConsumption(@RequestParam("openid") String openid,
                               @RequestParam("start") String start,
                               @RequestParam("end") String end) throws Exception {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getCardInfo(stu, start, end));
    }

    @RequestMapping(value = "/borrows", method = {RequestMethod.GET})
    public R<?> getBorrowList(@RequestParam("openid") String openid) throws StudentException {
        Student stu = iStudentService.findStudentById(openid);
        return R.success(iStudentService.getBookRecord(stu));
    }

    @RequestMapping(value = "/book", method = {RequestMethod.GET})
    public R<?> getBookInfo(@RequestParam("key") String key) {
        return R.success(iStudentService.getBooks(key));
    }

    /**
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/firstday", method = {RequestMethod.GET})
    public R<?> getFirstDay() throws IOException {
        return R.success(firstDay);
    }

    /**
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/phones", method = {RequestMethod.GET})
    public R<?> getPhones() throws IOException {
        return R.success(iPhoneService.getPhones());
    }

    /**
     * @param openid
     * @param sid
     * @param edupw
     * @param ecardpw
     * @param libpw
     * @param ssopw
     * @return
     */
    @RequestMapping(value = "/student", method = {RequestMethod.POST})
    public R<?> addStudent(@RequestParam("openid") String openid,
                           @RequestParam("sid") String sid,
                           @RequestParam("edupw") String edupw,
                           @RequestParam("ecardpw") String ecardpw,
                           @RequestParam("libppw") String libpw,
                           @RequestParam("ssopw") String ssopw) {
        Student stu = new Student();
        stu.setOpenid(openid);
        stu.setId(sid);
        stu.setEcardPw(ecardpw);
        stu.setEduPw(edupw);
        stu.setLibPw(libpw);
        stu.setSsoPw(ssopw);
        this.iStudentService.saveStudent(stu);
        return R.success();
    }
}
