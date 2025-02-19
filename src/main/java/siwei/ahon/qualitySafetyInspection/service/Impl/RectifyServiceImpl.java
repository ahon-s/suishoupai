package siwei.ahon.qualitySafetyInspection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.expection.BaseException;
import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.mapper.ProblemMapper;
import siwei.ahon.qualitySafetyInspection.mapper.RectifyMapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.service.RectifyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.UserConfigUtils.UserLoginInfo;

@Service
public class RectifyServiceImpl implements RectifyService {
    @Resource
    RectifyMapper rectifyMapper;

    @Resource
    ProblemMapper problemMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    private HttpServletRequest request;
    @Override
    @Transactional
    public Integer addRectify(Rectify rectify) {
        Problem problem = problemMapper.selectById(rectify.getProblemId());
        if (isEmpty(problem)) throw new BaseException("不存在对应问题");
        if (problem.getStatus() == 3) throw new BaseException("问题已归档");
        if (problem.getStatus() == 4) throw new BaseException("问题待审核");
        problem.setStatus(4);
        rectify.setStatus(1);
        problemMapper.updateById(problem);
        rectifyMapper.insert(rectify);
        return rectify.getId();
    }

    @Override
    public PageData<Rectify> getRectifyList(Rectify rectify, PageFilterPojo pf) {
        QueryWrapper<Rectify> rectifyQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(rectifyQueryWrapper, rectify);
        timeFilter(queryWrapper,pf);
        queryWrapper.orderByDesc("gmt_create");
        Page<Rectify> rectifyPage = new Page<>(pf.getPageNum(), pf.getPageSize());
        IPage page = rectifyMapper.selectPage(rectifyPage, queryWrapper);
        PageData<Rectify> pd = new PageData<>(page);
        return pd;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_create",pf.getsTime());
        qw.lt("gmt_create",pf.geteTime());
    }
}
