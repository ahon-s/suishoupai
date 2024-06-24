package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.AcceptProblem;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface AcceptProblemService {
    Integer addAcceptProblem(AcceptProblem acceptProblem);
    PageData<AcceptProblem> getAcceptProblemList(AcceptProblem acceptProblem, PageFilterPojo pf);
}
