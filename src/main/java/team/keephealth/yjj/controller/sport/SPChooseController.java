package team.keephealth.yjj.controller.sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.sport.SportPlanCService;

import javax.annotation.Resource;

@Api(tags = {"运动计划选择管理"})
@RestController
@RequestMapping("/sportPlan")
public class SPChooseController {

    @Resource
    private SportPlanCService cService;

    @ApiOperation(value = "选择该运动计划")
    @SystemLog(businessName = "选择该运动计划")
    @PutMapping("/choose/{planId}")
    public ResultVo<T> choosePlan(@PathVariable("planId") Long planId){
        return cService.choosePlan(planId);
    }

    @ApiOperation(value = "取消选择该运动计划")
    @SystemLog(businessName = "取消选择该运动计划")
    @PutMapping("/cancel/{planId}")
    public ResultVo<T> delChoose(@PathVariable("planId") Long planId){
        return cService.deleteChoose(planId);
    }
}
