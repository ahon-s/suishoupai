package siwei.ahon.qualitySafetyInspection.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiled;
import siwei.ahon.qualitySafetyInspection.annotation.FilterTypeEnum;
import siwei.ahon.qualitySafetyInspection.pojo.BaseModel;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmProblem extends BaseModel {
    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;
    @NotBlank(message = "确认人不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String confirmer;
    @NotBlank(message = "确认人id不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String confirmerId;
    @NotBlank(message = "问题不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer problemId;
    @NotBlank(message = "受理记录id不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer acceptId;
    String description;
    //1是 2否
    Integer status;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String supervisor;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String supervisorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
