package club.zstuca.myzstu.spyder.ezstu.viewlet;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 22:52
 */
public abstract class AbstractFineReport {
    /**
     * 报表名称
     */
    public String name;
    /**
     * 报表类型
     */
    public FineReportType type;
    /**
     * op参数
     *
     * @see <a herf="https://help.fanruan.com/finereport/doc-view-251.html">op 参数的说明</a>
     */
    public String op;

    AbstractFineReport(FineReportType type) {
        this("", type);
    }

    AbstractFineReport(String name, FineReportType type) {
        this.type = type;
        this.name = name;
    }
}
