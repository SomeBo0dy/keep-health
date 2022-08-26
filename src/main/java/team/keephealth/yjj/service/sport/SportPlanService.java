package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.yjj.domain.dto.sport.SportPlanInfoDto;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface SportPlanService extends IService<SportPlan> {

    // 添加运动计划推荐
    ResultVo<T> addPlan(SportPlanInfoDto dto);

    // 更改
    ResultVo<T> updatePlan(SportPlanInfoDto dto);

    // 删除
    ResultVo<T> deletePlan(Long planId);

    // 查看运动计划
    ResultVo<T> getPlan(Long planId);
}
