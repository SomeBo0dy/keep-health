package team.keephealth.yjj.domain.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleCheckQuery {

    @ApiModelProperty(value = "文章id")
    private Long articleId;
    // 每页数据量
    @ApiModelProperty(value = "每页数据量")
    private int pageSize;
    // 当前页数
    @ApiModelProperty(value = "当前页数")
    private int currentPage;

}
