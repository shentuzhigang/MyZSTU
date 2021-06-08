package club.zstuca.myzstu.boot.robot.listener;

import club.zstuca.myzstu.boot.robot.util.MessageSenderUtil;
import club.zstuca.myzstu.utils.http.HttpUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.GroupMemberIncrease;
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
 * @date 2021-06-08 15:53
 */
@Listener
@Component
public class MiscListener {

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "我爱你|你爱我吗",matchType = MatchType.REGEX_FIND)
    public void love(MsgGet msg, MsgSender sender) throws Exception {
        synchronized (this) {
            int t = ((int) (Math.random() * 1000)) % 2;
            String url = (t == 0 ? "https://api.ixiaowai.cn/tgrj/index.php/" : "http://test.isiyuan.net/tiangou/tg.php");
            String content = HttpUtil.doGet(url).getContent();
            if (content == null || content.isEmpty()) {
                return;
            }
            MessageSenderUtil.sendAndAtSender(msg, sender, content);
        }
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "网抑云",matchType = MatchType.REGEX_FIND)
    public void wyy(MsgGet msg, MsgSender sender) throws Exception {
        synchronized (this) {
            int t = ((int) (Math.random() * 1000)) % 2;
            String url = (t == 0 ? "https://api.lo-li.icu/wyy/" : "https://nd.2890.ltd/api/?format=text");
            String content = HttpUtil.doGet(url).getContent();
            if (content == null || content.isEmpty()) {
                return;
            }
            MessageSenderUtil.sendAndAtSender(msg, sender, content);
        }
    }

    @Listen(GroupMemberIncrease.class)
    public void i(GroupMemberIncrease groupMemberIncrease, MsgSender sender) {
        sender.SENDER.sendPrivateMsg("1600337300",
                groupMemberIncrease.getOperatorInfo().getOperatorCode() + "同意" +
                        groupMemberIncrease.getBeOperatorInfo().getBeOperatorCode() + "加入" +
                        groupMemberIncrease.getGroupInfo().getGroupCode());
    }

}
