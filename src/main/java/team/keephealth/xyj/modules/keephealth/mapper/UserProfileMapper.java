package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色外形(UserProfile)表数据库访问层
 *
 * @author xyj
 * @since 2022-07-31 10:54:40
 */
@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {

}

