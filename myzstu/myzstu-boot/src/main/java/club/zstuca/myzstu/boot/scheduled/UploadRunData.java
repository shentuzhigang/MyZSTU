package club.zstuca.myzstu.boot.scheduled;


import club.zstuca.myzstu.boot.service.IHongQingTingService;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-10-26 19:46
 */
//@Component
public class UploadRunData {
    private IHongQingTingService iHongQingTingService ;

    private BotManager botManager;


    public UploadRunData(BotManager botManager, IHongQingTingService iHongQingTingService){
        this.botManager = botManager;
        this.iHongQingTingService = iHongQingTingService;
    }
    //@Override
    //@Scheduled(cron = "0 0 15 * * *")
    public void execute() {
        MsgSender sender = botManager.getBot("3034372514").getSender();
        try{
//            Thread.sleep((long) (Math.random() * 100_000 + 1000_000));
//            Boolean b1=iHongQingTingService.randomUploadOne(System.currentTimeMillis()/1000,"2018329621200");
//            Boolean b2=iHongQingTingService.randomUploadOne(System.currentTimeMillis()/1000,"2018333500182");
//            iHongQingTingService.randomUploadOne("2018329621200");
//            if(b1&&b2){
//                sender.SENDER.sendPrivateMsg("1600337300","上传成功");
//            }else {
//                sender.SENDER.sendPrivateMsg("1600337300","上传失败");
//            }
//
//            //iHongQingTingService.randomUploadOne(System.currentTimeMillis()/1000,"2018329621197");
        }catch (Exception e){
            e.printStackTrace();
            sender.SENDER.sendPrivateMsg("1600337300","上传失败："+e.getMessage());
        }

    }
}
