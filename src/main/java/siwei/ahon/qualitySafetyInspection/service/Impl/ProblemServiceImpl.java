package siwei.ahon.qualitySafetyInspection.service.Impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemStatusMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemTypeMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.ProblemStatus;
import siwei.ahon.qualitySafetyInspection.model.ProblemType;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.ProblemService;
import siwei.ahon.qualitySafetyInspection.util.UserConfigUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.UserLoginInfo;

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

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }



}
