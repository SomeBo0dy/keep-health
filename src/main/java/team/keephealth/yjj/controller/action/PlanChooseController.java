package team.keephealth.yjj.controller.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.action.PlanChooseService;

import javax.annotation.Resource;

@Api(tags = {"查询用户使用的推荐计划接口"})
@RestController
@RequestMapping("/planChoose")
public class PlanChooseController {

    @Resource
    private PlanChooseService chooseService;

    @ApiOperation(value = "查询用户是否使用推荐的运动计划",
            notes = "设定我的计划中用户可以使用自定的，也可以是选择推荐的计划后添加进来的\n" +
                    "忘了当初为什么要写这个，或许有用？\n" +
                    "查询用户是否使用推荐的运动计划，有则返回推荐计划的今日内容(或者不需要？")
    @SystemLog(businessName = "查询用户是否使用推荐的运动计划")
    @GetMapping("/sport")
    public ResultVo<T> isUseSport(){
        return chooseService.isUseSport();
    }

    @ApiOperation(value = "查询用户是否使用推荐的套餐", notes = "同上")
    @SystemLog(businessName = "查询用户是否使用推荐的套餐")
    @GetMapping("/recipe")
    public ResultVo<T> isUseRecipe(){
        return chooseService.isUseRecipe();
    }
}
