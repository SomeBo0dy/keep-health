package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyKudosList {

    // 点赞文章的id
    @ApiModelProperty(name = "点赞文章的id")
    private Long aid;

    // 我点赞的文章的标题
    @ApiModelProperty(name = "我点赞的文章的标题")
    private String title;

    // 该文章的作者
    @ApiModelProperty(name = "该文章的作者")
    private String writer;

    // 该文章的作者id
    @ApiModelProperty(name = "该文章的作者id")
    private Long wid;

    // 点赞时间
    @ApiModelProperty(name = "点赞时间")
    private Date time;
}
