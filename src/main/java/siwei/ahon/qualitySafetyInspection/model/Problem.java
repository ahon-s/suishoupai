package siwei.ahon.qualitySafetyInspection.model;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;

    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank(message = "姓名不能为空")
    String submitter;

    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank(message = "提交人Id不能为空")
    String submitterId;

    @NotNull(message = "手机号不能为空")
    @FilterFiled(type = FilterTypeEnum.EQ)
    String phoneNumber;

    @NotBlank(message = "问题描述不能为空")
    String description;

//    @NotNull(message = "A坐标不能为空")
    Double A;
//    @NotNull(message = "B坐标不能为空")
    Double B;

//    @NotNull(message = "纬度不能为空")
    Double lat;
//    @NotNull(message = "经度不能为空")
    Double lng;

//    @NotBlank(message = "发现位置不能为空")
    String location;

//    @NotBlank
    String pictureUrl;

    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer type;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String sectionId;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String sectionName;
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer rectify;

    @FilterFiled(type = FilterTypeEnum.EQ)
    @TableField(value = "`status`")
    Integer status;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String rectifyDepartment;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String verifyDepartment;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String rectifyDepartmentId;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String verifyDepartmentId;

    //分配状态
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer assignStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    String rectifyTime;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String supervisor;
    @FilterFiled(type = FilterTypeEnum.EQ)
    String supervisorId;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String buildName;

    @FilterFiled(type = FilterTypeEnum.EQ)
    String buildId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    Date gmtCreate;
}
