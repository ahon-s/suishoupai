package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.AcceptProblem;
import siwei.ahon.qualitySafetyInspection.model.ConfirmProblem;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface ConfirmProblemService {
    Integer addConfirmProblem(ConfirmProblem confirmProblem);
    PageData<ConfirmProblem> getConfirmProblemList(ConfirmProblem confirmProblem, PageFilterPojo pf);
}
