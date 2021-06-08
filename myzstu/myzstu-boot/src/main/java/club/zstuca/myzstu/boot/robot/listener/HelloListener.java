package club.zstuca.myzstu.boot.robot.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.MatchType;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-24 16:30
 */
@Listener
@Component
public class HelloListener {

    @Priority(PriorityConstant.LAST)
    @Listen(PrivateMsg.class)
    @Filter(value = "hello.*", matchType = MatchType.REGEX_FIND, atBot = true)
    public void hello(PrivateMsg msg, MsgSender sender) {
        // ... do something
        sender.SENDER.sendPrivateMsg(msg.getAccountInfo().getAccountCode(),
                "hello " + msg.getAccountInfo().getAccountNickname());
    }

    @Listen(GroupMsg.class)
    @Filter(value = "大家好.*", matchType = MatchType.REGEX_FIND)
    public void everyoneHello(GroupMsg msg, MsgSender sender) {
        try {
            sender.SENDER.sendGroupMsg(msg.getGroupInfo().getGroupCode(),
                    "欢迎!" + msg.getAccountInfo().getAccountNickname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
