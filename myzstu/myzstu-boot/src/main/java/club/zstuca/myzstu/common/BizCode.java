package club.zstuca.myzstu.common;

/**
 * 业务码
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-05-23 00:05
 */
public enum BizCode {
    /**
     * 成功
     */
    SUCCESS(20000, "成功"),
    /**
     * 错误
     */
    ERROR(50000, "成功"),
    /**
     * 系统未知异常
     */
    UNKNOWN_EXCEPTION(50001, "系统未知异常"),
    /**
     * 参数格式校验失败
     */
    VALID_EXCEPTION(50002, "参数格式校验失败");

    private int code;

    private String msg;

    BizCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
