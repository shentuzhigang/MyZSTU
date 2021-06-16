package club.zstuca.myzstu.entity;

import lombok.Data;

/**
 * 学生基本信息
 * @author ShenTuZhiGang
 */
@Data
public class Student {
    /**
     * 学号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     *
     */
    private String openid;
    /**
     *
     */
    private String acadeemy;
    /**
     *
     */
    private String major;
    /**
     *
     */
    private String stuClass;
    /**
     * 教务管理系统密码
     */
    private String eduPw;
    /**
     * 教务管理系统密码
     */
    private String ezstuPw;
    /**
     *
     */
    private String libPw;
    /**
     *
     */
    private String ecardPw;
    /**
     *
     */
    private String ssoPw;
}
