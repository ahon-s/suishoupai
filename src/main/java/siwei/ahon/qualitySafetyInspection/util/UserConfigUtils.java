package siwei.ahon.qualitySafetyInspection.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.springframework.util.StringUtils.isEmpty;
@Component
public class UserConfigUtils {

    public static String isLoginAddress;

    @Value("${myValue.isLoginAddress}")
    public void setIsLoginAddress(String isLoginAddress) {
        UserConfigUtils.isLoginAddress = isLoginAddress;
    }

    public static String UserLoginInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        HashMap<String, String> map = new HashMap<>();
        map.put("token",token);
        map.put("Content-Type","application/x-www-form-urlencoded");
        HttpRequest httpRequest = HttpRequest.get(isLoginAddress).addHeaders(map);
        String body = httpRequest.execute().body();
        JSONObject bodyJson = JSONObject.parseObject(body);
        if (bodyJson.getString("code").equals("0")&&!isEmpty(bodyJson.getString("data"))){
            String nickName = bodyJson.getJSONObject("data").getString("nickName");
            return nickName;
        }
        return null;
    }
}
