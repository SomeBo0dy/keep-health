package team.keephealth.yjj.domain.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanChooseDto {

    // 添加的套餐id
    @ApiModelProperty(value = "添加的套餐id")
    private Long planId;

    // 计划实施天数
    @ApiModelProperty(value = "计划实施天数")
    private int days;
}
