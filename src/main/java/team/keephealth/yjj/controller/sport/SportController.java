package team.keephealth.yjj.controller.sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.sport.SportInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.sport.SportService;

import javax.annotation.Resource;

@Api(tags = {"我的运动计划操作"})
@RestController
@RequestMapping("/sport")
public class SportController {

    @Resource
    private SportService sportService;

    @ApiOperation(value = "添加我的运动计划")
    @SystemLog(businessName = "添加我的运动计划")
    @PutMapping("/add")
    public ResultVo<T> addSport(@RequestBody SportInfoDto dto){
        return sportService.addSport(dto);
    }

    @ApiOperation(value = "更改我的运动计划")
    @SystemLog(businessName = "更改我的运动计划")
    @PutMapping("/update")
    public ResultVo<T> updateSport(@RequestBody SportInfoDto dto){
        return sportService.updateSport(dto);
    }

    @ApiOperation(value = "获取我的运动计划信息")
    @SystemLog(businessName = "获取我的运动计划信息")
    @GetMapping("/detail/{sportId}")
    public ResultVo<T> getDetail(@PathVariable("sportId") Long sportId){
        return sportService.getDetail(sportId);
    }

    @ApiOperation(value = "删除我的运动计划")
    @SystemLog(businessName = "删除我的运动计划")
    @DeleteMapping("/delete/{sportId}")
    public ResultVo<T> deleteSport(@PathVariable("sportId") Long sportId){
        return sportService.deleteSport(sportId);
    }
}
