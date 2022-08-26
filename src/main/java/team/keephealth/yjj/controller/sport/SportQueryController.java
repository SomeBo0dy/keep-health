package team.keephealth.yjj.controller.sport;

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
import team.keephealth.yjj.service.sport.SportQueryService;

import javax.annotation.Resource;

@Api(tags = {"我的运动计划获取"})
@RestController
@RequestMapping("/sportList")
public class SportQueryController {

    @Resource
    private SportQueryService sportQueryService;

    @ApiOperation(value = "获取我的运动计划列表")
    @SystemLog(businessName = "获取我的运动计划列表")
    @GetMapping("/all")
    public ResultVo<T> getAll(){
        return sportQueryService.queryAll();
    }

    @ApiOperation(value = "查询起止时间范围内的运动计划")
    @SystemLog(businessName = "查询起止时间范围内的运动计划")
    @GetMapping("/time")
    public ResultVo<T> byTime(TimeQueryMy dto){
        return sportQueryService.queryByTime(dto);
    }

    @ApiOperation(value = "查询当前时间的运动计划")
    @SystemLog(businessName = "查询当前时间的运动计划")
    @GetMapping("/now")
    public ResultVo<T> getNow(){
        return sportQueryService.queryNow();
    }
}
