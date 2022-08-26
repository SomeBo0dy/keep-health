package team.keephealth.yjj.mapper.sport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.sport.SportPlanDetail;

@Mapper
@Repository
public interface SportPlanDetailMapper extends BaseMapper<SportPlanDetail> {
}
