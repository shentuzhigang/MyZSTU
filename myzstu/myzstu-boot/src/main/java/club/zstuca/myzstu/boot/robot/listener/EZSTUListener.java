package club.zstuca.myzstu.boot.robot.listener;

import catcode.CatCodeUtil;
import club.zstuca.myzstu.boot.service.IHealthDeclarationService;
import club.zstuca.myzstu.spyder.ezstu.AutoBackToDormitorySignServiceImpl;
import club.zstuca.myzstu.spyder.ezstu.AutoHealthDeclarationServiceImpl;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 16:08
 */
@Listener
@Component
public class EZSTUListener {

    Pattern p1 = Pattern.compile("归寝签到:(\\d{13})&amp;(.*)");
    Pattern p = Pattern.compile("健康申报:(\\d{13})&amp;(.*)");
    @Autowired
    private IHealthDeclarationService iHealthDeclarationService;

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "[E|e]浙理[问题|咨询]|网络请求失败",matchType = MatchType.REGEX_FIND)
    public void question(MsgGet msg, MsgSender sender) {
        String s = "加入群聊【2020E浙理】：660653780";
        if (msg instanceof GroupMsg) {
            GroupMsg msg1 = (GroupMsg) msg;
            if ("660653780".equals(msg1.getGroupInfo().getGroupCode())) {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, "小宝贝你已经在E浙理了哦！！！" +
                        CatCodeUtil.INSTANCE.toCat("at", "code=" + msg1.getAccountInfo().getAccountCode()));
            } else {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, s);
            }
        } else {
            sender.SENDER.sendPrivateMsg((PrivateMsg) msg, s);
        }
    }

    @Listen(PrivateMsg.class)
    @Filter(value = "归寝签到:(\\d{13})&(.*)",matchType = MatchType.REGEX_MATCHES)
    public void testListen12(PrivateMsg msg, MsgSender sender) throws Exception {
        String msgcontent = msg.getMsg();

        Matcher m = p1.matcher(msgcontent);
        if (m.find()) {
            String username = m.group(1);
            System.out.println(username);
            String password = m.group(2);
            System.out.println(password);
            AutoBackToDormitorySignServiceImpl autoBackToDormitorySignService = new AutoBackToDormitorySignServiceImpl();
            System.out.println(autoBackToDormitorySignService.autoService(username, password));
            sender.SENDER.sendPrivateMsg(msg, "签到成功");
        } else {
            sender.SENDER.sendPrivateMsg(msg, "格式错误");
        }
    }

    @Listen(PrivateMsg.class)
    @Filter(value = "健康申报:(\\d{13})&(.*)",matchType = MatchType.REGEX_MATCHES)
    public void testListen123(PrivateMsg msg, MsgSender sender) throws Exception {
        String msgcontent = msg.getMsg();
        Matcher m = p.matcher(msgcontent);
        if (m.find()) {
            String username = m.group(1);
            System.out.println(username);
            String password = m.group(2);
            System.out.println(password);
            AutoHealthDeclarationServiceImpl autoHealthDeclarationService = new AutoHealthDeclarationServiceImpl();
            System.out.println(autoHealthDeclarationService.autoService(username, password));
            sender.SENDER.sendPrivateMsg(msg, "申报成功");
        } else {
            sender.SENDER.sendPrivateMsg(msg, "格式错误");
        }
    }


    @Listen(PrivateMsg.class)
    @Filter(value = "提醒.*",matchType = MatchType.REGEX_MATCHES)
    public void testListen2(PrivateMsg msg, MsgSender sender) {
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2018", "计算机科学与技术18(3)", "1025402712");
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2020", "计算机类20(4)", "610234267");
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2020", "计算机类20(3)", "551013539");
    }
}
