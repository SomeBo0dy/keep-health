package team.keephealth.yjj.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.yjj.domain.entity.manage.InformComment;
import team.keephealth.yjj.domain.vo.CommentVioList;

import java.util.List;

@Mapper
public interface InformCommentMapper extends BaseMapper<InformComment> {

    @Select("SELECT DISTINCT m.comment_id as cid, c.words, c.account_id as uid, u.nick_name as name " +
            "FROM adm_comment as m " +
            "LEFT JOIN sys_comment as c " +
            "ON(m.comment_id = c.id) " +
            "LEFT JOIN sys_user as u " +
            "ON(c.account_id = u.id) " +
            "WHERE m.deal = #{deal}")
    List<CommentVioList> getList(@Param("deal") int deal);

    @Select("SELECT DISTINCT type " +
            "FROM adm_comment " +
            "WHERE comment_id = #{commentId} ")
    List<Integer> getType(@Param("commentId") Long commentId);

    @Select("UPDATE adm_comment " +
            "SET adm_comment.deal = 1 " +
            "WHERE comment_id = #{commentId} AND adm_comment.deal = 0 ")
    void haveDeal(@Param("commentId") Long commentId);
}
