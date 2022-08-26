package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.Article;

import java.util.List;

@Mapper
@Repository
public interface ArticleQueryMapper extends BaseMapper<Article> {

    // 略查，不返回具体内容
    // 按作者查询
    List<Article> queryByUser(int kind, int direction, Long id);

    // 全部排序获取
    List<Article> queryByAll(int kind, int direction);

    // 关键词查找
    List<Article> queryByKeyword(String keyword);


}
