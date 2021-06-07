package club.zstuca.myzstu.spyder.ezstu.viewlet;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 23:02
 */
public enum FineReportType {
    ORDINARY("普通报表", ".cpt"),
    AGGREGATE("聚合报表", ".cpt"),
    DECISION("决策报表", ".frm");
    public String name;
    public String extensionName;

    FineReportType(String name, String extensionName) {
        this.name = name;
        this.extensionName = extensionName;
    }
}
