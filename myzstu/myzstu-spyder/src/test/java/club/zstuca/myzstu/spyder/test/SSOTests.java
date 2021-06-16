package club.zstuca.myzstu.spyder.test;

import club.zstuca.myzstu.spyder.edu.EduLoginer;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-13 22:09
 */
public class SSOTests {
    public static void main(String[] args) throws Exception {
//        SSOLoginer ssoLoginer = new SSOLoginer();
//        ssoLoginer.login("2018329621200","STzg1600337300",null);
        new EduLoginer().login("2018329621200","stzg1600337300");
    }
}
