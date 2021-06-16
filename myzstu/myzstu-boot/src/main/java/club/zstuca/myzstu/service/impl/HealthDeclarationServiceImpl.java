package club.zstuca.myzstu.service.impl;


import catcode.CatCodeUtil;
import club.zstuca.myzstu.boot.service.IHealthDeclarationService;
import club.zstuca.myzstu.spyder.ezstu.entity.Student;
import club.zstuca.myzstu.spyder.ezstu.query.HealthDeclarationQuery;
import club.zstuca.myzstu.utils.core.text.UnicodeUtil;
import love.forte.simbot.api.message.results.GroupAdmin;
import love.forte.simbot.api.message.results.GroupMemberList;
import love.forte.simbot.api.sender.MsgSender;
import net.mamoe.mirai.contact.BotIsBeingMutedException;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-23 20:05
 */
@Service
public class HealthDeclarationServiceImpl implements IHealthDeclarationService {

    @Override
    public void toQQGroup(MsgSender sender, String xueyuan, String nianji,
                          String className, String classQQGroupCode) {
        HealthDeclarationQuery healthDeclarationQuery = new HealthDeclarationQuery();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = s.format(c.getTime());
        try {
            List<Student> undeclaredStudentList = healthDeclarationQuery
                    .getUndeclaredStudentList(UnicodeUtil.toUnicode(xueyuan, UnicodeUtil.UnicodeEncodingFormat.BRACKETS),
                            nianji,
                            className,
                            curDate);
            if (undeclaredStudentList.size() == 0) {
                return;
            }
            GroupMemberList groupMemberList = sender.GETTER.getGroupMemberList(classQQGroupCode);
            String atTest = "健康申报";
            for (Student student : undeclaredStudentList) {
                try {
                    //user = (User)iUserService.loadUserByUsername(student.getUsername());
                } catch (Exception e) {
                    sender.SENDER.sendPrivateMsg("1600337300",
                            e.getMessage());
                    continue;
                }
                if (StringUtils.isEmpty("1600337300")) {
                    continue;
                }
                if (undeclaredStudentList.indexOf(student) % 10 == 0) {
                    sender.SENDER.sendGroupMsg(classQQGroupCode, atTest.toString());
                    atTest = "健康申报";
                }
                atTest = atTest + CatCodeUtil.INSTANCE.toCat("at", "qq=" + "1600337300");
                //
            }
            System.out.println(atTest);
            sender.SENDER.sendGroupMsg(classQQGroupCode, atTest);
            List<GroupAdmin> adminList = sender.GETTER.getGroupInfo(classQQGroupCode).getAdmins();
            for (GroupAdmin ss : adminList) {
                if ("3034372514".equals(ss.getAccountCode())) {
                    sender.SENDER.sendGroupMsg(classQQGroupCode,
                            CatCodeUtil.INSTANCE.toCat("at", "qq=all"));
                    break;
                }
            }
            //sender.SENDER.sendPrivateMsg("1600337300",atTest.toString());
        } catch (BotIsBeingMutedException mute) {
            sender.SENDER.sendPrivateMsg("1600337300",
                    "机器人在" + sender.GETTER.getGroupInfo(classQQGroupCode).getGroupName() + "(" + classQQGroupCode + ")"
                            + "被禁言了，禁言详情：" + DurationFormatUtils.
                            formatDurationWords(mute.getTarget().getBotMuteRemaining() * 1000,
                                    false,
                                    false));
        } catch (Exception e) {
            e.printStackTrace();
            SimpleDateFormat ds = new SimpleDateFormat("yyyy 年 MM 月 dd 日 E HH 时 mm 分 ss 秒");
            String curvDate = ds.format(c.getTime());
            sender.SENDER.sendPrivateMsg("1600337300",
                    sender.GETTER.getGroupInfo(classQQGroupCode).getGroupName() + "(" + classQQGroupCode + ")"
                            + "在" + curvDate + "健康申报提醒失败");
            sender.SENDER.sendPrivateMsg("2712826740",
                    sender.GETTER.getGroupInfo(classQQGroupCode).getGroupName() + "(" + classQQGroupCode + ")"
                            + "在" + curvDate + "健康申报提醒失败");
        } finally {
            try {
                sender.SENDER.sendGroupMsg(classQQGroupCode, "E浙理网页版与备用网址：\n" +
                        "http://stu.zstu.edu.cn/webroot/decision\n" +
                        "http://stu2.zstu.edu.cn/webroot/decision\n" +
                        "http://115.236.14.201/webroot/decision");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
