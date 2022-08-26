package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ShowLifeDto {

    @ApiModelProperty(value = "动态内容")
    private String text;

}
