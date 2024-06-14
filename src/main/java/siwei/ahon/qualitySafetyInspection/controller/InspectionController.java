package siwei.ahon.qualitySafetyInspection.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import siwei.ahon.qualitySafetyInspection.expection.MyResult;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.*;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.pojo.ProblemStatistics;
import siwei.ahon.qualitySafetyInspection.service.ProblemService;
import siwei.ahon.qualitySafetyInspection.service.RectifyService;
import siwei.ahon.qualitySafetyInspection.service.VerifyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inspection")
@CrossOrigin(allowedHeaders  = {"*"})
@Validated
public class InspectionController {

    @Resource
    ProblemService problemService;

    @Resource
    RectifyService rectifyService;

    @Resource
    VerifyService verifyService;

    @Resource
    private HttpServletRequest request;
    /**问题模块*/
    @PostMapping("addProblem")
    public MyResult addProblem(@Valid Problem problem){
        Integer result = problemService.addProblem(problem);
        return MyResult.success(result);
    }
    /**获取问题记录*/
    @GetMapping("getProblemList")
    public MyResult getProblemList(Problem problem, PageFilterPojo pf){
        PageData<Problem> problemList = problemService.getProblemList(problem, pf);
        return MyResult.success(problemList);
    }
    @GetMapping("getProblemList2")
    public MyResult getProblemList2(Problem problem,String statusList, PageFilterPojo pf){
        PageData<Problem> problemList = problemService.getProblemList2(problem,statusList, pf);
        return MyResult.success(problemList);
    }

    /**获取问题类型*/
    @GetMapping("getProblemType")
    public MyResult getProblemType(){
        List<ProblemType> problemType = problemService.getProblemType();
        return MyResult.success(problemType);
    }
    /**获取问题状态*/
    @GetMapping("getProblemStatus")
    public MyResult getProblemStatus(){
        List<ProblemStatus> problemStatus = problemService.getProblemStatus();
        return MyResult.success(problemStatus);
    }
    /**获取问题统计信息*/
    @GetMapping("getProblemStatistics")
    public MyResult getProblemStatistics(Integer sectionId, PageFilterPojo pf){
        List<ProblemStatistics> problemStatistics = problemService.getProblemStatistics(sectionId, pf);
        return MyResult.success(problemStatistics);
    }
    /**整改模块*/
    @PostMapping("addRectify")
    public MyResult addRectify(@Valid Rectify rectify){
        request.setAttribute("problemId",rectify.getProblemId());
        Integer result = rectifyService.addRectify(rectify);
        return MyResult.success(result);
    }
    @GetMapping("getRectifyList")
    public MyResult getRectifyList(Rectify rectify,PageFilterPojo pf){
        PageData<Rectify> rectifyList = rectifyService.getRectifyList(rectify, pf);
        return MyResult.success(rectifyList);
    }

    /**审阅模块*/
    @PostMapping("addVerify")
    public MyResult addVerify(@Valid Verify verify){
        request.setAttribute("problemId",verify.getProblemId());
        Integer result = verifyService.addVerify(verify);
        return MyResult.success(result);
    }

    @GetMapping("getVerifyList")
    public  MyResult getVerifyList(Verify verify,PageFilterPojo pf){
        PageData verifyList = verifyService.getVerifyList(verify, pf);
        return MyResult.success(verifyList);
    }


}
