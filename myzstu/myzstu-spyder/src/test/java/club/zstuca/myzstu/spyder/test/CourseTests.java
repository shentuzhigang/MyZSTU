package club.zstuca.myzstu.spyder.test;


import club.zstuca.myzstu.spyder.edu.EduSpyder;
import club.zstuca.myzstu.spyder.edu.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 19:33
 */
@SpringBootTest
public class CourseTests{
    @Autowired
    EduSpyder eduSpyder;

    @Test
    public void Coursetest1() throws Exception {
        List<Course> scoreList = eduSpyder.getCourses("2018329621200","stzg1600337300");
        for(Course score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }

    @Test
    public void Coursetest2() throws Exception {
        List<Course> scoreList = eduSpyder.getCourses("2018329621200","stzg1600337300");
        for(Course score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }
}
