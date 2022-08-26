package team.keephealth.yjj.domain.dto.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportPunchDto {

    // 选择打卡的计划，打卡的计划id，计划打卡和自定义打卡二选一，如果都填了也只会记录计划打卡
    @ApiModelProperty(value = "选择打卡的计划id", notes = "计划打卡和自定义打卡二选一，如果都填了也只会记录计划打卡")
    private Long planId;

    // 自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败
    // 运动类型(查无运动默认为其他类）
    @ApiModelProperty(value = "运动类型(查无运动默认为其他类）",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private Long sport;
    // 运动时长(分钟)
    @ApiModelProperty(value = "运动时长(分钟)",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private Integer sportTime;
    // 运动消耗能量（也是千卡）
    @ApiModelProperty(value = "运动消耗能量（千卡）",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private Double energy;
    // 运动图片
    @ApiModelProperty(value = "运动图片",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private String pict;

}
