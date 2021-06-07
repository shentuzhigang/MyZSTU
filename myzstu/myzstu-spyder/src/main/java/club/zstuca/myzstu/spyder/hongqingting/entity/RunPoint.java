package club.zstuca.myzstu.spyder.hongqingting.entity;

import lombok.Data;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-29 20:31
 */
@Data
public class RunPoint implements CharSequence {
    private Double lat;
    private Double lon;
    private String str1;
    private String str2;
    private String str3;
    private Long timeStamp;
    private Double speed;
    private String source;

    public RunPoint(Double lat, Double lon, Long timeStamp, String str1, String str2, Double speed, String str3) {
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
        this.str1 = str1;
        this.str2 = str2;
        this.speed = speed;
        this.str3 = str3;
        this.source = toString();
    }

    public static RunPoint parseRPoint(String str) {
        String[] strings = str.split(";");
        if (strings.length != 7) {
            throw new RuntimeException("格式错误");
        }
        try {
            Double lat = Double.valueOf(strings[0]);
            Double lon = Double.valueOf(strings[1]);
            Long timeStamp = Long.valueOf(strings[2]);
            String str1 = strings[3];
            String str2 = strings[4];
            Double speed = Double.valueOf(strings[5]);
            String str3 = strings[6];
            return new RunPoint(lat, lon, timeStamp, str1, str2, speed, str3);
        } catch (Exception e) {
            throw new RuntimeException("解析错误");
        }
    }

    @Override
    public int length() {
        return source.length();
    }

    @Override
    public char charAt(int index) {
        return source.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return source.subSequence(start, end);
    }

    @Override
    public String toString() {
        return String.format("%.15f;%.15f;%d;%s;%s;%.1f,%s", lat, lon, timeStamp, str1, str2, speed, str3);
    }
}
