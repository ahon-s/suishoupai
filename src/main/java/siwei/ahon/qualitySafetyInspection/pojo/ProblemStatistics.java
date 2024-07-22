package siwei.ahon.qualitySafetyInspection.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemStatistics {
    String sectionId;
    Integer totalCount;
    Integer qualityCount;
    Integer secureCount;
}
