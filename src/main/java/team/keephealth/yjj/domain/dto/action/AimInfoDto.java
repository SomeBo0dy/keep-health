package team.keephealth.yjj.domain.dto.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AimInfoDto {

    // 目标方向(未定义记-1)
    @ApiModelProperty(value = "目标方向（详情获取目标列表")
    private int aim;

    // 今日允许吸收热量
    @ApiModelProperty(value = "今日允许吸收热量")
    private double absorbKcal;
    // 今日计划消耗热量
    @ApiModelProperty(value = "今日计划消耗热量")
    private double expendKcal;
}
