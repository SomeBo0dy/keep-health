package team.keephealth.yjj.domain.dto.sport;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanQDto {

    // 作者id
    @ApiModelProperty(value = "作者id")
    private Long accountId;
    // 训练目标(0全部, 数据错误直接按全部获取
    @ApiModelProperty(value = "训练目标", notes = "0全部, 数据错误直接按全部获取")
    private int aim;
    // 总卡路里下限
    @ApiModelProperty(value = "总卡路里下限")
    private double down;
    // 总卡路里上限
    @ApiModelProperty(value = "总卡路里上限")
    private double up;

}
