package team.keephealth.yjj.service.action;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface PlanChooseService extends IService<PlanChoose> {

    // 设定我的计划中用户可以使用自定的，也可以是选择推荐的计划后添加进来的
    // 我也忘了当初为什么要写这个，或许有用？
    // 查询用户是否使用推荐的运动计划，有则返回推荐计划的今日内容(或者不需要？
    ResultVo<T> isUseSport();

    // 查询用户是否使用推荐的套餐，有则返回推荐计划的今日内容(或者不需要？
    ResultVo<T> isUseRecipe();

}
