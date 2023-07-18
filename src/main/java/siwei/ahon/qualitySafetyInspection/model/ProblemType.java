package siwei.ahon.qualitySafetyInspection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemType {
    @TableId(type = IdType.AUTO)
    Integer id;
    String type;
}
