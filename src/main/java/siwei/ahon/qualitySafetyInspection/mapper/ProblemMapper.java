package siwei.ahon.qualitySafetyInspection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import siwei.ahon.qualitySafetyInspection.model.Problem;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
}
