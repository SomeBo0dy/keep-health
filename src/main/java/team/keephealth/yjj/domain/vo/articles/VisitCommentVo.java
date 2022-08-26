package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.entity.articles.Comment;
import team.keephealth.yjj.domain.vo.articles.CommentVo;
import team.keephealth.yjj.domain.vo.articles.ContentVo;
import team.keephealth.yjj.domain.vo.articles.ReplyVo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitCommentVo {

    // 文章信息
    @ApiModelProperty(name = "文章信息")
    private ContentVo contentVo;

    // 精选评论信息
    @ApiModelProperty(name = "精选评论信息")
    private List<Comment> comments;

    // 定位评论的母评（所有回复评论所回复的那个评论）
    @ApiModelProperty(name = "定位评论的母评", notes = "所有回复评论所回复的那个评论")
    private CommentVo commentVo;

    // 定位的评论回复信息
    @ApiModelProperty(name = "定位的评论回复信息")
    private List<ReplyVo> reply;
}
