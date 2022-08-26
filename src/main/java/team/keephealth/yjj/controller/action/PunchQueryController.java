package team.keephealth.yjj.controller.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.action.PunchQueryService;

import javax.annotation.Resource;

@Api(tags = {"我的打卡信息查询接口"})
@RestController
@RequestMapping("/punch")
public class PunchQueryController {

    @Resource
    private PunchQueryService punchQueryService;

    @ApiOperation(value = "查询当日减肥餐打卡信息")
    @SystemLog(businessName = "查询当日减肥餐打卡信息")
    @GetMapping("/todayRecipe")
    public ResultVo<T> todayRecipe(){
        return punchQueryService.todayRecipe();
    }

    @ApiOperation(value = "查询当日运动计划")
    @SystemLog(businessName = "查询当日运动计划")
    @GetMapping("/todaySport")
    public ResultVo<T> todaySport(){
        return punchQueryService.todaySport();
    }

    @ApiOperation(value = "查询今日是否已记录")
    @SystemLog(businessName = "查询今日是否已记录")
    @GetMapping("/check")
    public ResultVo<T> recordCheck(){
        return punchQueryService.recordCheck();
    }

    @ApiOperation(value = "查询这个月的打卡情况")
    @SystemLog(businessName = "查询这个月的打卡情况")
    @GetMapping("/month")
    public ResultVo<T> queryMonth(){
        return punchQueryService.queryMonth();
    }
}
