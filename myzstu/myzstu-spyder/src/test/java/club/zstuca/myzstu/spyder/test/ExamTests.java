package club.zstuca.myzstu.spyder.test;

import club.zstuca.myzstu.spyder.edu.EduSpyder;
import club.zstuca.myzstu.spyder.edu.entity.Exam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 20:36
 */
@SpringBootTest
public class ExamTests {
    @Autowired
    EduSpyder eduSpyder;
    @Test
    public void Examtest1() throws Exception {
        List<Exam> scoreList= eduSpyder.getExams("2018329621200","stzg1600337300");
        for(Exam score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }
}
