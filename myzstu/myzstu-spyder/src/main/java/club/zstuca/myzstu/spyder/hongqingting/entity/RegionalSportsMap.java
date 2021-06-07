package club.zstuca.myzstu.spyder.hongqingting.entity;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-29 21:57
 */
public abstract class RegionalSportsMap {
    abstract MeterPoint getPoint(Double dis);

    abstract MeterPoint getPointWithOffset(Double dis, Double normalOffset);
}
