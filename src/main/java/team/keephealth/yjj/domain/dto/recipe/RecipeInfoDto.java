package team.keephealth.yjj.domain.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInfoDto {

    // 或许也可以做返回信息用

    // 更改时要用的食谱id
    @ApiModelProperty(value = "更改时要用的食谱id")
    private Long recipeId;

    // 食谱名称（若不填则自动匹配默认标题
    @ApiModelProperty(value = "食谱名称", notes = "若不填则自动匹配默认标题")
    private String recipeName;
    // 食谱热量（若传入信息低于0或无传入则数值按记录的食谱计算, 若有填/更新该数值，则以用户输入数值为准
    @ApiModelProperty(value = "食谱热量", notes = "若传入信息低于0或无传入则数值按记录的食谱计算, 若有填/更新该数值，则以用户输入数值为准")
    private double tolKcal;
    // 食谱简介
    @ApiModelProperty(value = "食谱简介")
    private String words;

    // 早餐内容(食物名，如包子，沙拉）（或许可以读取食物库的信息填写？
    @ApiModelProperty(value = "早餐内容(食物名，如包子，沙拉）", notes = "或许可以读取食物库的信息填写")
    private String breakfast;
    // 早餐图片（可不填，若该餐在食物库有记录，会根据记录补全信息，其他同类信息同
    @ApiModelProperty(value = "早餐图片", notes = "可不填，若该餐在食物库有记录，会根据记录补全信息，其他同类信息同")
    private String bfPict;
    // 早餐热量
    @ApiModelProperty(value = "早餐热量")
    private double bfKcal;
    // 早餐介绍
    @ApiModelProperty(value = "早餐介绍")
    private String bfWords;
    // 午餐内容
    @ApiModelProperty(value = "午餐内容")
    private String lunch;
    // 午餐图片
    @ApiModelProperty(value = "午餐图片")
    private String luPict;
    // 午餐热量
    @ApiModelProperty(value = "午餐热量")
    private double luKcal;
    // 午餐介绍
    @ApiModelProperty(value = "午餐介绍")
    private String luWords;
    // 晚餐内容
    @ApiModelProperty(value = "晚餐内容")
    private String dinner;
    // 晚餐图片
    @ApiModelProperty(value = "晚餐图片")
    private String dnPict;
    // 晚餐热量
    @ApiModelProperty(value = "晚餐热量")
    private double dnKcal;
    // 晚餐介绍
    @ApiModelProperty(value = "晚餐介绍")
    private String dnWords;

    // 计划开始时间，直接传递年月日即可yyyy-mm-dd
    @ApiModelProperty(value = "计划开始时间，直接传递年月日即可yyyy-mm-dd")
    private String beginTime;
    // 计划结束时间
    @ApiModelProperty(value = "计划结束时间")
    private String endTime;
}
