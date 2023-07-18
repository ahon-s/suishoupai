package siwei.ahon.qualitySafetyInspection.expection;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.Data;

import java.io.Serializable;


@Data
public class MyResult<T> implements Serializable {




    private int code;
    /**
     * 状态码
     */
    private String status;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;


    public MyResult(int code, String status) {
        this.code = code;
        this.status = status;
    }


    public MyResult(ResponseSuperEnum superEnumFace) {
        this.code = superEnumFace.getCode();
        this.status = superEnumFace.getStatus();
        this.message = superEnumFace.getMessage();
    }

    public MyResult(ResponseSuperEnum superEnumFace, T data) {
        this.code = superEnumFace.getCode();
        this.status = superEnumFace.getStatus();
        this.message = superEnumFace.getMessage();
        this.data = data;
    }

    public MyResult(int code, String status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 参数等错误
     *
     * @param message
     * @return
     */

    public static MyResult fail(String message) {
        if (ObjectUtils.isEmpty(message)) {
            return new MyResult(BaseExceptionEnum.FAIL);
        } else {
            return new MyResult(BaseExceptionEnum.FAIL.getCode(), BaseExceptionEnum.FAIL.getStatus(), message, null);
        }
    }


    public static MyResult authfail(String message) {
        if (ObjectUtils.isEmpty(message)) {
            return new MyResult(BaseExceptionEnum.AuthFail,null);
        } else {
            return new MyResult(BaseExceptionEnum.AuthFail.getCode(), BaseExceptionEnum.AuthFail.getStatus(), message, null);
        }
    }
    public static MyResult fail(String message, Integer code) {
        if (ObjectUtils.isEmpty(message)) {
            return new MyResult(BaseExceptionEnum.FAIL,null);
        }else if(!ObjectUtils.isEmpty(code)){
            return new MyResult(code,  BaseExceptionEnum.FAIL.getStatus(), message, null);

        } else {
            return new MyResult(BaseExceptionEnum.FAIL.getCode(), BaseExceptionEnum.FAIL.getStatus(), message, null);
        }
    }

    /**
     * @param data
     * @return
     */
    public static <T> MyResult success(T data) {
        return new MyResult(BaseExceptionEnum.SUCCESS, data);
    }


    public static <T> MyResult success() {
        return new MyResult(BaseExceptionEnum.SUCCESS);
    }

}
