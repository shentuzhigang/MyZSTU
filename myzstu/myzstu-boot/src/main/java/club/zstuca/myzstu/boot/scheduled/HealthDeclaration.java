package club.zstuca.myzstu.boot.scheduled;

import club.zstuca.myzstu.spyder.ezstu.AutoHealthDeclarationServiceImpl;
import lombok.SneakyThrows;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-24 15:10
 */
public class HealthDeclaration {
    private BotManager botManager;

    public HealthDeclaration(BotManager botManager){
        this.botManager = botManager;
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 6 * * ? *")
    public void execute() {
        MsgSender msgSender = botManager.getDefaultBot().getSender();
        String username = "2018329621200";
        String password = "081639";
        AutoHealthDeclarationServiceImpl autoHealthDeclarationService = new AutoHealthDeclarationServiceImpl();
        System.out.println(autoHealthDeclarationService.autoService(username, password ));
        msgSender.SENDER.sendPrivateMsg("1600337300", "申报成功");
    }
}
