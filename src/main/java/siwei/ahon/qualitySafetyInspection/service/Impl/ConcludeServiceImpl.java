package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ConcludeMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ConfirmVerifyMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.VerifyMapper;
import siwei.ahon.qualitySafetyInspection.model.*;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.ConcludeService;

import javax.annotation.Resource;

@Service
public class ConcludeServiceImpl implements ConcludeService {

    @Resource
    ConcludeMapper concludeMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    VerifyMapper verifyMapper;

    @Resource
    ConfirmVerifyMapper confirmVerifyMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public Integer addConclude(Conclude conclude) {
        Integer status = conclude.getStatus();
        LambdaUpdateWrapper<Problem> updateWrapper= new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<Verify> updateWrapper1= new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<ConfirmVerify> updateWrapper2= new LambdaUpdateWrapper<>();
        if (status == 1){
            //修改问题状态 3已归档
            updateWrapper.eq(Problem::getId,conclude.getProblemId());
            updateWrapper.set(Problem::getStatus,3);
            problemMapper.update(null,updateWrapper);
            //修改审核意见状态 2 结办通过
            updateWrapper2.eq(ConfirmVerify::getId,conclude.getConfirmVerifyId());
            updateWrapper2.set(ConfirmVerify::getStatus,2);
            confirmVerifyMapper.update(null,updateWrapper2);
        }
        if (status == 2){
            //修改审核意见状态   3结办不通过
            updateWrapper2.eq(ConfirmVerify::getId,conclude.getConfirmVerifyId());
            updateWrapper2.set(ConfirmVerify::getStatus,3);
            confirmVerifyMapper.update(null,updateWrapper2);
        }
        int insert = concludeMapper.insert(conclude);
        return insert;
    }

    @Override
    public PageData<Conclude> getConcludeList(Conclude conclude, PageFilterPojo pf) {
        QueryWrapper<Conclude> queryWrapperT = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(queryWrapperT, conclude);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<Conclude> pageT = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = concludeMapper.selectPage(pageT, queryWrapper);
        PageData<Conclude> pd = new PageData<>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
