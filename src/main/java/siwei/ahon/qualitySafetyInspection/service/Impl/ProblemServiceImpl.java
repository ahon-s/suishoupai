package siwei.ahon.qualitySafetyInspection.service.Impl;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.net.URLEncoder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiledHelper;
import siwei.ahon.qualitySafetyInspection.dto.TokenSections;

import siwei.ahon.qualitySafetyInspection.expection.BaseException;
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
import siwei.ahon.qualitySafetyInspection.util.ExcelUtil;
import siwei.ahon.qualitySafetyInspection.util.RedisUtils;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Resource
    private HttpServletResponse response;

    @Override
    public Integer addProblem(Problem problem) {
        return null;
    }

    //日常检查
    @Override
    public Integer addProblemDailyInspection(Problem problem) {
        problem.setStatus(problem.getRectify() == 1 ? 3 : 1);
        problem.setStatus(1);
        if (isEmpty(problem.getSupervisorId())){
            problem.setStatus(99);
            problem.setAssignStatus(2);
        }
        problem.setType(1);
        problem.setAssignStatus(3);
        problemMapper.insert(problem);

        return problem.getId();
    }


    //随手拍
    @Override
    public Integer addProblemFreePhoto(Problem problem) {
        problem.setStatus(99);
        problem.setType(2);
        problem.setAssignStatus(1);
        problemMapper.insert(problem);
        return problem.getId();
    }


    //日常检查
    @Override
    public Integer addProblemDailyCheck(Problem problem) {
        problem.setType(3);
        problem.setStatus(3);
//        problem.setAssignStatus(3);
        problemMapper.insert(problem);
        return problem.getId();
    }


    //大连不用的接口
    @Override
    public PageData<Problem> getProblemList2(Problem problem, String statusList, PageFilterPojo pf) {

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("submitter",problem.getSubmitter());
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
    public List<ProblemStatistics> getProblemStatistics(String sectionId,PageFilterPojo pf) {
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
            Map<String, List<Problem>> sectionProblems = problems.stream().collect(Collectors.groupingBy(Problem::getSectionId));
            Set<String> keySet = sectionProblems.keySet();
            for (String key : keySet) {
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
    @Override
    public void getExcelFile(Problem problem, PageFilterPojo pf){

        /**
         * 获取数据
         * */
        pf.setPageSize(999999999);
        PageData<Problem> problemPageList = this.getProblemList(problem, pf);
        System.out.println(problemPageList);
        if (problemPageList.getTotalCount()>10000){
            throw new BaseException("数据量太大，请重新选择时间");
        }
        List<Problem> problemList = problemPageList.getDataList();
        String[] title = {"问题id","提出人","手机号","问题类型","问题标段","问题描述 ","问题照片","是否需要整改","整改部门","审核部门","分发工程单位","监理","处理状态","提出时间"};
        String fileName = "问题记录";
        String sheetName = "sheet1";
        String[][] content = new String[problemList.size()][title.length];
        final AtomicInteger atomicInteger= new AtomicInteger(0);
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        problemList.stream().forEach(e->{
            int row = atomicInteger.getAndIncrement();
            content[row][0] = e.getId().toString();
            content[row][1] = e.getSubmitter();
            content[row][2] = e.getPhoneNumber();
            switch (e.getType()){
                case 1:
                    content[row][3] = "日常检查";
                    break;
                case 2:
                    content[row][3] = "随手拍";
                    break;
                case 3:
                    content[row][3] = "日常巡检";
                    break;

            }
            content[row][4] = e.getSectionName();
            content[row][5] = e.getDescription();
            content[row][6] = e.getPictureUrl();
            if (!isEmpty(e.getRectify()))
            content[row][7] = e.getRectify() == 1? "否":"是";
            else content[row][7] = "未受理";
            content[row][8] = e.getRectifyDepartment();
            content[row][9] = e.getVerifyDepartment();
            content[row][10] = e.getBuildName();
            content[row][11] = e.getSupervisor();
            switch (e.getStatus()){
                case 1:
                    content[row][12] = "待整改";
                    break;
                case 2:
                    content[row][12] = "待审阅";
                    break;
                case 3:
                    content[row][12] = "已归档 ";
                    break;
                case 4:
                    content[row][12] = "待确认";
                    break;
                case 5:
                    content[row][12] = "待结办";
                    break;
                case 99:
                    content[row][12] = "待分配";
                    break;

            }
            content[row][13] = dateFormat.format(e.getGmtCreate());
//            content[row][14] = dateFormat.format(e.getGmtModified());
        });
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName,title,content,null);

        //设置响应文件
        response.addHeader("content-type", "application/vnd.ms-excel;charset=UTF-8");
        // 设置文件名称
        String fileNameURL = null;
        try {
            fileNameURL = URLEncoder.encode(fileName+".xls", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename="+fileNameURL+";"+"filename*=utf-8''"+fileNameURL);
        response.addHeader("Cache-Control", "no-cache");
        //返回文件流
        try{
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
