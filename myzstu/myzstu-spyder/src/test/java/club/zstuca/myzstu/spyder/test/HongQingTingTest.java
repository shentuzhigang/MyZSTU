package club.zstuca.myzstu.spyder.test;

import club.zstuca.myzstu.spyder.hongqingting.entity.RunPoint;
import club.zstuca.myzstu.spyder.hongqingting.entity.RunWay;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 16:57
 */
public class HongQingTingTest {
    public static void main(String[] args) {
        RunWay runPoints = new RunWay();
        runPoints.add(new RunPoint(null, null, null, null,null, null, null));
        runPoints.add(new RunPoint(null, null, null, null,null, null, null));
        System.out.println(runPoints);
        RunWay randomRoute = RunWay.getRandomRoute(1000.5, null, 3.0);
        System.out.println(randomRoute);
        //System.out.println(new HongQingTingSpyder().uploadRunData(new RunData()));
    }
}
