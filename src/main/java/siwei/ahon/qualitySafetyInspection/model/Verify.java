package siwei.ahon.qualitySafetyInspection.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import siwei.ahon.qualitySafetyInspection.annotation.FilterFiled;
import siwei.ahon.qualitySafetyInspection.annotation.FilterTypeEnum;
import siwei.ahon.qualitySafetyInspection.pojo.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verify extends BaseModel {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotBlank(message = "审阅人不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String verifier;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String nickName;

    String verifierSection;
    @NotNull
    Integer rectifyId;
    @NotNull
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer problemId;
    @NotBlank(message = "备注不能为空")
    String description;
    @NotNull
    Integer rectify;
    Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
