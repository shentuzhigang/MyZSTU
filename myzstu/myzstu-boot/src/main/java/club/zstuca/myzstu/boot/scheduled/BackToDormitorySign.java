package club.zstuca.myzstu.boot.scheduled;


import club.zstuca.myzstu.spyder.ezstu.AutoBackToDormitorySignServiceImpl;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-24 15:14
 */
public class BackToDormitorySign {

    private BotManager botManager;

    public BackToDormitorySign(BotManager botManager){
        this.botManager = botManager;
    }

    @Scheduled(cron = "0 0 21 * * ? *")
    public void execute() {
        MsgSender msgSender = botManager.getDefaultBot().getSender();
        try{
            String username = "2018329621200";
            System.out.println(username);
            String password = "081639";
            System.out.println(password);
            AutoBackToDormitorySignServiceImpl autoBackToDormitorySignService = new AutoBackToDormitorySignServiceImpl();
            System.out.println(autoBackToDormitorySignService.autoService(username, password ));
            msgSender.SENDER.sendPrivateMsg("1600337300", "签到成功");
        }catch ( Exception e){
            msgSender.SENDER.sendPrivateMsg("1600337300", "签到失败");
        }

    }
}
