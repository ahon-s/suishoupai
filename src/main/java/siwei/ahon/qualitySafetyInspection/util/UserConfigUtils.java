package siwei.ahon.qualitySafetyInspection.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import siwei.ahon.qualitySafetyInspection.dto.TokenSections;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
@Component
public class UserConfigUtils {


    public static RedisUtils redisUtils;
    @Resource
    public  void setRedisUtils(RedisUtils redisUtils) {
        UserConfigUtils.redisUtils = redisUtils;
    }

    public static String isLoginAddress;

    @Value("${myValue.isLoginAddress}")
    public void setIsLoginAddress(String isLoginAddress) {
        UserConfigUtils.isLoginAddress = isLoginAddress;
    }

    public static HashMap<String, String> map = new HashMap<>();


    public static String UserLoginInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        if (redisUtils.hasKey("nickName-"+token))
        return (String) redisUtils.get("nickName-"+token);
        map.put("token",token);
        map.put("Content-Type","application/x-www-form-urlencoded");
        HttpRequest httpRequest = HttpRequest.get(isLoginAddress).addHeaders(map);
        String body = httpRequest.execute().body();
        JSONObject bodyJson = JSONObject.parseObject(body);
        if (bodyJson.getString("code").equals("0")&&!isEmpty(bodyJson.getString("data"))){
            String nickName = bodyJson.getJSONObject("data").getString("nickName");
            redisUtils.set("nickName-"+token,nickName);
            redisUtils.expire("sections-"+token,60*10);
            return nickName;
        }
        return null;
    }
    public static void getLoginSections(HttpServletRequest request){
        String token = request.getHeader("token");
        if (redisUtils.hasKey("sections-"+token)) return;
        map.put("token",token);
        map.put("Content-Type","application/x-www-form-urlencoded");
        HttpRequest httpRequest = HttpRequest.get(isLoginAddress).addHeaders(map);
        String body = httpRequest.execute().body();
        JSONObject bodyJson = JSONObject.parseObject(body);
        if (bodyJson.getString("code").equals("0")&&!isEmpty(bodyJson.getString("data"))){
            List<TokenSections> tokenSectionsList = JSONObject.parseArray(bodyJson.getJSONObject("data").getJSONArray("secetions").toJSONString(),TokenSections.class);
            if (isEmpty(tokenSectionsList)) throw new BaseException("没有任何标段权限");
            redisUtils.set("sections-"+token,tokenSectionsList);
            redisUtils.expire("sections-"+token,60*10);
        }
    }
}
