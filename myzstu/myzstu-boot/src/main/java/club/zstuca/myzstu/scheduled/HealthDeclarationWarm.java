package club.zstuca.myzstu.boot.scheduled;

import club.zstuca.myzstu.boot.service.IHealthDeclarationService;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 21:18
 */
@Component
public class HealthDeclarationWarm {

    private IHealthDeclarationService iHealthDeclarationService;

    private BotManager botManager;

    @Autowired
    public HealthDeclarationWarm(IHealthDeclarationService iHealthDeclarationService, BotManager botManager) {
        this.iHealthDeclarationService = iHealthDeclarationService;
        this.botManager = botManager;
    }

    @Scheduled(cron = "0 0 8,9,10,11 * * *")
    public void execute() {
        MsgSender sender = botManager.getDefaultBot().getSender();
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2020", "计算机类20(4)", "610234267");
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2020", "计算机类20(3)", "551013539");
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void execute2() {
        MsgSender sender = botManager.getDefaultBot().getSender();
        iHealthDeclarationService.toQQGroup(sender, "信息学院", "2018", "计算机科学与技术18(3)", "1025402712");
    }
}
