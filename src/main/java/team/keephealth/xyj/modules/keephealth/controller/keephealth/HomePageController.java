package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.BMIDto;
import team.keephealth.xyj.modules.keephealth.utils.HomePageUtils;


@Api(value = "HomePageControllerApi", tags = {"主页工具操作接口"})
@RestController
@RequestMapping("/tool")
public class HomePageController {
    @ApiOperation(value = "计算bmi")
    @SystemLog(businessName = "计算bmi")
    @GetMapping("/bmi")
    public ResponseResult getBMI(BMIDto bmiDto) {
        return ResponseResult.okResult(HomePageUtils.getBMI(bmiDto));
    }

}
