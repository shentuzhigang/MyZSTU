package club.zstuca.myzstu.utils.test;

import club.zstuca.myzstu.utils.http.HttpResponse;
import club.zstuca.myzstu.utils.http.HttpUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-19 12:39
 */
public class MainTest {
    //@Test
    public void httpTest() throws Exception {
        HttpResponse httpResponse1 = HttpUtil.doGet("https://www.baidu.com/");
        System.out.println(httpResponse1);
        HttpResponse httpResponse2 = HttpUtil.doPost("https://www.baidu.com/");
        System.out.println(httpResponse2);
    }
    //@Test
    //自动识别验证码
    public void tess() throws Exception {
        String imgurl = "C:\\Users\\Lenovo\\Desktop\\shentuzhigang.top.png";
        //System.out.println("开始识别");
        String res = "";
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");
        //Properties properties = new Properties();
        //InputStream in = Consts.class.getClassLoader().getResourceAsStream("static/public.properties");
        //properties.load(in);
        //String dataPath = properties.getProperty("tessdatasrc");
        instance.setDatapath("D:\\Code\\Project\\JAVA\\DEMO_Project\\myzstu\\tessdata");
        System.out.println(instance);
        List<String> configs = new ArrayList<>();
        //configs.add("digits");
        instance.setTessVariable("user_defined_dpi", "300");
        instance.setConfigs(configs);
        //removeBackground(imgurl,imgurl);
        //cuttingImg(imgurl);
        File newFile = new File(imgurl);
        try {
            res = instance.doOCR(newFile);
            if(newFile.exists()) {
                //newFile.delete();
            }

        } catch (TesseractException e) {
            e.printStackTrace();
        }
        System.out.println(res);

    }
}
