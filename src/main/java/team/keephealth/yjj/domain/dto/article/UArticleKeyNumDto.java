package team.keephealth.yjj.domain.dto.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UArticleKeyNumDto {

    // 用户id
    @ApiModelProperty(value = "用户id")
    private Long id;
    // 关键词
    @ApiModelProperty(value = "关键词")
    private String keyword;
}
