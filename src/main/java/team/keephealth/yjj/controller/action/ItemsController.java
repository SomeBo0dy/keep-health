package team.keephealth.yjj.controller.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.enums.Inform;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.FoodService;
import team.keephealth.yjj.service.sport.WWEService;

import javax.annotation.Resource;

@Api(tags = {"项目数据（运动项目、食物）获取接口"})
@RestController
@RequestMapping("/items")
public class ItemsController {

    @Resource
    private FoodService foodService;
    @Resource
    private WWEService wweService;

    @ApiOperation(value = "获取食物列表")
    @SystemLog(businessName = "获取食物列表")
    @GetMapping("/foods")
    public ResultVo<T> getFoods(){
        return foodService.getFoods();
    }

    @ApiOperation(value = "获取运动项目列表")
    @SystemLog(businessName = "获取运动项目列表")
    @GetMapping("/wwe")
    public ResultVo<T> getWWE(){
        return wweService.getWWE();
    }

    @ApiOperation(value = "获取举报项目列表")
    @SystemLog(businessName = "获取举报项目列表")
    @GetMapping("/inform")
    public ResultVo<T> getInform(){
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", Inform.getInforms());
    }
}
