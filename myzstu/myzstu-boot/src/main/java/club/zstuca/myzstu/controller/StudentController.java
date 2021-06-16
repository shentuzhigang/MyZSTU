package club.zstuca.myzstu.controller;


import club.zstuca.myzstu.common.web.R;
import club.zstuca.myzstu.entity.Student;
import club.zstuca.myzstu.exception.StudentException;
import club.zstuca.myzstu.service.IPhoneService;
import club.zstuca.myzstu.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin("*")
@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IPhoneService iPhoneService;

    @Value("${myzstu.first-day}")
    private String firstDay;

    @RequestMapping(value = "/courses", method = {RequestMethod.POST})
    public R<?> getCourses(@RequestParam("sid") String sid,
                           @RequestParam("edupw") String edupw) throws StudentException {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(edupw);
        return R.success(iStudentService.getCourses(stu));
    }

    @RequestMapping(value = "/grades", method = {RequestMethod.POST})
    public R<?> getGrades(@RequestParam("sid") String sid,
                          @RequestParam("edupw") String edupw) throws StudentException {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(edupw);
        return R.success(iStudentService.getGrades(stu));
    }

    @RequestMapping(value = "/exams", method = {RequestMethod.POST})
    public R<?> getExams(@RequestParam("sid") String sid,
                         @RequestParam("edupw") String edupw) throws StudentException {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(edupw);
        return R.success(iStudentService.getExams(stu));
    }

    @RequestMapping(value = "/balance", method = {RequestMethod.POST})
    public R<?> getYktBalance(@RequestParam("sid") String sid,
                              @RequestParam("ecardpw") String ecardpw) throws Exception {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(ecardpw);
        return R.success(iStudentService.getBalance(stu));
    }

    @RequestMapping(value = "/consumption", method = {RequestMethod.POST})
    public R<?> getConsumption(@RequestParam("sid") String sid,
                               @RequestParam("ecardpw") String ecardpw,
                               @RequestParam("start") String start,
                               @RequestParam("end") String end) throws Exception {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(ecardpw);
        return R.success(iStudentService.getConsumptions(stu, start, end));
    }

    @RequestMapping(value = "/borrows", method = {RequestMethod.POST})
    public R<?> getBorrowList(@RequestParam("sid") String sid,
                              @RequestParam("libpw") String libpw) throws Exception {
        Student stu = new Student();
        stu.setId(sid);
        stu.setLibPw(libpw);
        return R.success(iStudentService.getBookRecord(stu));
    }

    @RequestMapping(value = "/book", method = {RequestMethod.POST})
    public R<?> getBookInfo(@RequestParam("book") String book) {
        return R.success(iStudentService.getBooks(book));
    }

    @RequestMapping(value = "/firstday", method = {RequestMethod.GET})
    public R<?> getFirstDay() throws IOException {
        return R.success(firstDay);
    }

    @RequestMapping(value = "/phones", method = {RequestMethod.GET})
    public R<?> getPhones() throws IOException {
        return R.success(iPhoneService.getPhones());
    }
}
