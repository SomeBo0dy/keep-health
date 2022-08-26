package team.keephealth.yjj.service.recipe;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanQDto;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface RecipePlanQueryService extends IService<RecipePlan> {

    // 获取全部智慧食谱
    ResultVo<T> queryAll(RecipePlanQDto dto);
    // 获取该作者全部智慧食谱
    ResultVo<T> queryByAc(RecipePlanQDto dto);

}
