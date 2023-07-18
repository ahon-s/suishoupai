package siwei.ahon.qualitySafetyInspection.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserInfo {
    Integer sectionId;
    String sectionName;
    String companyName;
    List<Craft> craft;
}
