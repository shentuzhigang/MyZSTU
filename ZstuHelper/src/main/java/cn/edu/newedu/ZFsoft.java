package cn.edu.newedu;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;

import cn.edu.zstu.beans.Grade;
import cn.edu.zstu.beans.Course;
import cn.edu.zstu.beans.Exam;
import cn.edu.zstu.util.RSAUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ZFsoft {
	public static void main(String[] args) {
        ZFsoft zFsoft=new ZFsoft();
        
        zFsoft.login("2018329621200","stzg1600337300");
        
        List<Course>courseList=zFsoft.checkCourse("2019","12");
        for(Course course:courseList){
            System.out.println(course);
        }
        System.out.println(courseList.size());/*
        List<Grade>gradeList=zFsoft.checkScore("","");
        for(Grade grade:gradeList){
            System.out.println(grade);
        }
        System.out.println(gradeList.size());
        List<Exam>examList=zFsoft.checkExam("","");
        for(Exam exam:examList){
            System.out.println(exam);
        }
        System.out.println(examList.size());
        */
        zFsoft.logout();
    }
	
	//登录URL
    private final String LOGIN_URL="http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
    //RSA URL
    private final String PUBLICKEY_URL="http://jwglxt.zstu.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=";
    //退出URL
    private final String LOGOUT_URL="http://jwglxt.zstu.edu.cn/jwglxt/logout?login_type=&t=";
    //课程URL
    private final String CHECK_COURSE_URL="http://jwglxt.zstu.edu.cn/jwglxt/kbcx/xskbcx_cxXsKb.html?doType=query&gnmkdm=N2151";
    //成绩URL
    private final String CHECK_GRADE_URL="http://jwglxt.zstu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdm=N305005";
    //考试URL
    private final String CHECK_EXAM_URL="http://jwglxt.zstu.edu.cn/jwglxt/kwgl/kscx_cxXsksxxIndex.html?doType=query&gnmkdm=N358105";

    private CloseableHttpClient httpClient;
    private BasicCookieStore basicCookieStore;
    public ZFsoft(){
        basicCookieStore=new BasicCookieStore();
        httpClient= HttpClients
                .custom()
                .setDefaultCookieStore(basicCookieStore)
                .build();
    }
    /**
     * 密码加密 RSA
     * @param password
     * @return
     */
    private String encryp(String password){
        //一、获取 exponent modulus 生成公钥
        String exponent=null,modulus=null;
        HttpGet gpkHttpGet=
                new HttpGet(PUBLICKEY_URL+new Date().getTime());
        gpkHttpGet.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        gpkHttpGet.setHeader("Accept-Encoding","gzip, deflate");
        gpkHttpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        gpkHttpGet.setHeader("Connection","keep-alive");
        gpkHttpGet.setHeader("Host","jwglxt.zstu.edu.cn");
        gpkHttpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        gpkHttpGet.setHeader("X-Requested-With","XMLHttpRequest");
        CloseableHttpResponse gpkResponse=null;
        try {
            gpkResponse = httpClient.execute(gpkHttpGet);
            if (gpkResponse.getStatusLine().getStatusCode() == 200) {
                String emJson = EntityUtils.toString(gpkResponse.getEntity(), "utf8");
                JSONObject jsonObject = new JSONObject(emJson);
                exponent = jsonObject.getString("exponent");
                modulus = jsonObject.getString("modulus");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                gpkResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //二、根据公钥进行密码加密
        System.out.println(modulus);
        System.out.println(exponent);
        System.out.println(password);
        try {
        	password=RSAUtil.encryptByRSAPublicKeySpec(password, modulus, exponent);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        System.out.println(password);
        return password;
    }
    
    /**
     * 获取Token
     * @param timestamp
     * @return
     */
    private String crawlCsrfToken(String timestamp){
        String csrftoken=null;
        HttpGet csrftokenHttpGet=
                new HttpGet(LOGIN_URL+timestamp);
        CloseableHttpResponse csrftokenResponse=null;
        try {
            csrftokenResponse = httpClient.execute(csrftokenHttpGet);
            if (csrftokenResponse.getStatusLine().getStatusCode() == 200) {
                Document csrftokenDoc = Jsoup.parse(EntityUtils.toString(csrftokenResponse.getEntity(), "utf8"));
                csrftoken = csrftokenDoc
                        .select(".col-sm-4")
                        .select(".sl_log_rt")
                        .select("input[id=csrftoken]")
                        .first()
                        .attr("value");
                return csrftoken;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                csrftokenResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 模拟登录
     * @param username
     * @param password
     * @return
     */
    public ZFsoft login(String username,String password){
        String timestamp=""+new Date().getTime();
        HttpPost loginHttpPost=new HttpPost(LOGIN_URL+timestamp);
        loginHttpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        loginHttpPost.setHeader("Accept-Encoding","gzip, deflate");
        loginHttpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        loginHttpPost.setHeader("Cache-Control","max-age=0");
        loginHttpPost.setHeader("Connection","keep-alive");
        loginHttpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        loginHttpPost.setHeader("Host","jwglxt.zstu.edu.cn");
        loginHttpPost.setHeader("Origin","http://jwglxt.zstu.edu.cn");
        loginHttpPost.setHeader("Upgrade-Insecure-Requests","1");
        loginHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        List<NameValuePair> loginParams=new ArrayList<NameValuePair>();
        password=this.encryp(password);
        String csrftoken=this.crawlCsrfToken(timestamp);
        loginParams.add(new BasicNameValuePair("csrftoken",csrftoken));
        loginParams.add(new BasicNameValuePair("yhm",username));
        loginParams.add(new BasicNameValuePair("mm",password));
        loginParams.add(new BasicNameValuePair("mm",password));
        CloseableHttpResponse loginResponse=null;
        try {
            loginHttpPost.setEntity(new UrlEncodedFormEntity(loginParams, "utf8"));
            loginResponse = httpClient.execute(loginHttpPost);
            List<Cookie>cookies=basicCookieStore.getCookies();
            if(cookies.isEmpty()){
                System.out.println("The Cookie Is None.");
            }else {
                for(Cookie cookie:cookies){
                	System.out.println(cookie.getName() + " : " + cookie.getValue());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
    /**
     * 退出
     * @return
     */
    public boolean logout() {
    	 String timestamp=""+new Date().getTime();
         HttpPost loginHttpPost=new HttpPost(LOGOUT_URL+timestamp);
         loginHttpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
         loginHttpPost.setHeader("Accept-Encoding","gzip, deflate");
         loginHttpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
         loginHttpPost.setHeader("Cache-Control","max-age=0");
         loginHttpPost.setHeader("Connection","keep-alive");
         loginHttpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
         loginHttpPost.setHeader("Host","jwglxt.zstu.edu.cn");
         loginHttpPost.setHeader("Origin","http://jwglxt.zstu.edu.cn");
         loginHttpPost.setHeader("Upgrade-Insecure-Requests","1");
         loginHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
         CloseableHttpResponse loginResponse=null;
         try {
             loginResponse = httpClient.execute(loginHttpPost);
             List<Cookie>cookies=basicCookieStore.getCookies();
             if(cookies.isEmpty()){
                 System.out.println("The Cookie Is None.");
             }else {
                 for(Cookie cookie:cookies){
                 	System.out.println(cookie.getName() + " : " + cookie.getValue());
                 }
             }
         }catch (Exception e){
             e.printStackTrace();
         }
         return true;
    }
    public List<Course> checkCourse(String xnm,String xqm){
        HttpPost courseHttpPost=new HttpPost(CHECK_COURSE_URL);
        courseHttpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        courseHttpPost.setHeader("Accept-Encoding","gzip, deflate");
        courseHttpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        courseHttpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        courseHttpPost.setHeader("Host","jwglxt.zstu.edu.cn");
        courseHttpPost.setHeader("Origin","http://jwglxt.zstu.edu.cn");
        courseHttpPost.setHeader("Proxy-Connection","keep-alive");
        courseHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        courseHttpPost.setHeader("X-Requested-With","XMLHttpRequest");
        List<NameValuePair>courseParams=new ArrayList<NameValuePair>();
        courseParams.add(new BasicNameValuePair("xnm",xnm));
        courseParams.add(new BasicNameValuePair("xqm",xqm));
        List<Course> res = new LinkedList<>();
        try {
            courseHttpPost.setEntity(new UrlEncodedFormEntity(courseParams, "utf8"));
            CloseableHttpResponse courseResponse = httpClient.execute(courseHttpPost);
            if (courseResponse.getStatusLine().getStatusCode() == 200) {
                if (courseResponse.getEntity() != null) {
                    String courseJson = EntityUtils.toString(courseResponse.getEntity(), "utf8");
                    System.out.print(courseJson);
                    if(courseResponse==null||courseResponse.equals("")|| !JSON.isValid(courseJson)){
                        return res;
                    }
                    JSONObject jsonObject = new JSONObject(courseJson);
                    JSONArray jsonArray = jsonObject.getJSONArray("kbList");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        Course course= new Course();
                        course.setDay(item.getString("xqjmc"));
                        course.setPeriod(item.getString("jc"));
                        course.setLength(item.getString("month"));
                        course.setName(item.getString("kcmc"));
                        course.setType("一般课");
                        course.setTeacher(item.getString("xm"));
                        course.setRoom(item.getString("cdmc"));
                        course.setWeek(item.getString("zcd"));
                        res.add(course);
                    }
                    jsonArray = jsonObject.getJSONArray("sjkList");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        Course course= new Course();
                        course.setName(item.getString("sjkcgs"));
                        course.setType("实践课");
                        course.setTeacher(item.getString("sjkcgs"));
                        res.add(course);
                    }

                    return  res;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看成绩
     * @param xnm
     * @param xqm
     * @return
     */
    public List<Grade> checkScore(String xnm,String xqm){
        HttpPost scoreHttpPost=new HttpPost(CHECK_GRADE_URL);
        scoreHttpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        scoreHttpPost.setHeader("Accept-Encoding","gzip, deflate");
        scoreHttpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        scoreHttpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        scoreHttpPost.setHeader("Host","jwglxt.zstu.edu.cn");
        scoreHttpPost.setHeader("Origin","http://jwglxt.zstu.edu.cn");
        scoreHttpPost.setHeader("Proxy-Connection","keep-alive");
        scoreHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        scoreHttpPost.setHeader("X-Requested-With","XMLHttpRequest");
        List<NameValuePair>scoreParams=new ArrayList<NameValuePair>();
        scoreParams.add(new BasicNameValuePair("xnm",xnm));
        scoreParams.add(new BasicNameValuePair("xqm",xqm));
        scoreParams.add(new BasicNameValuePair("_search","false"));
        scoreParams.add(new BasicNameValuePair("nd",""+new Date().getTime()));
        scoreParams.add(new BasicNameValuePair("queryModel.showCount","100"));
        scoreParams.add(new BasicNameValuePair("queryModel.currentPage","1"));
        scoreParams.add(new BasicNameValuePair("queryModel.sortName",""));
        scoreParams.add(new BasicNameValuePair("queryModel.sortOrder","asc"));
        scoreParams.add(new BasicNameValuePair("time","1"));
        List<Grade> res = new LinkedList<Grade>();
        try {
            scoreHttpPost.setEntity(new UrlEncodedFormEntity(scoreParams, "utf8"));
            CloseableHttpResponse scoreResponse = httpClient.execute(scoreHttpPost);
            if (scoreResponse.getStatusLine().getStatusCode() == 200) {
                if (scoreResponse.getEntity() != null) {
                    String scoreJson = EntityUtils.toString(scoreResponse.getEntity(), "utf8");
                    System.out.print(scoreJson);
                    JSONObject jsonObject = new JSONObject(scoreJson);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                    	Grade grade = new Grade();
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        try {
            				grade.setYear(item.getString("xnmmc"));
            				grade.setTerm(item.getString("xqm"));
            				grade.setCourseName(item.getString("kcmc"));
            				grade.setCredit(item.getString("xf"));
            				String gr = "";
            				// 有补考成绩 则算补考成绩
            				gr = new String(item.getString("ksxz").getBytes("UTF-8")).replace("?","");
            				if(!gr.equals("")) {
            					grade.setGrade(item.getString("cj"));
            				}
            				else {
            					grade.setGrade(item.getString("cj"));
            				}
            				grade.setGpa(item.getString("jd"));
            				res.add(grade);
            			}
            			catch(Exception e) {
            				e.printStackTrace();
            			}
                        
                    }
                    return  res;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Exam> checkExam(String xnm,String xqm){
        HttpPost examHttpPost=new HttpPost(CHECK_EXAM_URL);
        examHttpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
        examHttpPost.setHeader("Accept-Encoding","gzip, deflate");
        examHttpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
        examHttpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        examHttpPost.setHeader("Host","jwglxt.zstu.edu.cn");
        examHttpPost.setHeader("Origin","http://jwglxt.zstu.edu.cn");
        examHttpPost.setHeader("Proxy-Connection","keep-alive");
        examHttpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        examHttpPost.setHeader("X-Requested-With","XMLHttpRequest");
        List<NameValuePair>examParams=new ArrayList<NameValuePair>();
        examParams.add(new BasicNameValuePair("xnm","2019"));
        examParams.add(new BasicNameValuePair("xqm","3"));
        examParams.add(new BasicNameValuePair("_search","false"));
        examParams.add(new BasicNameValuePair("ksmcdmb_id","2019-20201QN"));
        examParams.add(new BasicNameValuePair("kch",""));
        examParams.add(new BasicNameValuePair("kc",""));
        examParams.add(new BasicNameValuePair("ksrq",""));
        examParams.add(new BasicNameValuePair("nd",""+new Date().getTime()));
        examParams.add(new BasicNameValuePair("queryModel.showCount","100"));
        examParams.add(new BasicNameValuePair("queryModel.currentPage","1"));
        examParams.add(new BasicNameValuePair("queryModel.sortName",""));
        examParams.add(new BasicNameValuePair("queryModel.sortOrder","asc"));
        examParams.add(new BasicNameValuePair("time","1"));
        List<Exam> res = new LinkedList<>();
        try {
            examHttpPost.setEntity(new UrlEncodedFormEntity(examParams, "utf8"));
            CloseableHttpResponse examResponse = httpClient.execute(examHttpPost);
            if (examResponse.getStatusLine().getStatusCode() == 200) {
                if (examResponse.getEntity() != null) {
                    String examJson = EntityUtils.toString(examResponse.getEntity(), "utf8");
                    System.out.print(examJson);
                    if(examJson==null||examJson.equals("")|| !JSON.isValid(examJson)){
                        return res;
                    }
                    JSONObject jsonObject = new JSONObject(examJson);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        Exam exam = new Exam();
                        JSONObject item = (JSONObject) jsonArray.get(i);
                        exam.setCourseName(item.getString("kcmc"));
                        exam.setSeatNumber(item.getString("zwh"));
                        exam.setExamPlace(item.getString("cdmc"));
                        exam.setExamTime(item.getString("kssj"));
                        res.add(exam);
                    }
                    return  res;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}