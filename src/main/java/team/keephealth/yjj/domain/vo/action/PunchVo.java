package team.keephealth.yjj.domain.vo.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PunchVo {

    // 今日减肥餐是否打卡成功
    @ApiModelProperty(name = "今日减肥餐是否打卡成功")
    private boolean recipe;
    // 今日运动是否打卡成功
    @ApiModelProperty(name = "今日运动是否打卡成功")
    private boolean sport;

    // 时间
    @ApiModelProperty(name = "时间")
    private Date time;
}
