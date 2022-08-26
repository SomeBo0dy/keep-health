package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShowLikeDto {

    @ApiModelProperty(value = "动态id")
    private Long showId;
}
