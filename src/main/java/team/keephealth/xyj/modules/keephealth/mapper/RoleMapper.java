package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.keephealth.xyj.modules.keephealth.domain.entity.Role;


/**
 * 角色表(Role)表数据库访问层
 *
 * @author xyj
 * @since 2022-07-29 09:54:51
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

