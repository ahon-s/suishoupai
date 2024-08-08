package siwei.ahon.qualitySafetyInspection.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiled;
import siwei.ahon.qualitySafetyInspection.annotation.FilterTypeEnum;
import siwei.ahon.qualitySafetyInspection.pojo.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "accept_problem")
public class AcceptProblem extends BaseModel {

    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;

    @NotBlank(message = "受理人id不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String accepterId;

    @NotBlank(message = "受理人不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String accepter;

    @NotNull(message = "问题id不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer problemId;

    String description;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String  buildName;


    @FilterFiled(type = FilterTypeEnum.EQ)
    String  buildId;


    //1是 2否
    @NotNull(message = "受理状态不能为空")
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @TableField(value = "`status`")
    int status;


    int confirmStatus;


    @FilterFiled(type = FilterTypeEnum.EQ)
    String  sectionName;


    @FilterFiled(type = FilterTypeEnum.EQ)
    String  sectionId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
