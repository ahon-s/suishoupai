package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.Verify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface VerifyService {
    Integer addVerify(Verify verify);
    PageData getVerifyList(Verify verify, PageFilterPojo pf);
}
