package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MyShowLifeDto {
    @ApiModelProperty(value = "页码")
    Integer pageNum;
    @ApiModelProperty(value = "每页数据条数")
    Integer pageSize;
    //升序还是倒叙
    @ApiModelProperty(value = "1 从最近动态开始排序， 0从最早动态开始排列")
    private String timeOrder;
}
