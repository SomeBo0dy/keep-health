package team.keephealth.yjj.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.entity.manage.InformArticle;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ArticleVioService extends IService<InformArticle> {

    // 获取被举报的文章列表
    // 0：未处理， 1：已处理
    ResultVo<T> getAll(int deal);

    // 查看这个文章的所有举报信息
    ResultVo<T> getArticleVio(Long articleId);

    // 处理文章举报
    ResultVo<T> setDeal(DoDeal dto);

}
