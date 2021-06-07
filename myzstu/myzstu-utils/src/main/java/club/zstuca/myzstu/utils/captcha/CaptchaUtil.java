package club.zstuca.myzstu.utils.captcha;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 13:01
 */
public class CaptchaUtil {
    //自动识别验证码
    public static String tess(String imgurl) throws Exception {
        //System.out.println("开始识别");
        String res = "";
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");
        //Properties properties = new Properties();
        //InputStream in = Consts.class.getClassLoader().getResourceAsStream("static/public.properties");
        //properties.load(in);
        //String dataPath = properties.getProperty("tessdatasrc");
        instance.setDatapath("D:/Code/Project/JAVA/myzstu/tessdata");
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
        return res.trim();
    }
    public static void removeBackground(String imgUrl, String resUrl){
        //定义一个临界阈值
        int threshold = 300;
        try{
            BufferedImage img = ImageIO.read(new File(imgUrl));
            int width = img.getWidth();
            int height = img.getHeight();
            for(int i = 1;i < width;i++){
                for (int x = 0; x < width; x++){
                    for (int y = 0; y < height; y++){
                        Color color = new Color(img.getRGB(x, y));
                        //System.out.println("red:"+color.getRed()+" | green:"+color.getGreen()+" | blue:"+color.getBlue());
                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num >= threshold){
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            for(int i = 1;i<width;i++){
                Color color1 = new Color(img.getRGB(i, 1));
                int num1 = color1.getRed()+color1.getGreen()+color1.getBlue();
                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < height; y++)
                    {
                        Color color = new Color(img.getRGB(x, y));

                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num==num1){
                            img.setRGB(x, y, Color.BLACK.getRGB());
                        }else{
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            File file = new File(resUrl);
            if (!file.exists())
            {
                File dir = file.getParentFile();
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ImageIO.write(img, "jpg", file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void cuttingImg(String imgUrl){
        try{
            File newfile=new File(imgUrl);
            BufferedImage bufferedimage=ImageIO.read(newfile);
            int width = bufferedimage.getWidth();
            int height = bufferedimage.getHeight();
//            if (width > 198) {
//                bufferedimage=bufferedimage.getSubimage(1,0,(int) (width - (width-198) / 2),(int) (height));
//                if (height > 48) {
//                    bufferedimage=bufferedimage.getSubimage(0,(int) ((height - 48) / 2),198,(int) (height - (height - 48) / 2));
//                }
//            }else{
//                if (height > 48) {
//                    bufferedimage=bufferedimage.getSubimage(0,(int) ((height - 48) / 2),(int) (width),(int) (height - (height - 48) / 2));
//                }
//            }
            bufferedimage = bufferedimage.getSubimage(1,1,198,48);
            ImageIO.write(bufferedimage, "jpg", new File(imgUrl));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
