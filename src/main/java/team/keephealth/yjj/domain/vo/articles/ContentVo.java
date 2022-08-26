package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentVo {

    // 文章标题
    @ApiModelProperty(name = "文章标题")
    private String title;

    // 文章简介
    @ApiModelProperty(name = "文章简介")
    private String brief;

    // 文章正文
    @ApiModelProperty(name = "文章正文")
    private String words;

    // 文章图片
    @ApiModelProperty(name = "文章图片")
    private String pict;

    // 文章点赞量
    @ApiModelProperty(name = "文章点赞量")
    private int kudos;

    // 作者昵称
    @ApiModelProperty(name = "作者昵称")
    private String name;

    // 作者头像
    @ApiModelProperty(name = "作者头像")
    private String avatar;

    // 作者id
    @ApiModelProperty(name = "作者id")
    private Long uid;

    // 文章发布时间
    @ApiModelProperty(name = "文章发布时间")
    private Date time;
}
