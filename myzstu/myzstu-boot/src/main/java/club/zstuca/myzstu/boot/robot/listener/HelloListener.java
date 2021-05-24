package club.zstuca.myzstu.boot.robot.listener;

import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-24 16:30
 */
@Listener
public class HelloListener {

    @Priority(PriorityConstant.LAST)
    @Listen(PrivateMsg.class)
    public void hello(PrivateMsg msg, MsgSender sender){
        // ... do something
        sender.SENDER.sendPrivateMsg(msg.getAccountInfo().getAccountCode(),"hello");
    }
}
