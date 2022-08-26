package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserRole;

/**
 * (UserRole)表数据库访问层
 *
 * @author xyj
 * @since 2022-07-28 16:27:16
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

