package team.keephealth.yjj.domain.vo.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayPunch {

    // 今日减肥餐是否打卡成功
    @ApiModelProperty(name = "今日减肥餐是否打卡成功")
    private boolean recipe;
    // 今日运动是否打卡成功
    @ApiModelProperty(name = "今日运动是否打卡成功")
    private boolean sport;

    // 早中晚餐是否已打卡 0：未， 1：已
    @ApiModelProperty(name = "早中晚餐是否已打卡")
    private boolean breakfast;
    private boolean lunch;
    private boolean dinner;

    // 今日运动计划是否打卡
    @ApiModelProperty(name = "今日运动计划是否打卡")
    private boolean plan;
}
