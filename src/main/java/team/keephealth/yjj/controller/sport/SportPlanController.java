package team.keephealth.yjj.controller.sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.sport.SportPlanInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.sport.SportPlanService;

import javax.annotation.Resource;

@Api(tags = {"运动计划操作"})
@RestController
@RequestMapping("/sportPlan")
public class SportPlanController {

    @Resource
    private SportPlanService service;

    @ApiOperation(value = "添加运动训练计划")
    @SystemLog(businessName = "添加运动训练计划")
    @PutMapping("/add")
    public ResultVo<T> addSport(@RequestBody SportPlanInfoDto dto){
        return service.addPlan(dto);
    }

    @ApiOperation(value = "更改运动训练计划")
    @SystemLog(businessName = "更改运动训练计划")
    @PutMapping("/update")
    public ResultVo<T> updateSport(@RequestBody SportPlanInfoDto dto){
        return service.updatePlan(dto);
    }

    @ApiOperation(value = "删除运动训练计划")
    @SystemLog(businessName = "删除运动训练计划")
    @DeleteMapping("/delete/{planId}")
    public ResultVo<T> deleteSport(@PathVariable("planId") Long planId){
        return service.deletePlan(planId);
    }

    @ApiOperation(value = "查看运动训练计划")
    @SystemLog(businessName = "查看运动训练计划")
    @GetMapping("/detail/{planId}")
    public ResultVo<T> getSport(@PathVariable("planId") Long planId){
        return service.getPlan(planId);
    }

}
