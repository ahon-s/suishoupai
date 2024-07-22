package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ConfirmVerifyMapper;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.VerifyMapper;
import siwei.ahon.qualitySafetyInspection.model.*;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.ConfirmVerifyService;

import javax.annotation.Resource;

@Service
public class ConfirmVerifyServiceImpl implements ConfirmVerifyService {

    @Resource
    ConfirmVerifyMapper confirmVerifyMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    VerifyMapper verifyMapper;

    @Resource
    FilterFiledHelper filedHelper;


    @Override
    public Integer addConfirmVerify(ConfirmVerify confirmVerify) {

        confirmVerify.setStatus(1);

        LambdaUpdateWrapper<Problem> updateWrapper= new LambdaUpdateWrapper<>();
        updateWrapper.eq(Problem::getId,confirmVerify.getProblemId());
        updateWrapper.set(Problem::getStatus,5);
        problemMapper.update(null,updateWrapper);


//        LambdaUpdateWrapper<Verify> updateWrapperT= new LambdaUpdateWrapper<>();
//        updateWrapperT.eq(Verify::getId,confirmVerify.getVerifyId());
//        updateWrapperT.set(Verify::getStatus,2);
//        verifyMapper.update(null,updateWrapperT);

        int insert = confirmVerifyMapper.insert(confirmVerify);
        return insert;
    }

    @Override
    public PageData<ConfirmVerify> getConfirmVerifyList(ConfirmVerify confirmVerify, PageFilterPojo pf) {
        QueryWrapper<ConfirmVerify> queryWrapperT = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(queryWrapperT, confirmVerify);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<ConfirmVerify> pageT = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = confirmVerifyMapper.selectPage(pageT, queryWrapper);
        PageData<ConfirmVerify> pd = new PageData<>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
