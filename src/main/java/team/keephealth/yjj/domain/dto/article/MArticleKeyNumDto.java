package team.keephealth.yjj.domain.dto.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MArticleKeyNumDto {

    // 文章状态
    @ApiModelProperty(value = "文章状态")
    private int state;
    @ApiModelProperty(value = "关键词")
    private String keyword;
}
