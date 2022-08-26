package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserInfoVo;


/**
 * 用户表(User)表数据库访问层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select(" SELECT u.id, u.nick_name, u.avatar, u.sex, u.email, u.type, r.name type_name, u.state, u.phone_number, u.location, u.introduction " +
            " FROM sys_user u " +
            " LEFT JOIN sys_user_role ur ON ur.user_id = u.id " +
            " LEFT JOIN sys_role r ON r.id = ur.role_id " +
            " ${ew.customSqlSegment} ")
    IPage<UserInfoVo> getUserInfoByPage(IPage<UserInfoVo> page, @Param(Constants.WRAPPER) QueryWrapper<UserInfoVo> queryWrapper);
}

