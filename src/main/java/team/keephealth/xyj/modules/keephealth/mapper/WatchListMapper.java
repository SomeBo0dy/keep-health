package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.xyj.modules.keephealth.domain.entity.WatchList;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserInfoVo;

/**
 * 关注列表(WatchList)表数据库访问层
 *
 * @author xyj
 * @since 2022-07-29 14:30:04
 */
@Mapper
public interface WatchListMapper extends BaseMapper<WatchList> {
    @Select(" SELECT u.id, u.nick_name, u.avatar, u.sex, u.email, u.type, r.name type_name, u.state, u.phone_number, u.location, u.introduction " +
            " FROM kh_watch_list kwl " +
            " LEFT JOIN sys_user u ON u.id = kwl.watched_id " +
            " LEFT JOIN sys_user_role ur ON ur.user_id = u.id " +
            " LEFT JOIN sys_role r ON r.id = ur.role_id " +
            " ${ew.customSqlSegment} ")
    IPage<UserInfoVo> getWatchList(IPage<UserInfoVo> page, @Param(Constants.WRAPPER) QueryWrapper<UserInfoVo> queryWrapper);
}

