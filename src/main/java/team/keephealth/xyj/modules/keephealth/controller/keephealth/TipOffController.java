package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TipShowDto;
import team.keephealth.xyj.modules.keephealth.service.ShowTipService;

@Api(value = "TipOffControllerApi", tags = {"举报动态操作接口"})
@RestController
@RequestMapping("/tip")
public class TipOffController {

    @Autowired
    private ShowTipService showTipService;

    @ApiOperation(value = "举报动态")
    @SystemLog(businessName = "举报动态")
    @PostMapping("/show")
    @PreAuthorize("hasAuthority('user:tip:add')")
    public ResponseResult addShowLifeTip(@RequestBody TipShowDto tipShowDto) {
        return showTipService.addShowLifeTip(tipShowDto);
    }

}
