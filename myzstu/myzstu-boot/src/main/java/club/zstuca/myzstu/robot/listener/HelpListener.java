package club.zstuca.myzstu.robot.listener;

import club.zstuca.myzstu.boot.robot.util.MessageSenderUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.SpareListen;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 16:12
 */
@Listener
@Component
public class HelpListener {

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = ".help")
    public void help(MsgGet msg, MsgSender sender) {
        MessageSenderUtil.sendAndAtSender(msg, sender, "还没写帮助");
    }

    @Listen(PrivateMsg.class)
    @Filter(value = "\\..*", matchType = MatchType.REGEX_MATCHES, atBot = true)
    public void testListen101(PrivateMsg msg, MsgSender sender) {
//        HTTPClientUtil.doPost("https://zstu.cpdaily.com/wec-amp-boya/boya/getChatReply",
//                );
        //System.out.println(msg);
        // 以下三种方法均可，效果相同
        //System.out.println(sender.SENDER.sendPrivateMsg(msg, msg.getMsg()));
//        sender.SENDER.sendPrivateMsg(msg.getQQ(), msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQCode(), msg.getMsg());
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = ".*[\\?|？]", matchType = MatchType.REGEX_MATCHES, atBot = true)
    public void ask(MsgGet msg, MsgSender sender) {
        String s = "想知道我会干什么吗?\n去问问我爸爸吧！！";
        MessageSenderUtil.sendAndAtSender(msg, sender, s);
    }

    @SpareListen
    public void spare(MsgGet msg, MsgSender sender) {
        MessageSenderUtil.send(msg, sender, "你说的，我不是很懂a");
    }
}
