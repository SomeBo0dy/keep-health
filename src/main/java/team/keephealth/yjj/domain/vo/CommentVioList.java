package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVioList {

    @ApiModelProperty(value = "被举报的评论id")
    private Long cid;

    @ApiModelProperty(value = "被举报的评论内容")
    private String words;

    @ApiModelProperty(value = "被举报的评论发布者id")
    private Long uid;

    @ApiModelProperty(value = "被举报的评论发布者昵称")
    private String name;
}
