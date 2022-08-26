package team.keephealth.yjj.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentQueryDto {

    // 文章，或者评论的id
    @ApiModelProperty(value = "文章，或者评论的id")
    private Long id;
    // 每页数据量
    @ApiModelProperty(value = "每页数据量")
    private int pageSize;
    // 当前页数
    @ApiModelProperty(value = "当前页数")
    private int currentPage;
}
