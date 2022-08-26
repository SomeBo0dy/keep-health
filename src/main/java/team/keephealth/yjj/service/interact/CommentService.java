package team.keephealth.yjj.service.interact;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.dto.CommentInfoDto;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface CommentService extends IService<Comment> {

    // 添加评论
    ResultVo addComment(CommentInfoDto dto);

    // 回复评论
    ResultVo replyComment(CommentInfoDto dto);

    // 删除评论
    ResultVo deleteComment(Long commentId);

    // 举报评论
    ResultVo complaintComment(InformDto dto);

    // 根据评论id定位评论所在的文章及评论和所以回复该评论的信息
    ResultVo visitComment(Long commentId);
}
