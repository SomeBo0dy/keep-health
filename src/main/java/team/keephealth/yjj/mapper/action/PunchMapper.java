package team.keephealth.yjj.mapper.action;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.action.Punch;

@Mapper
@Repository
public interface PunchMapper extends BaseMapper<Punch> {
}
