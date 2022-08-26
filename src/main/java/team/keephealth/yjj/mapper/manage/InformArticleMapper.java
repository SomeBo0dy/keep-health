package team.keephealth.yjj.mapper.manage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.keephealth.yjj.domain.entity.manage.InformArticle;
import team.keephealth.yjj.domain.vo.ArticleVioList;

import java.util.List;

@Mapper
public interface InformArticleMapper extends BaseMapper<InformArticle> {

    @Select("SELECT DISTINCT m.article_id as aid, a.title, a.account_id as uid, u.nick_name as name " +
            "FROM adm_article as m " +
            "LEFT JOIN sys_article as a " +
            "ON(m.article_id = a.id) " +
            "LEFT JOIN sys_user as u " +
            "ON(a.account_id = u.id) " +
            "WHERE m.deal = #{deal}")
    List<ArticleVioList> getList(@Param("deal") int deal);

    @Select("SELECT DISTINCT type " +
            "FROM adm_article " +
            "WHERE article_id = #{articleId} ")
    List<Integer> getType(@Param("articleId") Long articleId);

    @Select("UPDATE adm_article " +
            "SET adm_article.deal = 1 " +
            "WHERE article_id = #{articleId} AND adm_article.deal = 0 ")
    void haveDeal(@Param("articleId") Long articleId);

}
