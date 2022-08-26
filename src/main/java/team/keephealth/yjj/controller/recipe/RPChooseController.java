package team.keephealth.yjj.controller.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.recipe.PlanChooseDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.RecipePlanCService;

import javax.annotation.Resource;

@Api(tags = {"智慧食谱选择接口"})
@RestController
@RequestMapping("/recipePlan")
public class RPChooseController {

    @Resource
    private RecipePlanCService cService;

    @ApiOperation(value = "选择该智慧食谱")
    @SystemLog(businessName = "选择该智慧食谱")
    @PutMapping("/choose")
    public ResultVo<T> choosePlan(@RequestBody PlanChooseDto dto){
        return cService.choosePlan(dto);
    }

    @ApiOperation(value = "取消选择该智慧食谱")
    @SystemLog(businessName = "取消选择该智慧食谱")
    @PutMapping("/cancel/{planId}")
    public ResultVo<T> delChoose(@PathVariable("planId") Long planId){
        return cService.deleteChoose(planId);
    }
}
