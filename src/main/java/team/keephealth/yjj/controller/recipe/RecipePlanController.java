package team.keephealth.yjj.controller.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.RecipePlanService;

import javax.annotation.Resource;

@Api(tags = {"智慧食谱操作接口"})
@RestController
@RequestMapping("/recipePlan")
public class RecipePlanController {

    @Resource
    private RecipePlanService recipePlanService;

    @ApiOperation(value = "添加智慧食谱")
    @SystemLog(businessName = "添加智慧食谱")
    @PutMapping("/add")
    public ResultVo<T> addRecipe(@RequestBody RecipePlanInfoDto dto){
        return recipePlanService.addPlan(dto);
    }

    @ApiOperation(value = "更改智慧食谱")
    @SystemLog(businessName = "更改智慧食谱")
    @PutMapping("/update")
    public ResultVo<T> updateRecipe(@RequestBody RecipePlanInfoDto dto){
        return recipePlanService.updatePlan(dto);
    }

    @ApiOperation(value = "删除智慧食谱")
    @SystemLog(businessName = "删除智慧食谱")
    @DeleteMapping("/delete/{planId}")
    public ResultVo<T> deleteRecipe(@PathVariable("planId") Long planId){
        return recipePlanService.deletePlan(planId);
    }

    @ApiOperation(value = "查看智慧食谱内容")
    @SystemLog(businessName = "查看智慧食谱内容")
    @GetMapping("/detail/{planId}")
    public ResultVo<T> getRecipe(@PathVariable("planId") Long planId){
        return recipePlanService.getPlan(planId);
    }


}
