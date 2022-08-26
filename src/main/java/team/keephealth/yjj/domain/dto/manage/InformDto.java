package team.keephealth.yjj.domain.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InformDto {

    // 举报的id
    @ApiModelProperty(value = "举报的id")
    private Long informId;
    // 举报类别
    @ApiModelProperty(value = "举报类别", notes = "详情查看 项目数据 中 举报项目列表")
    private int type;
    // 补充信息
    @ApiModelProperty(value = "补充信息")
    private String message;
}
