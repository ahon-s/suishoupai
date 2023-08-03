package siwei.ahon.qualitySafetyInspection.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Craft implements Serializable {
    Integer kind;
    Integer level;
    String kindDesc;
    String levelDesc;
}
