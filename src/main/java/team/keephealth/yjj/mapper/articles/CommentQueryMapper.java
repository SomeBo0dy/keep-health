package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.vo.articles.CommentVo;
import team.keephealth.yjj.domain.vo.articles.MyCommentVo;
import team.keephealth.yjj.domain.vo.articles.MyReceiveComment;
import team.keephealth.yjj.domain.vo.articles.ReplyVo;

import java.util.List;

@Mapper
public interface CommentQueryMapper extends BaseMapper<Comment> {

    @Select("SELECT id, account, account_id as aid, words, kudos_number, add_time as time " +
            "FROM sys_comment " +
            "WHERE article_id = #{articleId} AND reply IS NULL " +
            "ORDER BY kudos_number AND add_time " +
            "DESC " +
            "LIMIT #{left}, #{right}")
    List<CommentVo> queryByHeat(@Param("articleId") Long articleId,
                                @Param("left") int left, @Param("right") int right);

    @Select("SELECT id, account, account_id as aid, words, kudos_number, add_time as time " +
            "FROM sys_comment " +
            "WHERE article_id = #{articleId} AND reply IS NULL " +
            "ORDER BY add_time " +
            "DESC " +
            "LIMIT #{left}, #{right}")
    List<CommentVo> queryByTime(@Param("articleId") Long articleId,
                                @Param("left") int left, @Param("right") int right);

    @Select("SELECT u.nick_name as name, u.id as uid, r.words, r.kudos_number as kudos, r.add_time as time " +
            "FROM sys_comment as c " +
            "JOIN sys_comment as r " +
            "ON(r.reply = c.id) " +
            "JOIN sys_user as u " +
            "ON(r.account_id = u.id) " +
            "WHERE c.id = #{commentId} " +
            "ORDER BY r.kudos_number " +
            "DESC " +
            "LIMIT #{left}, #{right}")
    List<ReplyVo> queryReply(@Param("commentId") Long commentId,
                             @Param("left") int left, @Param("right") int right);

    @Select("SELECT c.id, c.words, c.article_id as aid, a.title, b.words as reply, b.account as rac, b.account_id as rid, c.add_time as time " +
            "FROM sys_comment as c " +
            "LEFT JOIN sys_article as a " +
            "ON(a.id = c.article_id) " +
            "LEFT JOIN sys_comment as b " +
            "ON(b.id = c.reply) " +
            "WHERE c.account_id = #{accountId} " +
            "ORDER BY c.id DESC")
    List<MyCommentVo> mySend(@Param("accountId") Long accountId);

    @Select("SELECT c.id, c.account, c.account_id as uid, c.words, " +
            "c.article_id as aid, a.title, b.words as reply,  " +
            "c.add_time as time " +
            "FROM msg_comment as m " +
            "LEFT JOIN sys_comment as c " +
            "ON(m.article_comment = c.id OR m.comment_reply = c.id) " +
            "LEFT JOIN sys_article as a " +
            "ON(c.article_id = a.id) " +
            "LEFT JOIN sys_comment as b  " +
            "ON(c.reply = b.id) " +
            "WHERE m.account_id = #{accountId} " +
            "ORDER BY m.id DESC")
    List<MyReceiveComment> myReceive(@Param("accountId") Long accountId);

    @Select("SELECT c.id, c.account, c.account_id as uid, c.words, " +
            "c.article_id as aid, a.title, b.words as reply,  " +
            "c.add_time as time " +
            "FROM msg_comment as m " +
            "LEFT JOIN sys_comment as c " +
            "ON(m.article_comment = c.id OR m.comment_reply = c.id) " +
            "LEFT JOIN sys_article as a " +
            "ON(c.article_id = a.id) " +
            "LEFT JOIN sys_comment as b  " +
            "ON(c.reply = b.id) " +
            "WHERE m.account_id = #{accountId} AND m.msg_read = 0 " +
            "ORDER BY m.id DESC")
    List<MyReceiveComment> myReceiveNew(@Param("accountId") Long accountId);

    @Select("UPDATE msg_comment " +
            "SET msg_comment.msg_read = 1 " +
            "WHERE account_id = #{accountId} AND msg_comment.msg_read = 0 ")
    void updateRead(@Param("accountId") Long accountId);
}
