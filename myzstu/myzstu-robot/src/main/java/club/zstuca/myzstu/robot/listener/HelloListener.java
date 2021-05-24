package club.zstuca.myzstu.robot.listener;

import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.message.events.PrivateMsg;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-24 16:30
 */
@Listener
public class HelloListener {

    @Listen(PrivateMsg.class)
    public void hello(PrivateMsg msg, MsgSender sender){
        // ... do something

    }
}
