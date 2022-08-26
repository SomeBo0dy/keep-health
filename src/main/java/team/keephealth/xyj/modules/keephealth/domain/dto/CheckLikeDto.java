package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckLikeDto {
    @ApiModelProperty(value = "动态id")
    private Long kslId;
}
