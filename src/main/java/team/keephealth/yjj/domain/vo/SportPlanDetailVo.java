package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanDetailVo {

    // 第state阶段
    @ApiModelProperty(name = "第state阶段")
    private int state;
    // 阶段名称
    @ApiModelProperty(name = "阶段名称")
    private String name;
    // 阶段总能量消耗
    @ApiModelProperty(name = "阶段总能量消耗")
    private double tol;
    // 阶段说明
    @ApiModelProperty(name = "阶段说明")
    private String msg;
    // 阶段注意事项
    @ApiModelProperty(name = "阶段注意事项")
    private String tips;
    // 阶段总天数
    @ApiModelProperty(name = "阶段总天数")
    private int days;
    // 该阶段每轮运动天数
    @ApiModelProperty(name = "该阶段每轮运动天数")
    private int exercise;
    // 该阶段每轮休息天数
    @ApiModelProperty(name = "该阶段每轮休息天数")
    private int relax;
    // 该阶段运动
    @ApiModelProperty(name = "该阶段运动")
    private String sport;
    // 每次运动时间（minutes
    @ApiModelProperty(name = "每次运动时间（minutes")
    private int time;
    // 每次运动总消耗能量
    @ApiModelProperty(name = "每次运动总消耗能量")
    private double energy;
    // 图片
    @ApiModelProperty(name = "图片")
    private String pict;

}
