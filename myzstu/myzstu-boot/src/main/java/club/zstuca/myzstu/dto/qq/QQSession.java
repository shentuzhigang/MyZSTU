package club.zstuca.myzstu.dto.qq;

import lombok.Data;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-03-19 12:51
 */
@Data
public class QQSession {
    private String openid;
    private String sessionKey;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
