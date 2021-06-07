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
        return "Grade{" +
                "year='" + year + '\'' +
                ", term='" + term + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit='" + credit + '\'' +
                ", gpa='" + gpa + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
