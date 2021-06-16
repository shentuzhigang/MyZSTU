package club.zstuca.myzstu.robot.listener;

import club.zstuca.myzstu.boot.service.IHongQingTingService;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 16:15
 */
@Listener
@Component
public class HongQingTingListener {
    @Autowired
    private IHongQingTingService iHongQingTingService;


    @Listen(PrivateMsg.class)
    @Filter(value = "跑步.*", matchType = MatchType.REGEX_MATCHES)
    public void testListen212(PrivateMsg msg, MsgSender sender) {
        iHongQingTingService.randomUploadOne("2018329621200");
    }
}
