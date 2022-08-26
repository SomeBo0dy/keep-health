package team.keephealth.yjj.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckDto;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckQuery;
import team.keephealth.yjj.domain.entity.articles.Article;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface ArticleManageService extends IService<Article> {

    // 更改审核信息
    ResultVo<T> updateCheck(ArticleCheckDto dto);

    // 获取待审核的文章数量
    ResultVo<Long> getCheckNum();

    // 获取用户待审核的文章数量
    ResultVo<Long> getUserCNum(Long accountId);

    // 获取用户不通过审核的文章数量
    ResultVo<Long> getUserPNum(Long accountId);

    /**
     * 获取待审核的文章列表，默认文章更改时间正序排序
     * @param query : 分页数据
     * @param isUser : 是否查询单用户所属
     * @return
     */
    ResultVo getCheckList(ArticleCheckQuery query, boolean isUser);

    /**
     * 获取不通过审核的文章列表，默认文章更改时间倒序排序
     * @param query : 分页数据
     * @param isUser : 是否查询单用户所属
     * @return
     */
    ResultVo getPassList(ArticleCheckQuery query, boolean isUser);

    // 获取列表
    ResultVo<T> getList(int check);

}
