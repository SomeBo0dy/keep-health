package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AvatarDto {
    //头像cos路径
    @ApiModelProperty(value = "头像cos路径")
    private String avatarUrl;
}
