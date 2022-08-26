package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.MyShowLifeDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.UserSelectShowDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.ShowLifeDto;
import team.keephealth.xyj.modules.keephealth.service.ShowLifeService;

@Api(value = "ShowLifeControllerApi", tags = {"用户动态操作接口"})
@RestController
@RequestMapping("/shows")
public class ShowLifeController {

    @Autowired
    private ShowLifeService showLifeService;

    @ApiOperation(value = "发动态")
    @SystemLog(businessName = "发动态")
    @PostMapping
    @PreAuthorize("hasAuthority('user:show:add')")
    public ResponseResult addShowLife(@RequestBody ShowLifeDto showLifeDto) {
        return showLifeService.addShowLife(showLifeDto);
    }
    @ApiOperation(value = "获取个人动态")
    @SystemLog(businessName = "获取个人动态")
    @GetMapping
    @PreAuthorize("hasAuthority('user:show:list')")
    public ResponseResult getShowLife(MyShowLifeDto showLifeDto) {
        return showLifeService.getShowLife(showLifeDto);
    }
    @ApiOperation(value = "删除个人动态")
    @SystemLog(businessName = "删除个人动态")
    @DeleteMapping("/{kslId}")
    @PreAuthorize("hasAuthority('user:show:delete')")
    public ResponseResult deleteShowLife(@PathVariable("kslId") Long kslId) {
        return showLifeService.deleteShowLife(kslId);
    }
    @ApiOperation(value = "根据动态id获取动态信息")
    @SystemLog(businessName = "根据动态id获取动态信息")
    @GetMapping("/{kslId}")
    @PreAuthorize("hasAuthority('user:show:list')")
    public ResponseResult getShowLifeById(@PathVariable("kslId") Long kslId) {
        return showLifeService.getShowLifeById(kslId);
    }
    @ApiOperation(value = "获取动态列表(num和size,timeOrder，其余字段可组合使用作为查询条件，不需要就不传)")
    @SystemLog(businessName = "获取动态列表")
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('user:show:list')")
    public ResponseResult getShowLifeByUserId(UserSelectShowDto showDto) {
        return showLifeService.getShowLifeByUserId(showDto);
    }
}
