package team.keephealth.yjj.controller.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanQDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.RecipePlanQueryService;

import javax.annotation.Resource;

@Api(tags = {"智慧食谱列表获取接口"})
@RestController
@RequestMapping("/recipePlan")
public class RecipePlanQueryController {

    @Resource
    private RecipePlanQueryService queryService;

    @ApiOperation(value = "获取全部智慧食谱")
    @SystemLog(businessName = "获取全部智慧食谱")
    @GetMapping("/list")
    public ResultVo<T> queryAll(RecipePlanQDto dto){
        return queryService.queryAll(dto);
    }

    @ApiOperation(value = "获取该作者全部智慧食谱")
    @SystemLog(businessName = "获取该作者全部智慧食谱")
    @GetMapping("/list/user")
    public ResultVo<T> queryByU(RecipePlanQDto dto){
        return queryService.queryByAc(dto);
    }
}
