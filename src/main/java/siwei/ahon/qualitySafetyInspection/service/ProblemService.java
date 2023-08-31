package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.Problem;
import siwei.ahon.qualitySafetyInspection.model.ProblemStatus;
import siwei.ahon.qualitySafetyInspection.model.ProblemType;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;
import siwei.ahon.qualitySafetyInspection.pojo.ProblemStatistics;


import java.util.List;

public interface ProblemService {
    Integer addProblem(Problem problem);

    PageData<Problem> getProblemList(Problem problem, PageFilterPojo pf);

    List<ProblemStatus> getProblemStatus();

    List<ProblemType> getProblemType();

    List<ProblemStatistics> getProblemStatistics(Integer sectionId,  PageFilterPojo pf);

//    //撤销问题
//    void revokeProblem(Problem problem);
}
