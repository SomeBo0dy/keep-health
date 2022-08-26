package team.keephealth.yjj.domain.dto.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MArticleQKeyDto extends MArticleQueryDto{

    @ApiModelProperty(value = "关键词")
    private String keyword;
}
