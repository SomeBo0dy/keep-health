package team.keephealth.yjj.controller.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.action.PunchDeleteDto;
import team.keephealth.yjj.domain.dto.action.RecipePunchDto;
import team.keephealth.yjj.domain.dto.action.SportPunchDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.action.PunchService;

import javax.annotation.Resource;

@Api(tags = {"打卡操作接口"})
@RestController
@RequestMapping("/punch")
public class PunchController {

    @Resource
    private PunchService punchService;

    @ApiOperation(value = "添加运动打卡")
    @SystemLog(businessName = "添加运动打卡")
    @PutMapping("/addSport")
    public ResultVo<T> addSportPunch(@RequestBody SportPunchDto dto){
        return punchService.addSportPunch(dto);
    }

    @ApiOperation(value = "添加减肥餐打卡")
    @SystemLog(businessName = "添加减肥餐打卡")
    @PutMapping("/addRecipe")
    public ResultVo<T> addRecipePunch(@RequestBody RecipePunchDto dto){
        return punchService.addRecipePunch(dto);
    }

    @ApiOperation(value = "删除打卡记录",notes = "只可删除当天，以往记录删除只能选择删除全天所有记录。有传输的id都会删除")
    @SystemLog(businessName = "删除打卡记录")
    @DeleteMapping("/delete")
    public ResultVo<T> deletePunch(@RequestBody PunchDeleteDto dto){
        return punchService.deletePunch(dto);
    }

    @ApiOperation(value = "删除一日所有打卡记录")
    @SystemLog(businessName = "删除一日所有打卡记录")
    @DeleteMapping("/deleteDay/{day}")
    public ResultVo<T> deleteAll(@PathVariable("day") String day){
        return punchService.deleteAll(day);
    }
}
