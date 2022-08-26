package team.keephealth.yjj.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.article.ArticleInfoDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ArticleService extends IService<Article> {

    // 添加
    ResultVo<T> addArticle(ArticleInfoDto article);

    // 删除
    ResultVo<T> deleteArticle(Long id);

    // 更改
    ResultVo<T> updateArticle(ArticleInfoDto dto);

}
