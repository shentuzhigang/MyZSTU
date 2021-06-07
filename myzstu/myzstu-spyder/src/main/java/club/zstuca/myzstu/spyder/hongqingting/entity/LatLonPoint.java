package club.zstuca.myzstu.spyder.hongqingting.entity;

import lombok.Data;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-29 20:23
 */
@Data
public class LatLonPoint {
    public Double latitude;
    public Double longitude;

    public LatLonPoint(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public static LatLonPoint parse(MeterPoint point) {
        return new LatLonPoint((point.x + 3096025) / 102140.3687,
                (point.y + 12293160) / 102140.3687);
    }

    public static double getDistance(LatLonPoint A, LatLonPoint B) {
        // 东西经，南北纬处理，只在国内可以不处理(假设都是北半球，南半球只有澳洲具有应用意义)
        double MLonA = A.longitude;
        double MLatA = A.latitude;
        double MLonB = B.longitude;
        double MLatB = B.latitude;
        // 地球半径（千米）
        double R = 6371.004;
        double C = Math.sin(Math.toRadians(MLatA)) *
                Math.sin(Math.toRadians(MLatB)) +
                Math.cos(Math.toRadians(MLatA)) *
                        Math.cos(Math.toRadians(MLatB)) *
                        Math.cos(Math.toRadians(MLonA - MLonB));
        return (R * Math.acos(C));
    }
}
