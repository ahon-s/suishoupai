package siwei.ahon.qualitySafetyInspection.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Date gmtModified;

    @JsonIgnore
    @TableField(select = false)
    @TableLogic(value = "0", delval = "id")
    Integer deleted;

}
