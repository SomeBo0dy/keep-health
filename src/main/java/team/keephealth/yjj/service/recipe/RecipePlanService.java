package team.keephealth.yjj.service.recipe;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface RecipePlanService extends IService<RecipePlan> {

    // 添加智慧食谱
    ResultVo<T> addPlan(RecipePlanInfoDto dto);

    // 更改
    ResultVo<T> updatePlan(RecipePlanInfoDto dto);

    // 删除
    ResultVo<T> deletePlan(Long planId);

    // 查看食谱内容
    ResultVo<T> getPlan(Long planId);
}
