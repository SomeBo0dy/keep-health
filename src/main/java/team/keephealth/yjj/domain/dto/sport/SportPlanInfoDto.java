package team.keephealth.yjj.domain.dto.sport;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanInfoDto {

    // 需更改时添加的id
    @ApiModelProperty(value = "需更改时添加的id")
    private Long planId;

    // 训练目标 1：全身减脂 2：瘦腰 3：瘦胳膊 4：瘦腿
    // 参数错则不改
    @ApiModelProperty(value = "训练目标", notes = "如1：全身减脂 2：瘦腰etc...详情访问 '获取目标列表' \n" +
            "    // 参数错则不改")
    private int aim;
    // 标题
    @ApiModelProperty(value = "标题")
    private String title;
    // 简介
    @ApiModelProperty(value = "简介")
    private String words;
    // 共消耗kcal,不改返回-1
    @ApiModelProperty(value = "共消耗kcal,自动更改返回-1")
    private double kcal;
    // 各阶段详细信息
    @ApiModelProperty(value = "各阶段详细信息")
    private List<SportPlanDInfoDto> list;
}
