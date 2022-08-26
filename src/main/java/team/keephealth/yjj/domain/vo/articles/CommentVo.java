package team.keephealth.yjj.domain.vo.articles;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    // 评论的id
    @ApiModelProperty(name = "评论的id")
    private Long id;
    // 评论发表人的昵称
    @ApiModelProperty(name = "评论发表人的昵称")
    private String account;
    // 评论发表人的id
    @ApiModelProperty(name = "评论发表人的id")
    private Long aid;
    // 评论内容
    @ApiModelProperty(name = "评论内容")
    private String words;
    // 评论点赞量
    @ApiModelProperty(name = "评论点赞量")
    private int kudos_number;
    // 评论时间
    @ApiModelProperty(name = "评论时间")
    private Date time;
    // 评论的回复数量
    @ApiModelProperty(name = "评论的回复数量")
    private int count;
}
