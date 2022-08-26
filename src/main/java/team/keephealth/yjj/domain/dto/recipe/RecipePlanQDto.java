package team.keephealth.yjj.domain.dto.recipe;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePlanQDto {

    // 作者id
    @ApiModelProperty(value = "作者id，获取作者所属食谱用")
    private Long accountId;
    // 总卡路里下限
    @ApiModelProperty(value = "总卡路里下限")
    private double down;
    // 总卡路里上限
    @ApiModelProperty(value = "总卡路里上限")
    private double up;
}
