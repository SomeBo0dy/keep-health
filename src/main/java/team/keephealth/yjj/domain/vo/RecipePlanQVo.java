package team.keephealth.yjj.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RecipePlanQVo extends RecipePlanInfoDto {

    @ApiModelProperty(name = "是否选择")
    private boolean choose;

    public RecipePlanQVo(RecipePlan plan){
        this.setPlanId(plan.getId());
        this.setTitle(plan.getTitle());
        this.setMsg(plan.getMsg());
        this.setKcal(plan.getKcal());
        this.setCarbs(plan.getCarbsWt());;
        this.setPro(plan.getProWt());
        this.setFat(plan.getFatWt());
    }
}
