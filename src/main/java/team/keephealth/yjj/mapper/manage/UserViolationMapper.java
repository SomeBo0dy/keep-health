package team.keephealth.yjj.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.manage.UserViolation;

@Mapper
@Repository
public interface UserViolationMapper extends BaseMapper<UserViolation> {
}
