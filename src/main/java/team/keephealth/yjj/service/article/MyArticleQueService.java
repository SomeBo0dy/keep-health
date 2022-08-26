package team.keephealth.yjj.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.dto.article.MArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.MArticleQueryDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface MyArticleQueService extends IService<Article> {

    // 我的文章列表总览
    ResultVo queryMAAll(MArticleQueryDto dto);

    // 我的文章列表总览by关键词
    ResultVo queryMAKey(MArticleQKeyDto dto);
}
