package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyVo {

    // 回复的用户昵称
    @ApiModelProperty(name = "回复的用户昵称")
    private String name;

    // 回复的用户id
    @ApiModelProperty(name = "回复的用户id")
    private Long uid;

    // 回复的评论内容
    @ApiModelProperty(name = "回复的评论内容")
    private String words;

    // 回复的评论点赞量
    @ApiModelProperty(name = "回复的评论点赞量")
    private int kudos;

    // 回复的时间
    @ApiModelProperty(name = "回复的时间")
    private Date time;
}
