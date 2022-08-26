package team.keephealth.yjj.domain.vo.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayRPunch {

    // 早中晚餐是否已打卡 0：未， 1：已
    @ApiModelProperty(name = "早餐是否已打卡")
    private boolean breakfast;
    @ApiModelProperty(name = "午餐是否已打卡")
    private boolean lunch;
    @ApiModelProperty(name = "晚餐是否已打卡")
    private boolean dinner;

    // 今日的减肥餐计划
    @ApiModelProperty(name = "今日的减肥餐计划")
    List<RecipeInfoDto> list;
}
