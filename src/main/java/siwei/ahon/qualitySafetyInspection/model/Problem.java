package siwei.ahon.qualitySafetyInspection.model;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("problem")
public class Problem extends BaseModel {
    @TableId(type = IdType.AUTO)
    Integer id;
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank(message = "提交人不能为空")
    String submitter;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String nickName;

    String submitterSection;
    @NotBlank(message = "备注不能为空")
    String description;
    @NotNull(message = "经纬度不能为空")
    Double lat;
    @NotNull(message = "经纬度不能为空")
    Double lng;
    @NotBlank
    String pictureUrl;
    @NotNull(message = "类型不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer type;
    @NotNull
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer sectionId;
    @NotNull(message = "类型不能为空")
    Integer rectify;
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer status;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
