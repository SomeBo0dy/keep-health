package team.keephealth.yjj.service.interact;

import team.keephealth.yjj.domain.dto.CommentQueryDto;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface CommentQueryService {

    // 获取评论精选1-3条（有>10赞的评论按最高评论数获取，无则按最新评论获取）
    ResultVo highComment(Long articleId);

    // 获取全部评论 热度排序
    ResultVo queryByHeat(CommentQueryDto dto);

    // 获取全部评论 时间排序
    ResultVo queryByTime(CommentQueryDto dto);

    // 获取某评论回复的全部评论（默认按热度排序）
    ResultVo queryReply(CommentQueryDto dto);

    // 全部评论数量 不包含回复的评论
    ResultVo getNumber(Long articleId);

    // 全部评论数量 包含回复的评论
    ResultVo getNumberA(Long articleId);

    // 获取某评论回复的全部评论数量
    ResultVo getReplyNumber(Long commentId);
}
