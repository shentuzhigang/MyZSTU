package club.zstuca.myzstu.spyder.ezstu.viewlet;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 22:58
 */
public abstract class AbstractOrdinaryFineReport extends AbstractFineReport {
    AbstractOrdinaryFineReport() {
        this("");
    }

    AbstractOrdinaryFineReport(String name) {
        super(name, FineReportType.ORDINARY);
    }
}
