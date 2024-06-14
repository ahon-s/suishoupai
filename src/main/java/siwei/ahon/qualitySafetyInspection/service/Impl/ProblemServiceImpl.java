package siwei.ahon.qualitySafetyInspection.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.dto.TokenSections;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemStatusMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemTypeMapper;
import siwei.ahon.qualitySafetyInspection.mapper.VerifyMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.ProblemStatus;
import siwei.ahon.qualitySafetyInspection.model.ProblemType;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.pojo.ProblemStatistics;

import siwei.ahon.qualitySafetyInspection.service.ProblemService;
import siwei.ahon.qualitySafetyInspection.util.RedisUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.UserLoginInfo;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.getLoginSections;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    ProblemMapper problemMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    ProblemStatusMapper problemStatusMapper;

    @Resource
    ProblemTypeMapper problemTypeMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    VerifyMapper verifyMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    public Integer addProblem(Problem problem) {
        problem.setStatus(problem.getRectify() == 1 ? 3 : 1);
        if (isEmpty(problem.getNickName())) problem.setNickName(UserLoginInfo(request));
        problemMapper.insert(problem);
        return problem.getId();
    }

    @Override
    public PageData<Problem> getProblemList2(Problem problem, String statusList, PageFilterPojo pf) {

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Problem> queryWrapper = filedHelper.getQueryWrapper(problemQueryWrapper,problem);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<Problem> problemPage = new Page<>(pf.getPageNum(), pf.getPageSize());
        if (!isEmpty(statusList)){

            String[] status = statusList.split(",");

                queryWrapper.and(wrapper -> {
                            for (int i = 0; i < status.length; i++) {
                                int finalI = i;
                                wrapper.eq("status",Integer.valueOf(status[finalI])).or();
                            }
                    });


        }
        IPage page = problemMapper.selectPage(problemPage,queryWrapper);
        PageData<Problem> problemPageData = new PageData<>(page);
        return problemPageData;
    }
    @Override
    public PageData<Problem> getProblemList(Problem problem, PageFilterPojo pf) {
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(problemQueryWrapper,problem);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<Problem> problemPage = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = problemMapper.selectPage(problemPage,queryWrapper);
        PageData<Problem> problemPageData = new PageData<>(page);
        return problemPageData;
    }

    @Override
    public List<ProblemStatus> getProblemStatus() {
        List<ProblemStatus> problemStatuses = problemStatusMapper.selectList(null);
        return problemStatuses;
    }

    @Override
    public List<ProblemType> getProblemType() {
        List<ProblemType> problemTypes = problemTypeMapper.selectList(null);
        return problemTypes;
    }

    @Override
    public List<ProblemStatistics> getProblemStatistics(Integer sectionId,PageFilterPojo pf) {
        ArrayList<ProblemStatistics> problemStatisticsList = new ArrayList<>();

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        timeFilter(problemQueryWrapper,pf);
        if (isEmpty(sectionId)){
            String token = request.getHeader("token");
            getLoginSections(request);
            List<TokenSections> tokenSectionsList = (List<TokenSections>) redisUtils.get("sections-" + token);
            tokenSectionsList.stream().forEach(e->{
                problemQueryWrapper.eq("section_id",e.getAccount_BDID()).or();
            });
            List<Problem> problems = problemMapper.selectList(problemQueryWrapper);
            Map<Integer, List<Problem>> sectionProblems = problems.stream().collect(Collectors.groupingBy(Problem::getSectionId));
            Set<Integer> keySet = sectionProblems.keySet();
            for (Integer key : keySet) {
                ProblemStatistics problemStatistics = new ProblemStatistics();
                problemStatistics.setSectionId(key);
                problemStatistics.setTotalCount(sectionProblems.get(key).size());
                problemStatistics.setQualityCount((int) sectionProblems.get(key).stream().filter(e-> e.getType() == 1).collect(Collectors.toList()).stream().count());
                problemStatistics.setSecureCount((int) sectionProblems.get(key).stream().filter(e-> e.getType() == 2).collect(Collectors.toList()).stream().count());
                problemStatisticsList.add(problemStatistics);
            }
        }else {
            problemQueryWrapper.eq("section_id",sectionId);
            List<Problem> problems = problemMapper.selectList(problemQueryWrapper);
            ProblemStatistics problemStatistics = new ProblemStatistics();
            problemStatistics.setTotalCount(problems.size());
            problemStatistics.setSectionId(sectionId);
            problemStatistics.setQualityCount((int) problems.stream().filter(e-> e.getType() == 1).collect(Collectors.toList()).stream().count());
            problemStatistics.setSecureCount((int) problems.stream().filter(e-> e.getType() == 2).collect(Collectors.toList()).stream().count());
            problemStatisticsList.add(problemStatistics);
        }

        return problemStatisticsList;
    }

//    @Override
//    public void revokeProblem(Problem problem) {
//        Problem problem_origin = problemMapper.selectById(problem.getId());
//        String token = request.getHeader("token");
//        String nickName = (String) redisUtils.get("nickName-" + token);
//        if (!isEmpty(problem_origin.getNickName())){
//            if (!nickName.equals(problem_origin.getNickName()))
//                throw  new BaseException("不能撤回他人的提交");
//        }else {
//            JSONObject bodyObject = (JSONObject) JSONObject.toJSON(redisUtils.get(token));
//            String userName = bodyObject.getString("userName");
//            if (!userName.equals(problem_origin.getSubmitter()))
//                throw  new BaseException("不能撤回他人的提交");
//        }
//
//        QueryWrapper<Verify> queryWrapper = new QueryWrapper();
//        switch (problem_origin.getStatus()){
//            case 1 :
//                queryWrapper.eq("problem_id",problem.getId());
//                Verify verify = verifyMapper.selectOne(queryWrapper);
//                if(isEmpty(verify)) problemMapper.delete(new LambdaQueryWrapper<Problem>().eq(Problem::getId,problem.getId()));
//                else throw new BaseException("请撤销审阅");
//                break;
//            case 2:
//                throw new BaseException("问题已整改,需先撤销整改");
//                break;
//            case 3:
//                throw new BaseException("问题已归档，无法撤销");
//                break;
//            default:
//        }
//    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }



}
