package club.zstuca.myzstu.boot.robot.listener;

import club.zstuca.myzstu.boot.robot.util.MessageSenderUtil;
import club.zstuca.myzstu.spyder.edu.EduSSOLoginer;
import club.zstuca.myzstu.spyder.edu.EduSpyder;
import club.zstuca.myzstu.spyder.edu.GradeParser;
import club.zstuca.myzstu.spyder.edu.entity.Grade;
import club.zstuca.myzstu.utils.http.HttpContext;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 16:06
 */
@Listener
@Component
public class EduListener {

    @Listen(PrivateMsg.class)
    @Filter("查成绩")
    public void queryScore(PrivateMsg msg, MsgSender sender) throws Exception {
        System.out.println(msg);
        HttpContext httpContext = new HttpContext();
        EduSpyder eduSpyder = new EduSpyder();
        EduSSOLoginer eduSSOLoginer = new EduSSOLoginer();
        GradeParser gradeParser = new GradeParser();
        List<Grade> data = null;
        try {
            if (eduSSOLoginer.login("2018329621200", "STzg1600337300", httpContext)) {
                data = gradeParser.parse(eduSpyder.crawlGrades(httpContext));
            }
        } catch (Exception e) {
            throw e;
        }
        sender.SENDER.sendPrivateMsg(msg, data.toString());
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "学号.*查询|查询.*学号",matchType = MatchType.REGEX_FIND)
    public void xh(MsgGet msg, MsgSender sender){
        String s = "http://stu2.zstu.edu.cn/webroot/decision/view/report?viewlet=data%252F%25E5%25AD%25A6%25E5%258F%25B7%25E6%259F%25A5%25E8%25AF%25A2-%25E5%25AD%25A6%25E7%2594%259F-2020.cpt&op=h5#/report";
        MessageSenderUtil.send(msg, sender, s);
    }

}
