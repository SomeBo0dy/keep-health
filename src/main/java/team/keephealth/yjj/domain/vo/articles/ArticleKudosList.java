package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleKudosList {

    // 点赞信息列
    // 点赞用户的id
    @ApiModelProperty(name = "点赞用户的id")
    private Long id;

    // 点赞用户的昵称
    @ApiModelProperty(name = "点赞用户的昵称")
    private String name;

    // 点赞用户的头像
    @ApiModelProperty(name = "点赞用户的头像")
    private String photo;

    @ApiModelProperty(name = "点赞时间")
    private Date time;
}
