package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.AcceptProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.model.AcceptProblem;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.AcceptProblemService;

import javax.annotation.Resource;
@Service
public class AcceptProblemServiceImpl implements AcceptProblemService {

    @Resource
    AcceptProblemMapper acceptProblemMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public Integer addAcceptProblem(AcceptProblem acceptProblem) {
        int status = acceptProblem.getStatus();
        LambdaUpdateWrapper<Problem> updateWrapper= new LambdaUpdateWrapper<>();
        //是
        if (status==1){
            updateWrapper.eq(Problem::getId,acceptProblem.getProblemId());
            updateWrapper.set(Problem::getBuildId,acceptProblem.getBuildId());
            updateWrapper.set(Problem::getBuildName,acceptProblem.getBuildName());
            updateWrapper.set(Problem::getAssignStatus,2);
            updateWrapper.set(Problem::getStatus,99);
            problemMapper.update(null,updateWrapper);
        }
        //否
        if (status == 2){
            updateWrapper.eq(Problem::getId,acceptProblem.getProblemId());

            updateWrapper.set(Problem::getStatus,3);

            updateWrapper.set(Problem::getAssignStatus,4);

            problemMapper.update(null,updateWrapper);
        }

        int insert = acceptProblemMapper.insert(acceptProblem);

        return insert;
    }

    @Override
    public PageData<AcceptProblem> getAcceptProblemList(AcceptProblem acceptProblem, PageFilterPojo pf) {

        QueryWrapper<AcceptProblem> queryWrapperT = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(queryWrapperT, acceptProblem);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<AcceptProblem> pageT = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = acceptProblemMapper.selectPage(pageT, queryWrapper);
        PageData<AcceptProblem> pd = new PageData<>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
