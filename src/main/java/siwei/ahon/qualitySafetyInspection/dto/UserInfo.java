package siwei.ahon.qualitySafetyInspection.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class UserInfo implements Serializable {
    Integer sectionId;
    String sectionName;
    String companyName;
    List<Craft> craft;
}
