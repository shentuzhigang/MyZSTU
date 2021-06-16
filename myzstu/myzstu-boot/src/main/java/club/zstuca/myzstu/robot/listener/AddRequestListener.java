package club.zstuca.myzstu.robot.listener;

import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.FriendAddRequest;
import love.forte.simbot.api.message.events.GroupAddRequest;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 15:58
 */
@Listener
@Component
public class AddRequestListener {
    @Listen(FriendAddRequest.class)
    public void add(FriendAddRequest friendAddRequest, MsgSender sender) {
        sender.SENDER.sendPrivateMsg("1600337300", friendAddRequest.getAccountInfo().getAccountCode() + "发送添加好友");
        sender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), friendAddRequest.getText(), true, true);
    }


    @Listen(GroupAddRequest.class)
    public void addg(GroupAddRequest groupAddRequest, MsgSender sender) {
        sender.SENDER.sendPrivateMsg("1600337300", groupAddRequest.getAccountInfo().getAccountCode() + "通过" + groupAddRequest.getRequestType() + "邀请机器人加入" + groupAddRequest.getGroupInfo().getGroupCode());
        sender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), true, true, groupAddRequest.getText());
    }
}
