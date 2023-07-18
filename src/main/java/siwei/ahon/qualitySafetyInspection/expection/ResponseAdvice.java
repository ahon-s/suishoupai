package siwei.ahon.qualitySafetyInspection.expection;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getGenericParameterType().equals(MyResult.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(returnType.getGenericParameterType().equals(String.class)){ //返回string类型
            return JSON.toJSONString(MyResult.success(body));
        }else if(body == null){ //返回void
            return MyResult.success(null);
        }else {
            return MyResult.success(body);
        }
    }

}
