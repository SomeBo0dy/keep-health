package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.Content;
import team.keephealth.yjj.domain.vo.articles.ContentVo;

@Mapper
@Repository
public interface ContentMapper extends BaseMapper<Content> {

    @Select("SELECT a.title, a.brief, c.words, c.pict, a.kudos_number as kudos, u.nick_name as name, u.avatar, u.id as uid, a.update_time as time " +
            "FROM sys_article as a " +
            "JOIN sys_content as c " +
            "ON(a.content_id = c.id) " +
            "JOIN sys_user as u " +
            "ON(a.account_id = u.id) " +
            "WHERE a.id = #{articleId}")
    ContentVo contentDetail(@Param("articleId") Long articleId);

}
