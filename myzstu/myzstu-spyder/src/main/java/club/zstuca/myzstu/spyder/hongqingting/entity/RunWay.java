package club.zstuca.myzstu.spyder.hongqingting.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-29 20:47
 */
@Data
public class RunWay extends ArrayList<RunPoint> {
    /**
     * 预计跑步路程(米)
     */
    private Double distance;
    /**
     * 开始跑步时所在操场的位置(米)
     */
    private LatLonPoint startPoint;
    /**
     * 结束跑步时所在操场的位置(米)
     */
    private LatLonPoint endPoint;
    /**
     * 开始时间
     */
    private Long beginTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 速度函数(m/s)
     */
    private Double speed;
    /**
     * 法向噪声系数(米)
     */
    private Double normalOffset;

    public static RunWay getRandomRouteWith(Long time, Double distance, LatLonPoint startPoint, Double normalOffset) {
        RunWay result = new RunWay();
        Double pos = 0D;
        PlaygroundMap m = new PlaygroundMap();
        Integer i = 0;
        Integer j = 0;
        Random random = new Random();
        result.setBeginTime(time);
        result.setNormalOffset(normalOffset);
        result.setStartPoint(startPoint);
        while (pos < distance) {
            if (i == 0) {
                LatLonPoint point = LatLonPoint.parse(m.getPointWithOffset(pos, normalOffset));
                result.setEndPoint(point);
                result.add(new RunPoint(point.latitude, point.longitude, time, null, null, 1.0, null));
                if (j == 0) {
                    j = 7 + random.nextInt(4);
                    i = random.nextInt(27) + 10;
                } else {
                    j -= 1;
                    i = 3 + random.nextInt(2);
                }
            } else {
                i -= 1;
            }
            pos += mSpeed(pos / distance);
            time += 1;
        }
        result.setDistance(pos);
        result.setEndTime(time);
        result.setSpeed(pos / (result.getEndTime() - result.getBeginTime()));
        return result;
    }

    public static RunWay getRandomRoute(Double distance, LatLonPoint startPoint) {
        return getRandomRoute(distance, startPoint, 3.0);
    }

    public static RunWay getRandomRoute(Double distance, LatLonPoint startPoint, Double normalOffset) {
        RunWay result = new RunWay();
        Double pos = 0D;
        PlaygroundMap m = new PlaygroundMap();
        Long time = System.currentTimeMillis() / 1000;
        Integer i = 0;
        Integer j = 0;
        Random random = new Random();
        result.setBeginTime(time);
        result.setNormalOffset(normalOffset);
        result.setStartPoint(startPoint);
        while (pos < distance) {
            if (i == 0) {
                LatLonPoint point = LatLonPoint.parse(m.getPointWithOffset(pos, normalOffset));
                result.setEndPoint(point);
                result.add(new RunPoint(point.latitude, point.longitude, time, null, null, 1.0, null));
                if (j == 0) {
                    j = 7 + random.nextInt(4);
                    i = random.nextInt(27) + 10;
                } else {
                    j -= 1;
                    i = 3 + random.nextInt(2);
                }
            } else {
                i -= 1;
            }
            pos += mSpeed(pos / distance);
            time += 1;
        }
        result.setDistance(pos);
        result.setEndTime(time);
        result.setSpeed(pos / (result.getEndTime() - result.getBeginTime()));
        return result;
    }

    public static Double mSpeed(Double rat) {
        if (rat < 0.4) {
            return 2.0 + Math.random();
        } else if (rat < 0.6) {
            return 1.1 + Math.random();
        }
        return 1.8 + Math.random();
    }

    @Override
    public String toString() {
        return String.join("@", this);
    }
}
