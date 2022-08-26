package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLife;
import org.apache.ibatis.annotations.Mapper;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowLifeVo;

/**
 * 用户动态表(ShowLife)表数据库访问层
 *
 * @author xyj
 * @since 2022-07-29 19:54:57
 */
@Mapper
public interface ShowLifeMapper extends BaseMapper<ShowLife> {

    @Select(" SELECT ksl.ksl_id, ksl.user_id, u.nick_name user_name, u.avatar user_avatar, ksl.text, ksl.state, ksl.create_time, ksl.update_time " +
            " FROM kh_show_life ksl " +
            " LEFT JOIN sys_user u ON u.id = ksl.user_id " +
            " ${ew.customSqlSegment} ")
    IPage<ShowLifeVo> getShowLife(IPage<ShowLifeVo> page, @Param(Constants.WRAPPER) QueryWrapper<ShowLifeVo> queryWrapper);
}

