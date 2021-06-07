package club.zstuca.myzstu.spyder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 12:57
 */
public class Consts {

    //协议头
    public static final String PROTOCOL = "http://";

    //协议头
    public static final String PROTOCOLS = "https://";

    //浏览器代理
    public static final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

    //编码类型
    public static final String ENCODING = "UTF-8";

    //教务系统Host
    //public static final String EDUHOST = "jwglxt.zstu.edu.cn";
    public static final String EDUHOST = "10.11.247.52/jwglxt";

    //E浙理Host
    public static final String EZLHOST = "stu.zstu.edu.cn";
    //115.236.14.201
    //"stu.zstu.edu.cn"
    //"stu2.zstu.edu.cn"
    //图书馆Host
    public static final String LIBHOST = "10.11.35.23:8080";

    //统一身份认证
    public static final String SSOHOST= "sso.zstu.edu.cn";

    //一卡通Host
    public static final String YKTHOST="ykt.zstu.edu.cn";


    public static Map<String, String> weekMap = new HashMap<String, String>();
    static {
        weekMap.put("一", "1");
        weekMap.put("二", "2");
        weekMap.put("三", "3");
        weekMap.put("四", "4");
        weekMap.put("五", "5");
        weekMap.put("六", "6");
        weekMap.put("日", "7");
    }





}
