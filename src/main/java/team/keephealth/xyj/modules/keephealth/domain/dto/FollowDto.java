package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FollowDto {
    @ApiModelProperty(value = "用户id")
    private Long userId;
}
