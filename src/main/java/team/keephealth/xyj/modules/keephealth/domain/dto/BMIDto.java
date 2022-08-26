package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BMIDto {
    @ApiModelProperty(value = "体重kg")
    private Double weight;
    @ApiModelProperty(value = "身高cm")
    private Double height;
}
