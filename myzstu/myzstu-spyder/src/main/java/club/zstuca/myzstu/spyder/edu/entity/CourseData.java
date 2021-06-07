package club.zstuca.myzstu.spyder.edu.entity;

import lombok.Data;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-06-12 16:20
 */
@Data
public class CourseData {
    private String xkid;
    private String kcid;
    private String skrs;
    private String bkrs;
    private String teacher;

    @Override
    public String toString() {
        return "CourseData{" +
                "xkid='" + xkid + '\'' +
                ", kcid='" + kcid + '\'' +
                ", skrs='" + skrs + '\'' +
                ", bkrs='" + bkrs + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
