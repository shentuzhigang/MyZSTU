package club.zstuca.myzstu.boot.robot.util;


import catcode.CatCodeUtil;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-23 15:40
 */
public class MessageSenderUtil {
    public static void send(MsgGet msg, MsgSender sender, String content) {
        if (msg instanceof GroupMsg) {
            GroupMsg groupMsg = (GroupMsg) msg;
            sender.SENDER.sendGroupMsg(groupMsg, content);
        } else {
            sender.SENDER.sendPrivateMsg((PrivateMsg) msg, content);
        }
    }

    public static void sendAndAtSender(MsgGet msg, MsgSender sender, String content) {
        if (msg instanceof GroupMsg) {
            GroupMsg groupMsg = (GroupMsg) msg;
            sender.SENDER.sendGroupMsg(groupMsg,
                    CatCodeUtil.getInstance()
                            .toCat("at",
                                    false,
                                    "code=" +
                                            groupMsg.getAccountInfo()
                                                    .getAccountCode()) +
                            content);
        } else {
            sender.SENDER.sendPrivateMsg((PrivateMsg) msg, content);
        }
    }
}
