package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanQVo {

    // 运动计划的id
    @ApiModelProperty(name = "运动计划的id")
    private Long id;
    // 训练目标
    @ApiModelProperty(name = "训练目标")
    private int aim;
    // 标题
    @ApiModelProperty(name = "标题")
    private String title;
    // 简介
    @ApiModelProperty(name = "简介")
    private String words;
    // 共消耗kcal
    @ApiModelProperty(name = "共消耗kcal")
    private double kcal;

    // 图片
    @ApiModelProperty(name = "图片")
    private String pict;
    // 是否选择
    @ApiModelProperty(name = "是否选择")
    private boolean choose;

}
