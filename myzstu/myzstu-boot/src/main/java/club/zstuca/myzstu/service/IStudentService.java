package club.zstuca.myzstu.service;

import club.zstuca.myzstu.dto.edu.GradeDTO;
import club.zstuca.myzstu.entity.Student;
import club.zstuca.myzstu.exception.StudentException;
import club.zstuca.myzstu.spyder.edu.entity.Course;
import club.zstuca.myzstu.spyder.edu.entity.Exam;
import club.zstuca.myzstu.spyder.lib.entity.Book;
import club.zstuca.myzstu.spyder.lib.entity.BookRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-16 15:04
 */
public interface IStudentService extends IService<Student> {
    Student findStudentById(String openid) throws StudentException;

    boolean saveStudent(Student stu);

    List<Course> getCourses(Student stu) throws StudentException;

    List<Course> getRecommendedCourses(Student stu) throws StudentException;

    GradeDTO getGrades(Student stu) throws StudentException;

    List<Exam> getExams(Student stu) throws StudentException;

    Map<String, Object> getCardInfo(Student stu, String start, String end) throws Exception;

    Object getBalance(Student stu) throws Exception;

    Object getConsumptions(Student stu, String start, String end);

    List<Book> getBooks(String key);

    Map<String, List<BookRecord>> getBookRecord(Student stu) throws StudentException;
}
