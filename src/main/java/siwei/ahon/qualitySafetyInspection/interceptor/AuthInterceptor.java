package siwei.ahon.qualitySafetyInspection.interceptor;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;
import siwei.ahon.qualitySafetyInspection.expection.MyResult;
import siwei.ahon.qualitySafetyInspection.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${myValue.authAddress}")
    private String authAddress;

    @Resource
    public RedisUtils redisUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (isEmpty(token)) throw new BaseException("请传入用户token");
        if (redisUtils.hasKey(token)) {
            JSONObject bodyObject = (JSONObject) JSONObject.toJSON(redisUtils.get(token));
            String status = bodyObject.getString("status");
            if (!status.equals("0")||isEmpty(bodyObject.getString("data"))){
                throw new BaseException("用户token失效");
            }
            return true;
        }
        JSONObject requestJson = new JSONObject();
        requestJson.put("token",token);
        HttpRequest authRequest = HttpRequest.post(authAddress).body(requestJson.toJSONString());
        String body = authRequest.execute().body();
        JSONObject bodyJson = JSONObject.parseObject(body);
        System.out.println(bodyJson);
        String status = bodyJson.getString("status");
        if (!status.equals("0")){
            throw new BaseException("用户token失效");
        }
//        String tokenTimeOut = bodyJson.getJSONObject("data").getString("tokenTimeOut");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        long timeOut = simpleDateFormat.parse(tokenTimeOut).getTime();
//        long nowTime = System.currentTimeMillis();
        redisUtils.set(token,bodyJson);
        redisUtils.expire(token,60*10);
//        redisUtils.expire(token,(timeOut-nowTime)/1000);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
    private void  httpReturn(HttpServletResponse response, MyResult myResult){
        String r= JSONObject.toJSONString(myResult);

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(r);

        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }


    }
}

