package club.zstuca.myzstu;

import club.zstuca.myzstu.provider.QQProvider;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-18 17:34
 */
@SpringBootTest
public class QQqunTets {
    @Autowired
    private QQProvider qqProvider;

    private String testGuin =  "128263838,150809751,158923528,246501801,291257839,313853498,319437127,325499542,348228340,392240802,426635210,468759038,470557066,551749699,554241652,554817912,582578307,636899849,637488213,663907180,693060545,715431201,720022417,733684384,761625122,778377586,786449421,791346753,791613120,796367487,807319548,811658527,816919532,828496942,850512645,852888016,859553489,862428817,876814181,892903800,895309420,913549511,928372850,965210182,971763587,974012276";
    @Test
    public void qqqun() throws Exception {
        Map<String,String> params=new HashMap<>();
        Map<String,String> headers=new HashMap<>();
        params.put("guin",testGuin);
        params.put("t","" + new Date().getTime());
        headers.put("Referer","http://qun.qq.com/join.html");
        //headers.put("Host","qun.qq.com");

        String s= HttpUtil.doGet("http://qun.qq.com/proxy/domain/shang.qq.com/wpa/g_wpa_get",headers,params).getContent();
        System.out.println(s);
    }
    @Test
    public void qqqun2(){
        qqProvider.setQQqunWPA("246501801");

        String[] temps=testGuin.split(",");
        List<String> list = new ArrayList<>();

        for(String string: temps ){
            list.add(string);
        }
        //list.add("733684384");
        Map<String, String> qQqunWPA = qqProvider.getQQqunWPAs(list);

        System.out.println(qqProvider.idkeyToUrl(qQqunWPA.get("733684384")));
        //System.out.println(qqProvider.getQQqunWPA("733684384"));
    }
}
