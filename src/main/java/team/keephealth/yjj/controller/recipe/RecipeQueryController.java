package team.keephealth.yjj.controller.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.TimeQueryMy;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.RecipeQueryService;

import javax.annotation.Resource;

@Api(tags = {"我的食谱列表访问接口"})
@RestController
@RequestMapping("/recipeList")
public class RecipeQueryController {

    @Resource
    private RecipeQueryService queryService;

    @ApiOperation(value = "获取我的减肥餐计划列表")
    @SystemLog(businessName = "获取我的减肥餐计划列表")
    @GetMapping("/all")
    public ResultVo<T> getAll(){
        return queryService.queryAll();
    }

    @ApiOperation(value = "查询时间范围内的减肥餐计划")
    @SystemLog(businessName = "查询时间范围内的减肥餐计划")
    @GetMapping("/time")
    public ResultVo<T> byTime(TimeQueryMy dto){
        return queryService.queryByTime(dto);
    }

    @ApiOperation(value = "查询当前时间的运动计划")
    @SystemLog(businessName = "查询当前时间的运动计划")
    @GetMapping("/now")
    public ResultVo<T> getNow(){
        return queryService.queryNow();
    }
}
