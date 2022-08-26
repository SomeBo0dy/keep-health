package team.keephealth.yjj.domain.vo.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.sport.Sport;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodaySPunch {

    // 今日运动计划是否打卡
    @ApiModelProperty(name = "今日运动计划是否打卡")
    private boolean plan;

    // 计划列表
    @ApiModelProperty(name = "今日运动计划计划列表")
    List<Sport> list;
}
