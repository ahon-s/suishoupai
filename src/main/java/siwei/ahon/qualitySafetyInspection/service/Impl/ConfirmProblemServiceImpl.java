package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.AcceptProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ConfirmProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.model.AcceptProblem;
import siwei.ahon.qualitySafetyInspection.model.ConfirmProblem;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.ConfirmProblemService;

import javax.annotation.Resource;

public class ConfirmProblemServiceImpl implements ConfirmProblemService {
    @Resource
    ConfirmProblemMapper confirmProblemMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    AcceptProblemMapper acceptProblemMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public Integer addConfirmProblem(ConfirmProblem confirmProblem) {
        Integer status = confirmProblem.getStatus();
        LambdaUpdateWrapper<Problem> updateWrapper= new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<AcceptProblem> updateWrapper1= new LambdaUpdateWrapper<>();
        if (status == 1){
            //修改问题状态
            updateWrapper.eq(Problem::getId, confirmProblem.getProblemId());
            updateWrapper.set(Problem::getAssignStatus,3);
            updateWrapper.set(Problem::getSupervisor,confirmProblem.getSupervisor());
            updateWrapper.set(Problem::getSupervisorId,confirmProblem.getSupervisorId());
            updateWrapper.set(Problem::getStatus,1);
            problemMapper.update(null,updateWrapper);
            //修改受理记录状态
            updateWrapper1.eq(AcceptProblem::getId, confirmProblem.getAcceptId());
            updateWrapper1.set(AcceptProblem::getConfirmStatus,2);
            acceptProblemMapper.update(null,updateWrapper1);
        }
        if (status == 2 ){
            updateWrapper1.eq(AcceptProblem::getId, confirmProblem.getAcceptId());
            updateWrapper1.set(AcceptProblem::getConfirmStatus,3);
            acceptProblemMapper.update(null,updateWrapper1);
        }

        int insert = confirmProblemMapper.insert(confirmProblem);
        return insert;
    }

    @Override
    public PageData<ConfirmProblem> getConfirmProblemList(ConfirmProblem confirmProblem, PageFilterPojo pf) {

        QueryWrapper<ConfirmProblem> queryWrapperT = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(queryWrapperT, confirmProblem);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<ConfirmProblem> pageT = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = confirmProblemMapper.selectPage(pageT, queryWrapper);
        PageData<ConfirmProblem> pd = new PageData<>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
