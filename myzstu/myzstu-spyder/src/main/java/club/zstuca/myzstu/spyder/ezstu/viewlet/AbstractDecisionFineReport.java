package club.zstuca.myzstu.spyder.ezstu.viewlet;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 22:59
 */
public abstract class AbstractDecisionFineReport extends AbstractFineReport {
    AbstractDecisionFineReport() {
        this("");
    }

    AbstractDecisionFineReport(String name) {
        super(name, FineReportType.DECISION);
    }
}
