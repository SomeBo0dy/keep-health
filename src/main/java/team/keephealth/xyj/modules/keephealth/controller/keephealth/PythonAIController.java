package team.keephealth.xyj.modules.keephealth.controller.keephealth;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.python.ModelDetect;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;


import java.util.List;
import java.util.Map;

@Api(value = "PythonAIControllerApi", tags = {"AI操作接口"})
@RestController
@RequestMapping("/ai")
public class PythonAIController {
    @ApiOperation(value = "上传图片,获取食品热量")
    @PostMapping("/food-cal")
    @PreAuthorize("hasAuthority('system:file:upload')")
    public ResponseResult getFoodHeat(MultipartFile file) {
        ModelDetect modelDetect = new ModelDetect();
        List<Map<String, Object>> detect = modelDetect.detect(file);
        if (detect.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.FOOD_NOT_FOUND);
        }
        return ResponseResult.okResult(detect);
    }
}
