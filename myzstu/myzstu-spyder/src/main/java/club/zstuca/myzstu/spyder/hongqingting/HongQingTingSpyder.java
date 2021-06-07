package club.zstuca.myzstu.spyder.hongqingting;

import club.zstuca.myzstu.spyder.hongqingting.entity.RunData;
import club.zstuca.myzstu.utils.http.HttpUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 15:18
 */
@Component
public class HongQingTingSpyder {
    private String host = "http://tygl.zstu.edu.cn:8029";

    public String uploadRunData(RunData runData) {
//        Request.Builder builder = new Request.Builder();
//        Request build = builder.url("http://10.11.246.182:8029/DragonFlyServ/Api/webserver/uploadRunData")
//                .header("Charset", "UTF-8")
//                .header("Connection", "Keep-Alive")
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .post(RequestBody.create(compress(s,"UTF-8")))
//                .build();
//        System.out.println(new HttpClient().newCall(build).execute().body().string());
        Map header = new HashMap<String, String>();
        header.put("Charset", "UTF-8");
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        return HttpUtil.doGZIPPost(host + "/DragonFlyServ/Api/webserver/uploadRunData",
                header,
                null,
                runData.toString())
                .getContent();
    }

    public String getRunDataSummary(String studentno, String uid) {
//        String s1= "{'studentno':'2018329621200'," +
//                "'uid':'a26baf4e-a27a-424c-9d58-f04b700114a1faf4352521394a859275687a47b786721602508814$9d7370a8eb0c1286ce3c949c0c53469e'}";
//        Request.Builder builder1 = new Request.Builder();
//        Request build1 = builder1.url("http://10.11.246.182:8029/DragonFlyServ/Api/webserver/getRunDataSummary")
//                .header("Charset", "UTF-8")
//                .header("Connection", "Keep-Alive")
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .post(RequestBody.create(compress(s1,"UTF-8")))
//                .build();
//        System.out.println(new HttpClient().newCall(build1).execute().body().string());
        Map header = new HashMap<String, String>();
        header.put("Charset", "UTF-8");
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        return HttpUtil.doGZIPPost(host + "/DragonFlyServ/Api/webserver/getRunDataSummary",
                header,
                null,
                "{'studentno':'" + studentno + "'," +
                        "'uid':'" + uid + "'}")
                .getContent();

    }
}
