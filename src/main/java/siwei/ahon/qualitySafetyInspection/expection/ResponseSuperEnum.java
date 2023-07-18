package siwei.ahon.qualitySafetyInspection.expection;

public interface  ResponseSuperEnum <T>{

    /**
     * 获取状态码
     * @return
     */
    public int getCode();

    /**
     * 获取响应消息
     * @return
     */
    public String getMessage();


    public String getStatus();

}
