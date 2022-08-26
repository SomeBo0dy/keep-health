package team.keephealth.yjj.mapper.action;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.action.UserAim;

@Mapper
@Repository
public interface UserAimMapper extends BaseMapper<UserAim> {
}
