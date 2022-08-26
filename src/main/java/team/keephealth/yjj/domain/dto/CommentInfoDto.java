package team.keephealth.yjj.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentInfoDto {

    // 评论所属的文章id
    @ApiModelProperty(value = "评论所属的文章id")
    private Long articleId;
    // 评论内容
    @ApiModelProperty(value = "评论内容")
    private String words;
    // 回复的评论id
    @ApiModelProperty(value = "回复的评论id")
    private Long reply;

}
