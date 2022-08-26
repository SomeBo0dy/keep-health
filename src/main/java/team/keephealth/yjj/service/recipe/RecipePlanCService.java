package team.keephealth.yjj.service.recipe;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.recipe.PlanChooseDto;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface RecipePlanCService extends IService<RecipePlan> {

    // 选择该智慧食谱
    // 选择该推荐计划后计划内容自动按时间顺序添加到我的食谱计划中
    ResultVo<T> choosePlan(PlanChooseDto dto);

    // 取消选择该智慧食谱
    // 取消选择该推荐智慧食谱后，自动删除该智慧食谱添加到我的计划中的所有内容（或者选择性删除？只删除当日时间段后的
    ResultVo<T> deleteChoose(Long planId);
}
