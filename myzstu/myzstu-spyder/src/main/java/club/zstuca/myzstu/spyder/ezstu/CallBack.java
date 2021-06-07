package club.zstuca.myzstu.spyder.ezstu;

/**
 * @Author ShenTuZhiGang
 * @Version 1.0.0
 * @Date 2020-07-12 15:28
 */
public class CallBack {
    private String errorCode;
    private String status;
    private String errorMsg;
    private String accessToken;
    private String url;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "CallBack{" +
                "errorCode='" + errorCode + '\'' +
                ", status='" + status + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}