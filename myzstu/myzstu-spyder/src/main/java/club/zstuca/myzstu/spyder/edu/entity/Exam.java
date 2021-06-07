package club.zstuca.myzstu.spyder.edu.entity;

import lombok.Data;

import java.util.List;

@Data
public class Exam {

    private String courseName;
    private String courseId;
    private String examTime;
    private String examPlace;
    private String seatNumber;
    private List<CourseData> courseDatas;

    @Override
    public String toString() {
        return "Exam{" +
                "courseName='" + courseName + '\'' +
                ", courseid='" + courseId + '\'' +
                ", examTime='" + examTime + '\'' +
                ", examPlace='" + examPlace + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", courseDatas=" + courseDatas +
                '}';
    }
}

