package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.vo.articles.ArticleKudosList;
import team.keephealth.yjj.domain.vo.articles.MyKudosList;

import java.util.List;

@Mapper
@Repository
public interface KudosMapper extends BaseMapper<Kudos> {

    // 文章收到的赞列表
    @Select("SELECT k.visitor_id as id, u.nick_name as name, u.avatar as photo , k.add_time as time " +
            "FROM sys_kudos as k " +
            "LEFT JOIN sys_user as u " +
            "ON(u.id = k.visitor_id) " +
            "WHERE k.article_id = #{articleId}")
    List<ArticleKudosList> getArticleKudos(@Param("articleId") Long articleId);

    @Select("SELECT k.article_id as aid, a.title, a.account as writer, a.account_id as wid, k.add_time as time " +
            "FROM sys_kudos as k " +
            "LEFT JOIN sys_article as a " +
            "ON(k.article_id = a.id) " +
            "WHERE k.visitor_id = #{visitorId}")
    List<MyKudosList> myArticleKudos(@Param("visitorId") Long visitorId);


}
