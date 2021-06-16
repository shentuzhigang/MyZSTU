package club.zstuca.myzstu.dto.wechat;

import lombok.Data;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-02-10 12:47
 */
@Data
public class WeChatSession {
    private String openid;
    private String sessionKey;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
