package club.zstuca.myzstu.boot.service;


import love.forte.simbot.api.sender.MsgSender;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-23 20:04
 */
public interface IHealthDeclarationService {
    void toQQGroup(MsgSender sender, String xueyuan, String nianji,
                   String className, String classQQGroupCode);
}
