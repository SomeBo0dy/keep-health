package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewKudosList {

    // 给我点赞用户的昵称
    @ApiModelProperty(name = "给我点赞用户的昵称")
    private String name;

    // 给我点赞用户的id
    @ApiModelProperty(name = "给我点赞用户的id")
    private Long uid;

    // 我被点赞的文章标题
    @ApiModelProperty(name = "我被点赞的文章标题")
    private String title;

    // 我被点赞的文章id
    @ApiModelProperty(name = "我被点赞的文章id")
    private Long aid;

    // 我被点赞的评论内容
    @ApiModelProperty(name = "我被点赞的评论内容")
    private String words;

    // 我被点赞的评论id
    @ApiModelProperty(name = "我被点赞的评论id")
    private Long cid;

    // 文章被点赞的时间
    @ApiModelProperty(name = "文章被点赞的时间")
    private Date atime;

    // 评论被点赞的时间
    @ApiModelProperty(name = "评论被点赞的时间")
    private Date ctime;
}
