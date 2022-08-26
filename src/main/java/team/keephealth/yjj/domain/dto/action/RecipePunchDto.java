package team.keephealth.yjj.domain.dto.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipePunchDto {

    // 选择打卡的计划，打卡的计划id
    @ApiModelProperty(value = "选择打卡的计划id", notes = "计划打卡和自定义打卡二选一，如果都填了也只会记录计划打卡")
    private Long planId;

    // 自定义打卡
    // 餐饮类别（ 早餐：1， 午餐：2， 晚餐：3 ）
    @ApiModelProperty(value = "餐饮类别（ 早餐：1， 午餐：2， 晚餐：3 ）",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败", required = true)
    private int cate;
    // 选择的食物
    @ApiModelProperty(value = "选择的食物",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private String meal;
    // 图片识别，待补充
    // 或者选择上传图片识别
    @ApiModelProperty(value = "图片",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private String pict;
    // 卡路里量(可不填使用记录的卡路里量
    @ApiModelProperty(value = "卡路里量(可不填使用记录的卡路里量",
            notes = "此为自定义打卡 若有缺漏信息需确保已有信息充分可以提供补全，否则记录失败")
    private Double kcal;
}
