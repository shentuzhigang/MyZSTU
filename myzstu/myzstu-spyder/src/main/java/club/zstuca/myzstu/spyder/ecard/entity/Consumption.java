package club.zstuca.myzstu.spyder.ecard.entity;

import lombok.Data;

/**
 * @author ShenTuZhiGang
 */
@Data
public class Consumption {
    private String time;
    private String content;
    private String money;
    private String workstation;
    private String balance;
    private String place;

    @Override
    public String toString() {
        return "{" +
                "time:" +
                this.time +
                "," +
                "content:" +
                this.content +
                "," +
                "money:" +
                this.money +
                "," +
                "workstation:" +
                this.workstation +
                "," +
                "place:" +
                this.place +
                "}" +
                "\n";
    }

}
