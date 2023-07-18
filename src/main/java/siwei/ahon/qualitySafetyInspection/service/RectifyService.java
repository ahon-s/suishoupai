package siwei.ahon.qualitySafetyInspection.service;

import siwei.ahon.qualitySafetyInspection.expection.PageData;
import siwei.ahon.qualitySafetyInspection.model.Rectify;
import siwei.ahon.qualitySafetyInspection.pojo.PageFilterPojo;

public interface RectifyService {
    Integer addRectify(Rectify rectify);
    PageData<Rectify> getRectifyList(Rectify rectify, PageFilterPojo pf);
}
