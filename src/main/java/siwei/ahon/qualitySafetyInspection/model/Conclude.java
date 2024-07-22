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
public class Conclude extends BaseModel {
    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;
    @NotBlank(message = "结办人不能为空")
    String concluder;
    @NotBlank(message = "结办id不能为空")
    String concluderId;

    @NotNull(message = "问题id不能为空")
    Integer problemId;
//    @NotNull(message = "确认审核记录id不能为空")
//    Integer confirmVerifyId;
//    @NotNull(message = "整改记录id不能为空")
//    Integer rectifyId;
//    @NotNull(message = "审核记录id不能为空")
//    Integer verifyId;


    String description;

    //1是 2否
    @NotNull(message = "审阅状态不能为空")
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @TableField(value = "`status`")
    Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;

}
