package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.model.RectifySupervisor;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface RectifySupervisorService {
    Integer addRectifySupervisor(RectifySupervisor rectifySupervisor);
    PageData<RectifySupervisor> getRectifySupervisorList(RectifySupervisor rectifySupervisor, PageFilterPojo pf);
}
