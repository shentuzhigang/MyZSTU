package club.zstuca.myzstu.service.impl;

import club.zstuca.myzstu.dto.edu.GradeDTO;
import club.zstuca.myzstu.entity.Student;
import club.zstuca.myzstu.exception.StudentException;
import club.zstuca.myzstu.mapper.CourseMapper;
import club.zstuca.myzstu.mapper.StudentMapper;
import club.zstuca.myzstu.service.IStudentService;
import club.zstuca.myzstu.spyder.ecard.EcardSpyder;
import club.zstuca.myzstu.spyder.edu.EduSpyder;
import club.zstuca.myzstu.spyder.edu.entity.Course;
import club.zstuca.myzstu.spyder.edu.entity.Exam;
import club.zstuca.myzstu.spyder.edu.entity.Grade;
import club.zstuca.myzstu.spyder.lib.LibSpyder;
import club.zstuca.myzstu.spyder.lib.LibSpyderB;
import club.zstuca.myzstu.spyder.lib.entity.Book;
import club.zstuca.myzstu.spyder.lib.entity.BookRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-02-10 13:34
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private EduSpyder eduSpyder;

    @Autowired
    private EcardSpyder ecardSpyder;

    @Autowired
    private LibSpyder libSpyder;

    @Autowired
    private LibSpyderB libSpyderB;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    /**
     * @param openId
     * @return
     */
    @Override
    public Student findStudentById(String openId) throws StudentException {
        Student stu = studentMapper.findStudentById(openId);
        if (stu == null || "".equals(stu.getId())) {
            throw new StudentException("201|请补全个人信息");
        }
        return stu;
    }

    /**
     * 添加一个学生，如果不存在添加，如果已存在，则更新
     *
     * @param stu
     * @return
     */
    @Override
    public boolean saveStudent(Student stu) {
        return save(stu);
    }

    @Override
    public List<Course> getCourses(Student stu) throws StudentException {
        List<Course> courses = eduSpyder.getCourses(stu.getId(),stu.getEduPw());
        ArrayList<Course> res = new ArrayList<>();
        for (Course itema : courses) {
            boolean isAdd = true;
            for (int j = 0; j < res.size(); j++) {
                Course itemb = res.get(j);
                if (itema.getType().equals("实践课")
                        && itema.getType().equals(itemb.getType())
                        && itema.getName().equals(itemb.getName())) {
                    isAdd = false;
                    break;
                } else if (itema.getType().equals("一般课")
                        && itema.getType().equals(itemb.getType())
                        && itema.getName().equals(itemb.getName())
                        && itema.getDay().equals(itemb.getDay())
                        && itema.getWeek().equals(itemb.getWeek())) {
                    res.remove(j);
                    itema.setLength(Integer.parseInt(itema.getLength())
                            + Integer.parseInt(itemb.getLength()) + "");
                    itema.setPeriod(
                            Integer.parseInt(itema.getPeriod()) >= Integer.parseInt(itemb.getPeriod()) ?
                                    itemb.getPeriod() : itema.getPeriod());
                }
            }
            if (isAdd) {
                res.add(itema);
            }
        }
        return res;
    }

    @Override
    public List<Course> getRecommendedCourses(Student stu) throws StudentException {
        List<Course> courses = eduSpyder.getRecommendedCourses(stu.getId(),stu.getEduPw());
        return courses;
    }

    @Override
    public GradeDTO getGrades(Student stu) throws StudentException {
        GradeDTO dto = new GradeDTO();
        List<Grade> data = eduSpyder.getGrades(stu.getId(),stu.getEduPw());
        data.removeIf(x -> x.getGrade().equals("放弃"));
        Set<String> years = new LinkedHashSet<>();
        String time = "";
        for (Grade datum : data) {
            time = datum.getYear() + "-" + datum.getTerm();
            years.add(time);
        }
        dto.setData(data);
        dto.setYears(years);
        return dto;
    }

    @Override
    public List<Exam> getExams(Student stu) throws StudentException {
        List<Exam> exams = eduSpyder.getExams(stu.getId(),stu.getEduPw());
//        for (Exam exam : exams) {
//            exam.setCourseDatas(courseMapper.findById(exam.getCourseid(), exam.getCourseName()));
//        }
        return exams;
    }

    @Override
    public Map<String, Object> getCardInfo(Student stu, String start, String end) throws Exception {
        return ecardSpyder.getCardInfo(stu.getEcardPw(),stu.getEduPw(), start, end);
    }

    @Override
    public Object getBalance(Student stu) throws Exception {
        return ecardSpyder.getCardInfo(stu.getEcardPw(),stu.getEduPw(), "2020-05-05", "2020-05-12");
    }

    @Override
    public Object getConsumptions(Student stu, String start, String end) {
        return null;
    }

    @Override
    public List<Book> getBooks(String key) {
        return libSpyder.getBookInfo(key);
    }

    @Override
    public Map<String, List<BookRecord>> getBookRecord(Student stu) {
        Map<String, List<BookRecord>> res = new HashMap<>();
        try {
            res = libSpyderB.getBorrowList(stu.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
