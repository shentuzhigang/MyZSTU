package club.zstuca.myzstu.common.exception;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2021-05-22 16:29
 */
public class MyZSTUException extends Exception{

    private static final long serialVersionUID = 1L;

    public MyZSTUException() {
        super();
    }

    public MyZSTUException(String message) {
        super(message);
    }


    public MyZSTUException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyZSTUException(Throwable cause) {
        super(cause);
    }

    protected MyZSTUException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
