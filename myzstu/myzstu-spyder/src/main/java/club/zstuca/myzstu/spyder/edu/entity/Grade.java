package club.zstuca.myzstu.spyder.edu.entity;

import lombok.Data;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 13:09
 */
@Data
public class Grade {
    private String year;
    private String term;
    private String courseName;
    private String credit;
    private String gpa;
    private String grade;

    @Override
    public String toString() {
        StringBuffer sf = new StringBuffer();
        sf.append("{");
        sf.append("\"学年\":" + this.year + ",");
        sf.append("\"学期\":" + this.term + ",");
        sf.append("\"课程名称\":" + this.courseName + ",");
        sf.append("\"学分\":" + this.credit + ",");
        sf.append("\"绩点\":" + this.gpa + ",");
        sf.append("\"成绩\":" + this.grade);
        sf.append("}");
        return sf.toString();
    }
}
