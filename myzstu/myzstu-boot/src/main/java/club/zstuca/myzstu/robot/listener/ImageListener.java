package club.zstuca.myzstu.robot.listener;

import catcode.CatCodeUtil;
import club.zstuca.myzstu.utils.http.HttpUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.springframework.stereotype.Component;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-11-16 20:35
 */
@Listener
@Component
public class ImageListener {
    @Listen(PrivateMsg.class)
    @Filter(value = ".*", atBot = true)
    public void testListeni(PrivateMsg msg, MsgSender sender) {
        new MessageChainBuilder();
        System.out.println(CatCodeUtil.INSTANCE.getCat(msg.getMsg(), "image"));
        String mqs = CatCodeUtil.INSTANCE.getCat(msg.getMsg());
        System.out.println(msg.getMsg());
        //List<CQCode> image = CatCodeUtil.INSTANCE..build().getCQCodeFromMsgByType(msg.getMsg(), "image");
        //System.out.println(CQUtils.getImageFile(image.get(0).getParam("file")));
        System.out.println(mqs);
    }

    @Listen(GroupMsg.class)
    @Filter(value = "校\\s*历", matchType = MatchType.REGEX_MATCHES)
    public void xiaoli(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/d8f7bc30-48a5-470e-a330-176ecc7021a92020-2021.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=1llahRXTg%2BAeZpmzko4zHiJGZNc%3D&Expires=1913335243"));
    }

    @Listen(GroupMsg.class)
    @Filter(value = "[E|e]浙理.*下载", matchType = MatchType.REGEX_FIND)
    public void testListenezstu(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/55de84f9-b84c-41d3-a210-8b30ee17aed7e.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=mNt86ik5FFE%2Fxzo1uvdUydk7jxs%3D&Expires=1914025574"));
    }

    @Listen(GroupMsg.class)
    @Filter(value = "地\\s*图", matchType = MatchType.REGEX_MATCHES)
    public void ditu(GroupMsg msg, MsgSender sender) {
        if (msg.getMsg().replace(" ", "").trim().contains("地图1")) {
            sender.SENDER.sendGroupMsg(msg, CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/d8df8430-bfaf-482f-8177-646518f1cd98%E5%9C%B0%E5%9B%BE%20%281%29.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=B%2BEgnHHoqk3fYKIRdgqxb7samks%3D&Expires=1913349368"));
        } else if (msg.getMsg().replace(" ", "").trim().contains("地图2")) {
            sender.SENDER.sendGroupMsg(msg, CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/d8df8430-bfaf-482f-8177-646518f1cd98%E5%9C%B0%E5%9B%BE%20%281%29.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=B%2BEgnHHoqk3fYKIRdgqxb7samks%3D&Expires=1913349368"));

        } else {
            sender.SENDER.sendGroupMsg(msg,
                    CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/3b101582-4353-4826-bd2c-513693228a8c%E5%9C%B0%E5%9B%BE%20%286%29.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=T%2FIATl3YnOQ0kwu1wVtBnbdX4Cc%3D&Expires=1913371664"));

        }
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "涩图", atBot = true)
    public void at(MsgGet msg, MsgSender sender) throws Exception {
        synchronized (this) {
            Integer t = ((int) (Math.random() * 1000)) % 2;
            String url1 = (t == 0 ? "https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php" : "https://api.btstu.cn/sjbz/api.php?lx=suiji");
            String url = HttpUtil.doGet(url1).getContent();
            if (url == null || url.isEmpty()) {
                return;
            }
            if (msg instanceof GroupMsg) {
                sender.SENDER.sendGroupMsg((GroupMsg) msg,
                        CatCodeUtil.INSTANCE.toCat("image", "file=" + url));

            } else {
                sender.SENDER.sendPrivateMsg((PrivateMsg) msg,
                        CatCodeUtil.INSTANCE.toCat("image", "file=" + url));
            }
        }
    }
}
