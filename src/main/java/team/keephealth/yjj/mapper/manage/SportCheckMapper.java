package team.keephealth.yjj.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.manage.SportCheck;

@Mapper
@Repository
public interface SportCheckMapper extends BaseMapper<SportCheck> {
}
