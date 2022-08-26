package team.keephealth.yjj.domain.dto.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PunchDeleteDto {

    // 打卡记录删除(有传输的id都会删除

    // 计划记录
/*    private Long sportPlan;
    private Long recipePlan;*/
    @ApiModelProperty(value = "1:运动， 2：减肥餐")
    private Integer plan;   // 1:运动， 2：减肥餐

    // 是减肥餐的话是哪一餐（未定义：0， 早餐：1， 午餐：2， 晚餐：3 ）信息错误不予删除
    @ApiModelProperty(value = "是减肥餐的话是哪一餐（早餐：1， 午餐：2， 晚餐：3 ）")
    private int cate;

    // 自定义打卡
    @ApiModelProperty(value = "运动自定义打卡id")
    private Long sportCP;
    @ApiModelProperty(value = "餐饮自定义打卡id")
    private Long recipeCP;
    // 日期 yyyy-MM-dd
    /*@ApiModelProperty(value = "删除的日期 yyyy-MM-dd")
    private String day;*/
}
