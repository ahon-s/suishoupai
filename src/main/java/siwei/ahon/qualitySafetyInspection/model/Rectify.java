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
public class Rectify extends BaseModel {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotBlank(message = "整改人不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String rectifier;

    String rectifierSection;
    @NotNull
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer problemId;
    @NotBlank(message = "备注不能为空")
    String description;
    @NotBlank(message = "图片不能为空")
    String pictureUrl;
//    @FilterFiled(type = FilterTypeEnum.EQ)
//    @TableField(value = "`status`")
    Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
