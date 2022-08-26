package team.keephealth.yjj.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckInfoDto {

    // 文章id
    @ApiModelProperty(value = "文章id")
    private Long id;

    // 审核状态
    @ApiModelProperty(value = "审核状态")
    private int check;

    // 审核评论
    @ApiModelProperty(value = "审核评论")
    private String opinion;

}
