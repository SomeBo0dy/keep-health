package team.keephealth.yjj.domain.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleCheckDto {

    // 审核信息更改
    @ApiModelProperty(value = "文章id")
    private Long articleId;
    @ApiModelProperty(value = "更改审核状态", notes = " 0未审核， 1通过， 2不通过")
    private int check;
    @ApiModelProperty(value = "更改审核评语")
    private String opinion;
}
