package team.keephealth.yjj.mapper.articles;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import team.keephealth.yjj.domain.entity.articles.Article;

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article>{

    // 根据账号查找
    Article selectByAccount(String account);

    // 更改审核状态
    int updateCheck(Long id, int check);

    // 更改评语
    int updateOpinion(Long id, String opinion);

    // 获取最后一个数据id
    int selectLast(Long id);

    // 获取章节id
    Long getContentId(Long id);

    // 获取userId
    Long getUserId(Long id);
}
