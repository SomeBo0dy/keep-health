package team.keephealth.yjj.mapper.articles;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.articles.Article;

@Mapper
@Repository
public interface ArticleNumberMapper extends BaseMapper<Article> {

    // 获取总数据量
    //Long getDataNumber();

    // 获取用户文章数量
    Long getUNumber(Long id);

    // 当前用户文章数量
    Long getANumber(Long id, int state);
}
