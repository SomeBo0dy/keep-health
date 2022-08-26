package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TarProfileDto {
    //体重（kg/公斤）
    @ApiModelProperty(value = "目标体重（kg/公斤）（1位小数）")
    private Double targetWeight;

    @ApiModelProperty(value = "希望的减肥时间")
    private Long targetFinishTime;
}
