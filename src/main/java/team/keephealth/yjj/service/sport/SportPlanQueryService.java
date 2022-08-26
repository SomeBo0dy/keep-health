package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.sport.SportPlanQDto;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface SportPlanQueryService extends IService<SportPlan> {

    // 获取全部运动计划推荐
    ResultVo<T> queryAll(SportPlanQDto dto);

    // 获取该作者全部运动计划
    ResultVo<T> queryByAc(SportPlanQDto dto);

    // 根据用户浏览推荐
    ResultVo<T> getRecommend();
}
