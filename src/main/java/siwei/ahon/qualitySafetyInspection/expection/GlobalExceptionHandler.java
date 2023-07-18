package siwei.ahon.qualitySafetyInspection.expection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理业务异常
     * @param baseException
     * @return
     */


    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public MyResult exceptionHandler(BaseException baseException){
        return MyResult.fail(baseException.getMessage());
    }

//    @ExceptionHandler(value = authException.class)
//    @ResponseBody
//    public MyResult exceptionHandler(authException authException){
//        return MyResult.fail(authException.getMessage());
//    }


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public MyResult exceptionHandler(BindException BindException){

        List<FieldError> fieldError=BindException.getFieldErrors();
        StringBuffer stringBuffer=new StringBuffer();
        fieldError.forEach(s->stringBuffer
                .append(s.getField()+":"+s.getDefaultMessage())
                .append(";"));
        return MyResult.fail(stringBuffer.toString());
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public MyResult exceptionHandler(HttpRequestMethodNotSupportedException Exception){
        return MyResult.fail(Exception.getMessage());
    }


    @ExceptionHandler(value = DataAccessException.class)
    @ResponseBody
    public MyResult exceptionHandler(DataAccessException exception){
        System.out.println(exception);
        return MyResult.fail("数据库访问错误:"+exception.getCause().getMessage());
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public MyResult exceptionHandler(DuplicateKeyException exception){
        System.out.println(exception);
        return MyResult.fail("数据库数据已存在:"+exception.getCause().getMessage());
    }


    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public MyResult exceptionHandler(NoHandlerFoundException exception){
        System.out.println(exception);
        return MyResult.fail("不存在的接口:"+exception.getMessage(),404);
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public MyResult exceptionHandler(MissingServletRequestParameterException exception){
        System.out.println(exception);
        return MyResult.fail("必选参数:"+exception.getMessage(),404);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public MyResult exceptionHandler(ConstraintViolationException exception){
        System.out.println(exception);
        return MyResult.fail(exception.getMessage(),400);
    }


//
//    /**
//     * 未知异常处理
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public MyResult exceptionHandler(Exception exception){
//        logger.error("未处理的错误类型:"+exception);
//        return MyResult.fail("未处理的错误类型:"+exception.getMessage());
//    }

}
