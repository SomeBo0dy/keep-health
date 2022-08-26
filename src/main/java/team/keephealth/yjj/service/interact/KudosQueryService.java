package team.keephealth.yjj.service.interact;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface KudosQueryService extends IService<Kudos> {

    // 不做分页
    // 文章收到的赞
    ResultVo getArticleKudos(Long articleId);

    // 用户的点赞总赞数(文章）
    ResultVo getUserKNum(Long accountId);

    // 我的点赞总赞数(文章）
    ResultVo getMyKNum();

    // 我的点赞列表(文章）
    ResultVo getMyKList();

    // 我收到的赞总数（文章）
    ResultVo getMyANum();

    // 文章的总赞数
    // 写的时候忘了文章实体有总赞数了，或许有别的用？
    ResultVo getArticleKNum(Long articleId);
}
