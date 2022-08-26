package team.keephealth.yjj.domain.dto.sport;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanDInfoDto {

    // 需更改时添加的id
    @ApiModelProperty(value = "需更改时添加的id")
    private Long planId;

    // 计划阶段,若无默认按list顺序排列
    // 修改时需传回正确state参数，否则不做修改，若需增加删除阶段使用阶段的接口
    @ApiModelProperty(value = "计划阶段", notes = "若无默认按list顺序排列\n" +
            "    // 修改时需传回正确state参数，否则不做修改，若需增加删除阶段使用阶段的接口")
    private int state;
    // 阶段名称
    @ApiModelProperty(value = "阶段名称")
    private String name;
    // 阶段预计消耗能量
    @ApiModelProperty(value = "阶段预计消耗能量")
    private double tol;
    // 阶段说明
    @ApiModelProperty(value = "阶段说明")
    private String msg;
    // 阶段注意事项
    @ApiModelProperty(value = "阶段注意事项")
    private String tips;
    // 该阶段共执行天数
    @ApiModelProperty(value = "该阶段共执行天数")
    private int days;
    // 每轮锻炼持续天数
    @ApiModelProperty(value = "每轮锻炼持续天数")
    private int exercise;
    // 每轮锻炼后休息天数
    @ApiModelProperty(value = "每轮锻炼后休息天数")
    private int relax;
    // 该阶段运动类型
    @ApiModelProperty(value = "该阶段运动类型")
    private int sport;
    // 每次运动时长, < 0或 >720则默认改为0
    @ApiModelProperty(value = "每次运动时长", notes = "< 0或 >720则默认改为0")
    private int time;
    // 每次运动消耗能量
    // 能量kcal一类变量可以选择不填，若没填则传回数值-1，会根据记录的自动计算填补，若无传回或传回0会记录为0（或许会有什么运动消耗量为0？
    // 更改时同，如果不该则传回-1
    @ApiModelProperty(value = "每次运动消耗能量", notes = "能量kcal一类变量可以选择不填，若没填则传回数值-1，会根据记录的自动计算填补，若无传回或传回0会记录为0（或许会有什么运动消耗量为0？\n" +
            "    // 更改时同，如果不该则传回-1")
    private double energy;
    // 配图
    @ApiModelProperty(value = "配图")
    private String pict;

}
