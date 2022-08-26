package team.keephealth.xyj.modules.keephealth.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.StateDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SysSelectShowDto;
import team.keephealth.xyj.modules.keephealth.service.ShowLifeService;

@Api(value = "SysShowLifeControllerApi", tags = {"管理员操作动态接口"})
@RestController
@RequestMapping("/sys/show")
public class SysShowLifeController {

    @Autowired
    private ShowLifeService showLifeService;

    @ApiOperation(value = "管理员分页获取动态信息(num和size,timeOrder必传，其余字段可组合使用作为查询条件，不需要就不传)")
    @SystemLog(businessName = "管理员分页获取用户信息")
    @GetMapping("/info")
    public ResponseResult getShowInfoByPage(SysSelectShowDto sysSelectShowDto) {
            return showLifeService.getShowInfoByPage(sysSelectShowDto);
    }
    @ApiOperation(value = "管理员根据动态id改变动态状态（0正常 1停用）")
    @SystemLog(businessName = "管理员根据动态id改变动态状态（0正常 1停用）")
    @PutMapping("/state")
    public ResponseResult setShowState(@RequestBody StateDto stateDto) {
        return showLifeService.setShowState(stateDto);
    }
    @ApiOperation(value = "管理员通过id删除动态")
    @SystemLog(businessName = "管理员通过id删除动态")
    @DeleteMapping("/{kslId}")
    public ResponseResult sysCancelShow(@PathVariable("kslId") Long kslId) {
        return showLifeService.sysCancelShow(kslId);
    }
}
