package team.keephealth.xyj.modules.keephealth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowComment;


/**
 * 评论表(ShowComment)表数据库访问层
 *
 * @author xyj
 * @since 2022-08-01 16:30:13
 */
@Mapper
public interface ShowCommentMapper extends BaseMapper<ShowComment> {

}

