package siwei.ahon.qualitySafetyInspection.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import siwei.ahon.qualitySafetyInspection.dto.TokenSections;
import siwei.ahon.qualitySafetyInspection.dto.UserInfo;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.util.StringUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.getLoginSections;

@Component
@Aspect
public class AuthAop {
    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ProblemMapper problemMapper;

    @Before("execution(* siwei.ahon.qualitySafetyInspection.service.Impl.ProblemServiceImpl.addProblem(..))")
    public void beforeProblemService(JoinPoint joinPoint){
        Integer problemSectionId = Integer.valueOf(request.getParameter("sectionId"));
        System.out.println("problemSectionId:"+problemSectionId);
        if(!validPermission(problemSectionId)) throw new BaseException("不在对应标段，权限不足");

    }
    @Before("execution(* siwei.ahon.qualitySafetyInspection.service.Impl.RectifyServiceImpl.addRectify(..))" +
            "||execution(* siwei.ahon.qualitySafetyInspection.service.Impl.VerifyServiceImpl.addVerify(..))")
    public void beforeVerifyAndRectifyService(JoinPoint joinPoint){
        Integer problemId = (Integer) request.getAttribute("problemId");
        Problem problem = problemMapper.selectById(problemId);
        if (isEmpty(problem)) throw new BaseException("对应的问题不存在");
        if (!validPermission(problem.getSectionId())) throw new BaseException("不在对应标段，权限不足");
    }



    private boolean validPermission(Integer problemSectionId){
        String token = request.getHeader("token");
        JSONObject bodyJson = (JSONObject) JSONObject.toJSON(redisUtils.get(token));
//        List<UserInfo> userInfos = null;
        List<TokenSections> tokenSectionsList = null;
        try{
            getLoginSections(request);
            tokenSectionsList = (List<TokenSections>) redisUtils.get("sections-" + token);
//           userInfos = JSONObject.parseArray(bodyJson.getJSONObject("data").getJSONArray("section").toJSONString(), UserInfo.class);

        }catch (NullPointerException nullPointerException){
            throw new BaseException("token无对应权限");
        }
//        System.out.println("userInfo:"+tokenSectionsList);
        AtomicReference<Boolean> aBoolean = new AtomicReference<>(new Boolean(false));
        tokenSectionsList.stream().forEach(e->{
            if (e.getAccount_BDID().equals(problemSectionId)) aBoolean.set(true);
        });

        return aBoolean.get();
    }

}
