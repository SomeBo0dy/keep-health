package team.keephealth.yjj.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.dto.article.MArticleKeyNumDto;
import team.keephealth.yjj.domain.dto.article.UArticleKeyNumDto;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ArticleNumberService extends IService<Article> {

    // 获得文章总数
    ResultVo Number();

    // 总数但有关键词
    ResultVo KeyNumber(String keyword);

    // 获取作者文章总数
    ResultVo UNumber(Long id);

    // 作者文章总数有关键词
    ResultVo KeyUNumber(UArticleKeyNumDto dto);

    // 获取用户文章总数
    ResultVo ANumber(int state);

    // 个人文章总数关键词
    ResultVo AKeyNum(MArticleKeyNumDto dto);
}
