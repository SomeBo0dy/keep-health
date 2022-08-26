package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface SportPlanCService extends IService<SportPlan> {

    // 选择该运动计划
    // 选择该推荐计划后计划内容自动按时间顺序添加到我的运动计划中
    ResultVo<T> choosePlan(Long planId);

    // 取消选择该运动计划
    // 取消选择该推荐运动计划后，自动删除该运动计划添加到我的计划中的所有内容（或者选择性删除？只删除当日时间段后的
    ResultVo<T> deleteChoose(Long planId);

}
