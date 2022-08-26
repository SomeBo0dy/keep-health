package team.keephealth.yjj.domain.dto.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleInfoDto {

    //id
    @ApiModelProperty(value = "文章的id")
    private Long id;
    @ApiModelProperty(value = "文章的标题", required = true)
    private String title;
    @ApiModelProperty(value = "文章的简介")
    private String brief;
    @ApiModelProperty(value = "文章的正文内容")
    private String words;
    @ApiModelProperty(value = "文章的配图", required = true)
    private String pict;
}
