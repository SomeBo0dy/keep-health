package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVioList {

    @ApiModelProperty(value = "被举报的文章id")
    private Long aid;

    @ApiModelProperty(value = "被举报的文章标题")
    private String title;

    @ApiModelProperty(value = "被举报的文章作者id")
    private Long uid;

    @ApiModelProperty(value = "被举报的文章作者昵称")
    private String name;

}
