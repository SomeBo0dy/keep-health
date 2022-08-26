package team.keephealth.yjj.mapper.sport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.sport.SportPunch;

@Mapper
@Repository
public interface SportPunchMapper extends BaseMapper<SportPunch> {
}
