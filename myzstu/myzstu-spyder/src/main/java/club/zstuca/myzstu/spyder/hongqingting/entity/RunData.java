package club.zstuca.myzstu.spyder.hongqingting.entity;


import club.zstuca.myzstu.utils.zip.GZIPUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 15:22
 */
@Data
public class RunData {
    /**
     * 开始时间
     */
    private Long beginTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 未知id
     */
    private String uid;
    /**
     * 学校代码
     */
    private String schoolNo;
    /**
     * 距离
     */
    private String distance;
    /**
     * 速度
     */
    private String speed;
    /**
     * 学生代码
     */
    private String studentNo;
    /**
     * 类型
     */
    private String attType;
    /**
     *
     */
    private String eventNo;
    /**
     * 定位
     */

    private String location;
    /**
     * 未知
     */
    private Float useTime;

    public static RunData parse(String str) {
        return JSONObject.parseObject(str, RunData.class);
    }

    public static RunData parse(byte[] arr) {
        return parse(GZIPUtil.uncompressToString(arr));
    }

    @Override
    public String toString() {
        return "{" +
                "'begintime':'" + this.getBeginTime() + "'," +
                "'endtime':'" + this.getEndTime() + "'," +
                "'uid':'" + this.getUid() + "'," +
                "'schoolno':'" + this.getSchoolNo() + "'," +
                "'distance':'" + this.getDistance() + "'," +
                "'speed':'" + this.getSpeed() + "'," +
                "'studentno':'" + this.getStudentNo() + "'," +
                "'atttype':'" + this.getAttType() + "'," +
                "'eventno':'" + this.getEventNo() + "'," +
                "'location':'" + this.getLocation() + "'," +
                "'usetime':'" + this.getUseTime() + "'}";
    }

    public String getLocation(Long now) {
        String[] list = location.split("@");
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            String[] strings = s.split(";");
            strings[1] = String.valueOf(Long.parseLong(strings[1]) + now);
            s = String.join(";", strings);
            newList.add(s);
        }
        return String.join("@", newList);
    }
}
