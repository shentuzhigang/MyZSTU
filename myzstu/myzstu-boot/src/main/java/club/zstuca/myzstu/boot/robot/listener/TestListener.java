package club.zstuca.myzstu.boot.robot.listener;

import club.zstuca.myzstu.boot.robot.util.MessageSenderUtil;
import club.zstuca.myzstu.utils.core.text.UnicodeUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 15:53
 */
@Listener
@Component
public class TestListener {
    @Listen(PrivateMsg.class)
    @Filter("测试")
    public void test(MsgGet msg, MsgSender sender) {
        MessageSenderUtil.send(msg, sender,
                UnicodeUtil.toUnicode("信息学院", UnicodeUtil.UnicodeEncodingFormat.BRACKETS));
    }
}
