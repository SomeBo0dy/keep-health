package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowTip;
import org.apache.ibatis.annotations.Mapper;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowTipListVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.ShowTipVo;

import static team.keephealth.common.constants.SystemConstants.STATUS_NORMAL;


/**
 * 动态举报表(ShowTip)表数据库访问层
 *
 * @author xyj
 * @since 2022-08-04 10:34:48
 */
@Mapper
public interface ShowTipMapper extends BaseMapper<ShowTip> {
    @Select(" SELECT kst.ksl_id ksl_id, ksl.user_id ksl_user_id, u.nick_name ksl_user_name, ksl.text ksl_content, count(*) AS tip_count " +
            " FROM kh_show_tip kst " +
            " LEFT JOIN kh_show_life ksl ON kst.ksl_id = ksl.ksl_id " +
            " LEFT JOIN sys_user u ON ksl.user_id = u.id " +
            " WHERE ksl.state = '" + STATUS_NORMAL +"' " +
            " GROUP BY kst.ksl_id ")
    IPage<ShowTipListVo> tipList(IPage<ShowTipListVo> page);

    @Select(" SELECT kst.kst_id kst_id, kst.ksl_id ksl_id, kst.tip_content tip_content, ksl.user_id ksl_user_id, ksl.text ksl_content, u.nick_name ksl_user_name, kst.create_by create_by, u2.nick_name tipper_name, kst.create_time creat_time" +
            " FROM kh_show_tip kst " +
            " LEFT JOIN kh_show_life ksl ON kst.ksl_id = ksl.ksl_id " +
            " LEFT JOIN sys_user u ON u.id = ksl.user_id " +
            " LEFT JOIN sys_user u2 ON u2.id = kst.create_by " +
            " ${ew.customSqlSegment} ")
    IPage<ShowTipVo> getTipPageInfo(IPage<ShowTipVo> page, @Param(Constants.WRAPPER)  QueryWrapper<ShowTipVo> queryWrapper);
}

