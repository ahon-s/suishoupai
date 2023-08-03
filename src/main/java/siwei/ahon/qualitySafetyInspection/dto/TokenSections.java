package siwei.ahon.qualitySafetyInspection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class TokenSections implements Serializable {
    String bCode;
    Integer account_BDID;
    String bName;
    String bID;
}
