package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetTipPageDto {
    //页码
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    //每页数据条数
    @ApiModelProperty(value = "每页数据条数")
    private Integer pageSize;
    @ApiModelProperty(value = "动态id")
    private Long kslId;

}
