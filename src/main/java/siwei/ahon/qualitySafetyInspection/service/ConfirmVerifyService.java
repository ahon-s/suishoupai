package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.ConfirmProblem;
import siwei.ahon.qualitySafetyInspection.model.ConfirmVerify;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface ConfirmVerifyService {
    Integer addConfirmVerify(ConfirmVerify confirmVerify);
    PageData<ConfirmVerify> getConfirmVerifyList(ConfirmVerify confirmVerify, PageFilterPojo pf);
}
