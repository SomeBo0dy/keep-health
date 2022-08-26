package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.vo.articles.CommentVo;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT id, account, account_id as aid, words, kudos_number, add_time as time " +
            "FROM sys_comment " +
            "WHERE id = #{commentId} AND reply IS NULL ")
    CommentVo getAComment(@Param("commentId") Long commentId);

}
