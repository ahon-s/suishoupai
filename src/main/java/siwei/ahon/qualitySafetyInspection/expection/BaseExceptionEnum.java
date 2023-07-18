package siwei.ahon.qualitySafetyInspection.expection;



import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  BaseExceptionEnum implements ResponseSuperEnum {


    SUCCESS(0, "SUCCESS","操作成功"),
    FAIL(400, "FAIL","操作失败"),
    AuthFail(401, "AuthFail","身份认证失败");

    private int code;
    private String status;
    private String message;


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getStatus() {
        return this.status;
    }



    @Override
    public String getMessage() {
        return this.message;
    }

}
