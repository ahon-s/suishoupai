package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.AcceptProblem;
import siwei.ahon.qualitySafetyInspection.model.Conclude;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface ConcludeService {
    Integer addConclude(Conclude conclude);
    PageData<Conclude> getConcludeList(Conclude conclude, PageFilterPojo pf);
}
