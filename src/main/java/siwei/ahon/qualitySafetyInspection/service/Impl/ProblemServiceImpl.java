package siwei.ahon.qualitySafetyInspection.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.dto.TokenSections;
import siwei.ahon.qualitySafetyInspection.dto.UserInfo;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemStatusMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemTypeMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.ProblemStatus;
import siwei.ahon.qualitySafetyInspection.model.ProblemType;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.pojo.ProblemStatistics;

import siwei.ahon.qualitySafetyInspection.service.ProblemService;
import siwei.ahon.qualitySafetyInspection.util.RedisUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    RedisUtils redisUtils;

    @Override
    public Integer addProblem(Problem problem) {
        problem.setStatus(problem.getRectify() == 1 ? 3 : 1);
        problem.setNickName(UserLoginInfo(request));
        problemMapper.insert(problem);
        return problem.getId();
    }

    @Override
    public PageData<Problem> getProblemList(Problem problem, PageFilterPojo pf) {
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(problemQueryWrapper,problem);
        timeFilter(queryWrapper,pf);
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

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }



}
