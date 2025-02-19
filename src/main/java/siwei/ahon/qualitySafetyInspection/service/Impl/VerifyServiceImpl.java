package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.RectifyMapper;
import siwei.ahon.qualitySafetyInspection.mapper.VerifyMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.model.Verify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.VerifyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.UserLoginInfo;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    VerifyMapper verifyMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    RectifyMapper rectifyMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    private HttpServletRequest request;

    @Override
    @Transactional
    public Integer addVerify(Verify verify) {
        Problem problem = problemMapper.selectById(verify.getProblemId());
        Rectify rectify = rectifyMapper.selectById(verify.getRectifyId());

        List<Verify> preVerify = verifyMapper.selectList(new LambdaQueryWrapper<Verify>().eq(Verify::getRectifyId, verify.getRectifyId()));
        if (isEmpty(problem)) throw new BaseException("不存在对应问题");
        if (isEmpty(rectify)) throw new BaseException("不存在对应整改记录");
        if (!rectify.getProblemId().equals(problem.getId())) throw new BaseException("问题与整改记录不匹配");
        if (problem.getStatus() == 3) throw new BaseException("问题已归档");
        if (!isEmpty(preVerify)) throw new BaseException("已审阅此整改记录");
        if (problem.getType()==1)
            problem.setStatus(verify.getRectify() == 1 ? 3 : 1);
        if (problem.getType()==2)
            problem.setStatus(verify.getRectify() == 1 ? 4 : 1);
        rectify.setStatus(verify.getRectify() == 1 ? 2 : 3);
        problemMapper.updateById(problem);
        rectifyMapper.updateById(rectify);
//        if (isEmpty(verify.getNickName())) verify.setNickName(UserLoginInfo(request));
        verify.setStatus(1);
        verifyMapper.insert(verify);
        return verify.getId();
    }

    @Override
    public PageData getVerifyList(Verify verify, PageFilterPojo pf) {
        QueryWrapper<Verify> verifyQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(verifyQueryWrapper, verify);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<Verify> verifyPage = new Page<>(pf.getPageNum(),  pf.getPageSize());
        IPage page = verifyMapper.selectPage(verifyPage, queryWrapper);
        PageData<Verify> pd = new PageData<Verify>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
