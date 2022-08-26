package team.keephealth.xyj.modules.keephealth.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetTipPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TipShowDto;
import team.keephealth.xyj.modules.keephealth.service.ShowTipService;

@Api(value = "SysTipOffControllerApi", tags = {"管理员操作动态举报接口"})
@RestController
@RequestMapping("/sys/tip")
public class SysTipOffController {
    @Autowired
    private ShowTipService showTipService;

    @ApiOperation(value = "通过举报id获得动态举报信息")
    @SystemLog(businessName = "通过举报id获得动态举报信息")
    @GetMapping("/show/{id}")
    public ResponseResult getTipById(@PathVariable("id") Long id) {
        return showTipService.getTipById(id);
    }
    @ApiOperation(value = "通过动态id获得举报")
    @SystemLog(businessName = "通过动态id获得举报")
    @GetMapping("/show/info")
    public ResponseResult getTipByShowId(GetTipPageDto tipPageDto) {
        return showTipService.getTipByShowId(tipPageDto);
    }


    @ApiOperation(value = "获得动态举报信息列表")
    @SystemLog(businessName = "获得动态举报信息列表")
    @GetMapping("/show")
    public ResponseResult tipList(GetPageDto getTipDto) {
        return showTipService.tipList(getTipDto);
    }
    @ApiOperation(value = "管理撤销举报")
    @SystemLog(businessName = "管理撤销举报")
    @DeleteMapping("/{kstId}")
    public ResponseResult deleteTip(@PathVariable("kstId") Long kstId) {
        return showTipService.deleteTip(kstId);
    }
    @ApiOperation(value = "通过kslId动态id，删除与该动态相关的举报信息")
    @SystemLog(businessName = "通过kslId动态id，删除与该动态相关的举报信息")
    @DeleteMapping("/all/{kslId}")
    public ResponseResult deleteTipByKslId(@PathVariable("kslId") Long kslId) {
        return showTipService.deleteTipByKslId(kslId);
    }
}
