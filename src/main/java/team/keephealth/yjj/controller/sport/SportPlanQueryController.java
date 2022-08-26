package team.keephealth.yjj.controller.sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.sport.SportPlanQDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.sport.SportPlanQueryService;

import javax.annotation.Resource;

@Api(tags = {"运动计划获取"})
@RestController
@RequestMapping("/sportPlan")
public class SportPlanQueryController {

    @Resource
    private SportPlanQueryService queryService;

    @ApiOperation(value = "获取全部运动计划")
    @SystemLog(businessName = "获取全部运动计划")
    @GetMapping("/list")
    public ResultVo<T> queryAll(SportPlanQDto dto){
        return queryService.queryAll(dto);
    }

    @ApiOperation(value = "获取作者全部运动计划")
    @SystemLog(businessName = "获取作者全部运动计划")
    @GetMapping("/list/user")
    public ResultVo<T> queryByU(SportPlanQDto dto){
        return queryService.queryByAc(dto);
    }

    @ApiOperation(value = "根据用户浏览推荐")
    @SystemLog(businessName = "根据用户浏览推荐")
    @GetMapping("/recommend")
    public ResultVo<T> getRecommend(){
        return queryService.getRecommend();
    }
}
