package club.zstuca.myzstu.spyder.edu.entity;

import lombok.Data;

@Data
public class Course {
    private String day;    //周几
    private String period; //第几节
    private String length; //上课长度 2/3
    private String name;
    private String type;  //1为普通课 2为实践课
    private String teacher;
    private String room;
    private String week;  //单周 双周 前八周 后八周 正常(1-16周)

    @Override
    public String toString() {
        StringBuffer sf = new StringBuffer();
        sf.append("{name:" + this.name + ",");
        sf.append("room:" + this.room + ",");
        sf.append("teacher:" + this.teacher + ",");
        sf.append("week:" + this.week + ",");
        sf.append("teacher:" + this.teacher + ",");
        sf.append("period:" + this.period + ",");
        sf.append("length:" + this.length + "}");
        return sf.toString();
    }

}

