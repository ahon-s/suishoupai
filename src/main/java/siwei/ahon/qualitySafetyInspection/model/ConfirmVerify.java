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
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmVerify extends BaseModel {

    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank(message = "确认人为空")
    String confirmer;
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank(message = "确认人id不能为空")
    String confirmerId;
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotNull(message = "问题不能为空")
    Integer problemId;
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @NotNull(message = "整改id不能为空")
//    Integer rectifyId;
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @NotNull(message = "审阅id不能为空")
//    Integer verifyId;

    String description;

    //1是 2否
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @TableField(value = "`status`")
    Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;


}
