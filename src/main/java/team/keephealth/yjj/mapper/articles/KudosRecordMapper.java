package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.KudosRecord;
import team.keephealth.yjj.domain.vo.articles.NewKudosList;

import java.util.List;

@Mapper
@Repository
public interface KudosRecordMapper extends BaseMapper<KudosRecord> {

    @Select("SELECT u.nick_name as name, u.id as uid,  " +
            "a.title, ak.article_id as aid, " +
            "c.words, ck.comment_id as cid, " +
            "ak.add_time as atime, ck.add_time as ctime  " +
            "FROM msg_kudos as m " +
            "LEFT JOIN sys_kudos as ak " +
            "ON(m.article_kudos = ak.id) " +
            "LEFT JOIN sys_com_kudos as ck " +
            "ON(m.comment_kudos = ck.id) " +
            "LEFT JOIN sys_article as a " +
            "ON(ak.article_id = a.id) " +
            "LEFT JOIN sys_comment as c " +
            "ON(ck.comment_id = c.id) " +
            "LEFT JOIN sys_user as u " +
            "ON(ak.visitor_id = u.id OR ck.visiter_id = u.id) " +
            "WHERE m.account_id = #{accountId} AND m.msg_read = 0 " +
            "ORDER BY m.id DESC")
    List<NewKudosList> newKudos(@Param("accountId") Long accountId);

    @Select("UPDATE msg_kudos " +
            "SET msg_kudos.msg_read = 1 " +
            "WHERE account_id = #{accountId} AND msg_kudos.msg_read = 0 ")
    void updateRead(@Param("accountId") Long accountId);

    @Select("SELECT u.nick_name as name, u.id as uid,  " +
            "a.title, ak.article_id as aid, " +
            "c.words, ck.comment_id as cid, " +
            "ak.add_time as atime, ck.add_time as ctime  " +
            "FROM msg_kudos as m " +
            "LEFT JOIN sys_kudos as ak " +
            "ON(m.article_kudos = ak.id) " +
            "LEFT JOIN sys_com_kudos as ck " +
            "ON(m.comment_kudos = ck.id) " +
            "LEFT JOIN sys_article as a " +
            "ON(ak.article_id = a.id) " +
            "LEFT JOIN sys_comment as c " +
            "ON(ck.comment_id = c.id) " +
            "LEFT JOIN sys_user as u " +
            "ON(ak.visitor_id = u.id OR ck.visiter_id = u.id) " +
            "WHERE m.account_id = #{accountId} " +
            "ORDER BY m.id DESC")
    List<NewKudosList> allKudos(@Param("accountId") Long accountId);
}
