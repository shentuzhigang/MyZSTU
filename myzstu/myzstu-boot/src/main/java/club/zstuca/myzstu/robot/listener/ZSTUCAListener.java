package club.zstuca.myzstu.robot.listener;

import catcode.CatCodeUtil;
import club.zstuca.myzstu.boot.robot.util.MessageSenderUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-08 16:10
 */
@Listener
@Component
public class ZSTUCAListener {

    private final static String rex = "([a-zA-Z]+(j\\s*j|g\\s*g))";
    Pattern p = Pattern.compile(rex);

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "yt[gg|hh|hp].*", matchType = MatchType.REGEX_MATCHES)
    public void testListen(MsgGet msg, MsgSender sender) {
        String cqCode_at = CatCodeUtil.INSTANCE.toCat("at", "code=411841438");
        //String cq = KQCodeUtils.INSTANCE.toCq("at","qq=qq=411841438");
        Integer t = ((int) (Math.random() * 1000)) % 2;
        String s = t == 0 ? "ytjj推送做了吗？" + cqCode_at : "ytjj今天女装了吗？ヽ(•̀ω•́ )ゝ" + cqCode_at;
        MessageSenderUtil.send(msg, sender, s);
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "zyhh.*", matchType = MatchType.REGEX_MATCHES)
    public void testListen5(MsgGet msg, MsgSender sender) {
        String cqCode_at = CatCodeUtil.INSTANCE.toCat("at", "code=1569693919");
        String cqCode_at1 = CatCodeUtil.INSTANCE.toCat("at", "code=411841438");
        //String cq = KQCodeUtils.INSTANCE.toCq("at","qq=qq=411841438");
        Integer t = ((int) (Math.random() * 1000)) % 2;
        String s = t == 0 ? "zyhh今天拉新人了吗？" + cqCode_at : "ytjj今天女装了吗？ヽ(•̀ω•́ )ゝ" + cqCode_at1;
        MessageSenderUtil.send(msg, sender, s);
    }

    @Listen(GroupMsg.class)
    @Filter(value = rex, matchType = MatchType.REGEX_FIND, trim = true)
    public void testListen1_ext(GroupMsg msg, MsgSender sender) {
        String msgcontent = msg.getMsg().trim().replace(" ", "");

        Matcher m = p.matcher(msgcontent);
        Integer t = ((int) (Math.random() * 1000)) % 2;
        if (m.find()) {
            if (m.group(1).contains("st") || "ytjj".equals(m.group(1))) {
                String cqCode_at = CatCodeUtil.INSTANCE.toCat("at", "code=411841438");
                //String cq = KQCodeUtils.INSTANCE.toCq("at","qq=411841438");
                sender.SENDER.sendGroupMsg(msg, t == 0 ? "ytjj推送做了吗？" + cqCode_at : "ytjj今天女装了吗？ヽ(•̀ω•́ )ゝ" + cqCode_at);
            } else if ("zygg".equals(m.group(1)) || "zyjj".equals(m.group(1))) {
                String cqCode_at = CatCodeUtil.INSTANCE.toCat("at", "code=884519458");
                sender.SENDER.sendGroupMsg(msg, (t == 0 ? "zyjj今天zn了吗？" : "zyjj今天女装了吗？ヽ(•̀ω•́ )ゝ") + cqCode_at);
            } else {
                sender.SENDER.sendGroupMsg(msg, m.group(1) + "今天女装了吗？ヽ(•̀ω•́ )ゝ");
            }
        } else {
            sender.SENDER.sendGroupMsg(msg, "are you ok?" +
                    CatCodeUtil.INSTANCE.toCat("at", "code=" + msg.getAccountInfo().getAccountCode()));
        }
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "@加入计协", matchType = MatchType.REGEX_FIND)
    public void testListen1Add(MsgGet msg, MsgSender sender) {

        String s = "加入群聊【浙理工计算机协会2020】：160553121";
        if (msg instanceof GroupMsg) {
            GroupMsg msg1 = (GroupMsg) msg;
            if ("160553121".equals(msg1.getGroupInfo().getGroupCode())) {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, "小宝贝你已经在计协了哦，快点报名干事吧！！！" +
                        CatCodeUtil.INSTANCE.toCat("at", "code=" + msg1.getAccountInfo().getAccountCode()));
            } else {
                sender.SENDER.sendGroupMsg((GroupMsg) msg, s);
            }
        } else {
            sender.SENDER.sendPrivateMsg((PrivateMsg) msg, s);
        }
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "关注计协", matchType = MatchType.REGEX_FIND)
    public void testListen1_a(MsgGet msg, MsgSender sender) {
        String s = "关注计协微信公众号" + CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/cf23006c-b932-4e33-aa3c-9c82e975b31a%E5%BE%AE%E4%BF%A1%E5%85%AC%E4%BC%97%E5%8F%B7%E4%BA%8C%E7%BB%B4%E7%A0%81.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=1QlOZfUca9zKH4HjL5RtJH9qmXg%3D&Expires=1914026114");
        MessageSenderUtil.send(msg, sender, s);
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = ".*清韵.*纳新.*", matchType = MatchType.REGEX_FIND)
    public void testListen1(MsgGet msg, MsgSender sender) throws Exception {
        String s = "{" +
                " \"app\": \"com.tencent.structmsg\"," +
                " \"config\": {" +
                "  \"autosize\": true," +
                "  \"ctime\": 1598691597," +
                "  \"forward\": true," +
                "  \"token\": \"93ddf3af8fbd32fb8f7c7c6abc579483\"," +
                "  \"type\": \"normal\"" +
                " }," +
                " \"desc\": \"新闻\"," +
                " \"meta\": {" +
                "  \"news\": {" +
                "   \"action\": \"\"," +
                "   \"android_pkg_name\": \"\"," +
                "   \"app_type\": 1," +
                "   \"appid\": 1103188687," +
                "   \"desc\": \"清韵时逢丁丑月逢霜，奉天有幸生钱塘。四海诸朋皆来宜，词兑为酒曲为觞。清腔蕴魂通兰…\"," +
                "   \"jumpUrl\": \"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAwMjU2NTg3OA==&mid=2685693120&idx=1&sn=885992ddd1697facd65903272089576f&chksm=bf28bf59885f364fc08013d4f4f7edfe1f179b5d71ca86f814d6712e9903be88712ec112415c&mpshare=1&scene=23&srcid=0829ogLDoOEwkgJBZUkCPbCP&sharer_sharetime=1598691593441&sharer_shareid=3cd83d5d68e76d13fab13a243f7ee351#rd\"," +
                "   \"preview\": \"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/ZjKTRtuib8IAK4hpSibyur6Gqic2zp3BuDjnLRxGD3BaWh4U5NibAdmqFrPsM1YoPmRm7O7fESL2MFVY4oESTVcfVg\\/300?wx_fmt=jpeg&wxfrom=7\"," +
                "   \"source_icon\": \"\"," +
                "   \"source_url\": \"\"," +
                "   \"tag\": \"微信\"," +
                "   \"title\": \"清腔戏韵·静待君来||纳新，简介\"" +
                "  }" +
                " }," +
                " \"prompt\": \"[分享]清腔戏韵·静待君来||纳新，简介\"," +
                " \"ver\": \"0.0.0.1\"," +
                " \"view\": \"news\"" +
                "}";
//        s=LightApp.Templates.share("http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzAwMjU2NTg3OA==&mid=2685693120&idx=1&sn=885992ddd1697facd65903272089576f&chksm=bf28bf59885f364fc08013d4f4f7edfe1f179b5d71ca86f814d6712e9903be88712ec112415c&mpshare=1&scene=23&srcid=0829ogLDoOEwkgJBZUkCPbCP&sharer_sharetime=1598691593441&sharer_shareid=3cd83d5d68e76d13fab13a243f7ee351#rd",
//                "[分享]清腔戏韵·静待君来||纳新，简介",
//                "清腔戏韵·静待君来||纳新，简介",
//                "http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/ZjKTRtuib8IAK4hpSibyur6Gqic2zp3BuDjnLRxGD3BaWh4U5NibAdmqFrPsM1YoPmRm7O7fESL2MFVY4oESTVcfVg\\/300?wx_fmt=jpeg&wxfrom=7")
//        .toString();
        //s = MQCodeUtils.INSTANCE.toMq("app",s)+"收到[[分享]清腔戏韵·静待君来||纳新，简介]消息，请升级QQ版本查看";
        MessageSenderUtil.send(msg, sender, s);
    }

    @Listen(PrivateMsg.class)
    @Listen(GroupMsg.class)
    @Filter(value = "手语纳新", matchType = MatchType.REGEX_FIND)
    public void testListen1_as(MsgGet msg, MsgSender sender) {
        String s = "关注手语微信公众号" + CatCodeUtil.INSTANCE.toCat("image", "file=https://shentuzhigang.cn-bj.ufileos.com/45ab51be-be06-443e-adbc-dca37949396cs.jpg?UCloudPublicKey=TOKEN_199899cb-682e-4742-95f9-e1f31676169f&Signature=QI3guDh8H1FEx9Nia53sBntLgBE%3D&Expires=1914061136");
        MessageSenderUtil.send(msg, sender, s);
    }
}
