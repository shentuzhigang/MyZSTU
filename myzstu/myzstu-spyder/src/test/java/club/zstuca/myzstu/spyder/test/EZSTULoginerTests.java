package club.zstuca.myzstu.spyder.test;

import club.zstuca.myzstu.spyder.ezstu.AutoBackToDormitorySignServiceImpl;
import club.zstuca.myzstu.spyder.ezstu.query.HealthDeclarationQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 11:17
 */
@SpringBootTest
public class EZSTULoginerTests{
    @Test
    public static void a()throws Exception{
        AutoBackToDormitorySignServiceImpl autoBackToDormitorySignService = new AutoBackToDormitorySignServiceImpl();
        System.out.println(autoBackToDormitorySignService.autoService("2018329621200", "081639"));
    }
    @Test
    public static void h()throws Exception {
        HealthDeclarationQuery ezstuLoginer = new HealthDeclarationQuery();
        //ezstuLoginer.login("2018329621200","081639");
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        String curDate = s.format(c.getTime());
        ezstuLoginer.getUndeclaredStudentList("[4fe1][606f][5b66][9662]","2018","计算机科学与技术18(3)",curDate);
    }
}
