package club.zstuca.myzstu.spyder.ezstu.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Random;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student {
    private static final long serialVersionUID = 1L;
    //ID
    private String id;
    //学生基本信息
    //微信ID
    private String openid;
    //学号
    private String username;
    //名字
    private String sname;
    //学院
    private String acadeemy;
    //专业
    private String major;
    //班级
    private String stuClass;
    //教务管理系统密码
    private String eduPw;
    //图书馆密码
    private String libPw;
    //一卡通密码
    private String ecardPw;
    //SSO密码
    private String ssoPw;
    //访问教务系统时，url中会随机生成一个24位字符串
    private String urlKey;
    //该学生的教务系统登录地址
    private String eduLoginUrl;
    //E浙理密码
    private String ezstuPw;
    private String xh;
    private String xm;
    private String xb;
    private String xy;
    private String x;
    private String xymc;
    private String xzb;
    private String nj;
    private String xslb;
    private String xz;
    private String xjzt;
    private String sfzx;
    private String sfzc;
    private String lys;
    private String byf;
    private String ydlb;
    private String qq;
    private String WeChat;

    public Student() {
        this.urlKey = get24RandomStr();
    }

    private static String get24RandomStr() {
        String res = "(";
        String source = "012345abcdefghigklmnopqrstuvwxyz";
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            res += source.charAt(random.nextInt(31));
        }
        res += ")";
        return res;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student [openid=");
        builder.append(openid);
        builder.append(", sid=");
        builder.append(username);
        builder.append(", sname=");
        builder.append(sname);
        builder.append(", acadeemy=");
        builder.append(acadeemy);
        builder.append(", major=");
        builder.append(major);
        builder.append(", stuClass=");
        builder.append(stuClass);
        builder.append(", eduPw=");
        builder.append(eduPw);
        builder.append(", libPw=");
        builder.append(libPw);
        builder.append(", ecardPw=");
        builder.append(ecardPw);
        builder.append(", ssoPw=");
        builder.append(ssoPw);
        builder.append(", urlKey=");
        builder.append(urlKey);
        builder.append(", eduLoginUrl=");
        builder.append(eduLoginUrl);
        builder.append("]");
        return builder.toString();
    }


}
