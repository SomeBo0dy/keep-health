package team.keephealth.yjj.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.dto.article.ArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.ArticleQueryDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ArticleQueryService extends IService<Article> {

    // 获取该作者全部文章
    ResultVo queryByUser(ArticleQueryDto dto);

    // 按无作者排序获取
    ResultVo queryByAll(ArticleQueryDto dto);

    // 关键词查找
    ResultVo queryByKeyword(ArticleQKeyDto dto);

    // 单人下关键词
    ResultVo queryByUKey(ArticleQKeyDto dto);

}
