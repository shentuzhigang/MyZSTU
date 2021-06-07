package club.zstuca.myzstu.spyder.hongqingting.entity;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-30 23:08
 */
public class MeterPoint {
    public Double x;
    public Double y;

    public MeterPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static MeterPoint parse(LatLonPoint point) {
        return new MeterPoint(point.latitude * 102140.3687 - 3096025,
                point.longitude * 102140.3687 - 12293160);
    }

    public static double getDistance(MeterPoint A, MeterPoint B) {
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }
}
