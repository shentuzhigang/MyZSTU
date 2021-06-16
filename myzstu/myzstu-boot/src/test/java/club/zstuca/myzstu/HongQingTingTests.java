package club.zstuca.myzstu;

import club.zstuca.myzstu.boot.service.IHongQingTingService;
import club.zstuca.myzstu.spyder.hongqingting.entity.RunPoint;
import club.zstuca.myzstu.spyder.hongqingting.entity.RunWay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 16:57
 */
@SpringBootTest
public class HongQingTingTests {

    @Autowired
    private IHongQingTingService iHongQingTingService;

    @Test
    public void test1() {
        RunWay runPoints = new RunWay();
        runPoints.add(new RunPoint(null, null, null, null,null, null, null));
        runPoints.add(new RunPoint(null, null, null, null,null, null, null));
        System.out.println(runPoints);
        RunWay randomRoute = RunWay.getRandomRoute(1000.5, null, 3.0);
        System.out.println(randomRoute);
        //System.out.println(new HongQingTingSpyder().uploadRunData(new RunData()));
    }
}
