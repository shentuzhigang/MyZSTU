package club.zstuca.myzstu.exception;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-16 16:11
 */
public class StudentException extends MyZSTUException{
    private int code;

    public StudentException(String message){
        super(message);
    }
    public StudentException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
