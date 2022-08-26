package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentVo {

    // 该评论信息的id
    @ApiModelProperty(name = "该评论信息的id")
    private Long id;
    // 评论的内容
    @ApiModelProperty(name = "评论的内容")
    private String words;
    // 评论所属文章的id
    @ApiModelProperty(name = "评论所属文章的id")
    private Long aid;
    // 评论所属文章的标题
    @ApiModelProperty(name = "评论所属文章的标题")
    private String title;
    // 回复的评论内容
    @ApiModelProperty(name = "回复的评论内容")
    private String reply;
    // 回复评论的发表用户名称
    @ApiModelProperty(name = "回复评论的发表用户名称")
    private String rac;
    // 回复评论的发表用户id
    @ApiModelProperty(name = "回复评论的发表用户id")
    private Long rid;
    // 发表的时间
    @ApiModelProperty(name = "发表的时间")
    private Date time;
}
