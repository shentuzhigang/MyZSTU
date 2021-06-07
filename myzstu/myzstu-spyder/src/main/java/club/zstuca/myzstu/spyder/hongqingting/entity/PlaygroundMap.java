package club.zstuca.myzstu.spyder.hongqingting.entity;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-29 21{27
 */
public class PlaygroundMap extends RegionalSportsMap {
    /**
     * 初始化坐标信息
     */
    private MeterPoint p1, p2, p3, p4;
    private Double l1, l2, l3, l4, totleLength;

    public PlaygroundMap() {
        this.p1 = MeterPoint.parse(new LatLonPoint(30.312759515931962, 120.35571445439146));
        this.p2 = MeterPoint.parse(new LatLonPoint(30.311800891797446, 120.35574664089964));
        this.p3 = MeterPoint.parse(new LatLonPoint(30.311828678136038, 120.35648156616972));
        this.p4 = MeterPoint.parse(new LatLonPoint(30.31275951593204, 120.35649229500578));
        this.l1 = MeterPoint.getDistance(this.p1, this.p2);
        this.l2 = MeterPoint.getDistance(this.p3, this.p2) * Math.PI / 2;
        this.l3 = MeterPoint.getDistance(this.p4, this.p3);
        this.l4 = MeterPoint.getDistance(this.p4, this.p1) * Math.PI / 2;
        this.totleLength = this.l1 + this.l2 + this.l3 + this.l4;
    }

    /**
     * 通过距离开始位置的路程(米)获得坐标
     *
     * @param dis 距离开始位置的路程(米)
     * @return
     */
    @Override
    MeterPoint getPoint(Double dis) {
        while (dis > this.totleLength) {
            dis -= this.totleLength;
        }
        while (dis < 0) {
            dis += this.totleLength;
        }
        if (dis < this.l1) {
            Double rat = dis / this.l1;
            Double x = (this.p2.x - this.p1.x) * rat + this.p1.x;
            Double y = (this.p2.y - this.p1.y) * rat + this.p1.y;
            return new MeterPoint(x, y);
        } else if (dis < this.l1 + this.l2) {
            Double r = MeterPoint.getDistance(this.p2, this.p3) / 2;
            Double deg = -(dis - this.l1) / r;
            Double cx = (this.p2.x + this.p3.x) / 2;
            Double cy = (this.p2.y + this.p3.y) / 2;
            Double deg_ = Math.atan((this.p2.x - cx) / (this.p2.y - cy));
            if (this.p2.y - cy < 0) {
                deg_ += Math.PI;
            }
            deg = deg_ - deg;
            Double x = cx + r * Math.sin(deg);
            Double y = cy + r * Math.cos(deg);
            return new MeterPoint(x, y);
        } else if (dis < this.l1 + this.l2 + this.l3) {
            Double rat = (dis - this.l1 - this.l2) / this.l3;
            Double x = (this.p4.x - this.p3.x) * rat + this.p3.x;
            Double y = (this.p4.y - this.p3.y) * rat + this.p3.y;
            return new MeterPoint(x, y);
        } else {
            Double r = MeterPoint.getDistance(this.p4, this.p1) / 2;
            Double deg = -(dis - this.l1 - this.l2 - this.l3) / r;
            Double cx = (this.p1.x + this.p4.x) / 2;
            Double cy = (this.p1.y + this.p4.y) / 2;
            Double deg_ = Math.atan((this.p4.x - cx) / (this.p4.y - cy));
            if (this.p4.y - cy < 0) {
                deg_ += Math.PI;
            }
            deg = deg_ - deg;
            Double x = cx + r * Math.sin(deg);
            Double y = cy + r * Math.cos(deg);
            return new MeterPoint(x, y);
        }
    }

    /**
     * 通过距离开始位置的路程(米)获得带有法向噪声的坐标
     *
     * @param dis          距离开始位置的路程(米)
     * @param normalOffset 法向噪声系数(米)
     * @return
     */
    @Override
    MeterPoint getPointWithOffset(Double dis, Double normalOffset) {
        while (dis > this.totleLength) {
            dis -= this.totleLength;
        }
        while (dis < 0) {
            dis += this.totleLength;
        }
        MeterPoint noVec;
        if (dis < this.l1) {
            noVec = new MeterPoint(this.p2.y - this.p1.y, this.p1.x - this.p2.x);
            Double l = MeterPoint.getDistance(noVec, new MeterPoint(0, 0));
            noVec = new MeterPoint(noVec.x / l, noVec.y / l);
        } else if (dis < this.l1 + this.l2) {
            Double r = MeterPoint.getDistance(this.p2, this.p3) / 2;
            Double deg = -(dis - this.l1) / r;
            Double cx = (this.p2.x + this.p3.x) / 2;
            Double cy = (this.p2.y + this.p3.y) / 2;
            Double deg_ = Math.atan((this.p2.x - cx) / (this.p2.y - cy));
            if (this.p2.x - cx < 0) {
                deg_ += Math.PI;
            }
            deg = deg_ - deg;
            noVec = new MeterPoint(Math.sin(deg), Math.cos(deg));
        } else if (dis < this.l1 + this.l2 + this.l3) {
            noVec = new MeterPoint(this.p4.y - this.p3.y, this.p3.x - this.p4.x);
            Double l = MeterPoint.getDistance(noVec, new MeterPoint(0, 0));
            noVec = new MeterPoint(noVec.x / l, noVec.y / l);
        } else {
            Double r = MeterPoint.getDistance(this.p4, this.p1) / 2;
            Double deg = -(dis - this.l1 - this.l2 - this.l3) / r;
            Double cx = (this.p1.x + this.p4.x) / 2;
            Double cy = (this.p1.y + this.p4.y) / 2;
            Double deg_ = Math.atan((this.p4.x - cx) / (this.p4.y - cy));
            if (this.p4.x - cx < 0) {
                deg_ += Math.PI;
            }
            deg = deg_ - deg;
            noVec = new MeterPoint(Math.sin(deg), Math.cos(deg));
        }
        MeterPoint raw = this.getPoint(dis);
        return new MeterPoint(raw.x + noVec.x * Math.random() * normalOffset,
                raw.y + noVec.y * Math.random() * normalOffset);
    }
}