package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSelectShowDto {
    //页码
    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    //每页数据条数
    @ApiModelProperty(value = "每页数据条数")
    private Integer pageSize;
    //用户id
    @ApiModelProperty(value = "用户id")
    private Long userId;
    //发布时间
    @ApiModelProperty(value = "开始时间  格式（yyyy-MM-dd HH:mm:ss）")
    private String startTime;
    @ApiModelProperty(value = "结束时间  格式（yyyy-MM-dd HH:mm:ss）")
    private String endTime;
    //升序还是倒叙
    @ApiModelProperty(value = "1 从最近动态开始排序， 0从最早动态开始排列")
    private String timeOrder;

}
