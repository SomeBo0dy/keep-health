package team.keephealth.yjj.mapper.sport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.sport.Sport;

@Mapper
@Repository
public interface SportMapper extends BaseMapper<Sport> {
}
