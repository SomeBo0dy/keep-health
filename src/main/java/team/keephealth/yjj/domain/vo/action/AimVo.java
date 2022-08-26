package team.keephealth.yjj.domain.vo.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AimVo {

    // 用户id
    //private Long accountId;
    // 目标方向(未定义记-1)
    @ApiModelProperty(name = "目标方向(未定义记0)")
    private int aim;

    // 今日允许吸收热量
    @ApiModelProperty(name = "今日允许吸收热量")
    private double absorbKcal;
    // 今日计划消耗热量
    @ApiModelProperty(name = "今日计划消耗热量")
    private double expendKcal;

    // 今日已吸收热量
    @ApiModelProperty(name = "今日已吸收热量")
    private double absorbed;
    // 今日已消耗热量
    @ApiModelProperty(name = "今日已消耗热量")
    private double expended;
}
