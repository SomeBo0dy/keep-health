package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyReceiveComment {

    // 评论的id
    @ApiModelProperty(name = "评论的id")
    private Long id;
    // 给我评论的账号昵称
    @ApiModelProperty(name = "给我评论的账号昵称")
    private String account;
    // 给我评论的账号id
    @ApiModelProperty(name = "给我评论的账号id")
    private Long uid;
    // 给我评论的内容
    @ApiModelProperty(name = "给我评论的内容")
    private String words;
    // 该评论所属的文章id
    @ApiModelProperty(name = "该评论所属的文章id")
    private Long aid;
    // 该评论所属的文章标题
    @ApiModelProperty(name = "该评论所属的文章标题")
    private String title;
    // 该评论回复我的评论（内容），若不算回复我的评论的评论， 而是给文章的评论，则为null
    @ApiModelProperty(name = "该评论回复我的评论（内容）", notes = "若不是回复我的评论的评论， 而是给文章的评论，则为null")
    private String reply;
    // 该评论发表时间
    @ApiModelProperty(name = "该评论发表时间")
    private Date time;
}
