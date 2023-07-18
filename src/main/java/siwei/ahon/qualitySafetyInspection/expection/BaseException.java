package siwei.ahon.qualitySafetyInspection.expection;



import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private int code;
    private String message;

    /**
     * 自定义异常枚举构造
     *
     * @param superEnumFace
     */
    public BaseException(ResponseSuperEnum superEnumFace) {
        this.code = superEnumFace.getCode();
        this.message = superEnumFace.getMessage();
    }
    public BaseException(String msg) {
        this.code = 400;
        this.message = msg;
    }
}

