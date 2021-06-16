package club.zstuca.myzstu.spyder.test;


import club.zstuca.myzstu.spyder.edu.EduSpyder;
import club.zstuca.myzstu.spyder.edu.entity.Grade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 15:10
 */

@SpringBootTest
public class GradeTests {
    @Autowired
    EduSpyder eduSpyder;
    @Test
    public void Gradetest1() throws Exception {
        List<Grade> scoreList= eduSpyder.getGrades("2018329621200","stzg1600337300");
        for(Grade score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }
    @Test
    public void Gradetest2() throws Exception {
        List<Grade> scoreList= eduSpyder.getGrades("2018329621200","stzg1600337300");
        for(Grade score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }

    @Test
    public void Gradetest3(){
        new Thread(()->{
            while (true){
                try {
                    Gradetest1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (true){
            try {
                Gradetest2();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*new Thread(()->{
            while (true){
                try {
                    Gradetest2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    @Test
    public void Gradetest4() throws Exception {
        List<Grade> scoreList=eduSpyder.getGrades("2018329621200","stzg1600337300");
        for(Grade score:scoreList){
            System.out.println(score);
        }
        System.out.println(scoreList.size());

    }
}
