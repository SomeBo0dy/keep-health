package team.keephealth.yjj.service.action;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.action.Punch;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface PunchQueryService  extends IService<Punch> {
    /*
    用于打卡选择，若有计划可选择打卡计划或者自定义打卡，若无计划只能自定义打卡
    同时若有计划会返回当日所有计划列表
    若当日已有打卡会返回已打卡提醒
    减肥餐分为早中晚打卡（每一餐不可重复打卡
    运动可重复打卡（需要合理判定？
     */
    // 查询当日减肥餐打卡信息
    ResultVo<T> todayRecipe();

    // 查询当日运动计划
    ResultVo<T> todaySport();

    // 查询今日是否已记录（可在用户选择进入记录页面时查询提醒）用于弹窗提醒？
    ResultVo<T> recordCheck();

    // 查询这个月的打卡情况(若某天的没数据就是没打卡
    ResultVo<T> queryMonth();
}
