package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CheckLikeDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.ShowLifeDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.ShowLikeDto;
import team.keephealth.xyj.modules.keephealth.service.ShowLikeService;

@Api(value = "ShowLikeControllerApi", tags = {"用户动态点赞操作接口"})
@RestController
@RequestMapping("/shows/like")
public class ShowLikeController {
    @Autowired
    private ShowLikeService showLikeService;

    @ApiOperation(value = "点赞动态")
    @SystemLog(businessName = "点赞动态")
    @PostMapping
    @PreAuthorize("hasAuthority('user:show:like')")
    public ResponseResult likeShow(@RequestBody ShowLikeDto showLikeDto) {
        return showLikeService.likeShow(showLikeDto);
    }
    @ApiOperation(value = "取消点赞动态")
    @SystemLog(businessName = "取消点赞动态")
    @DeleteMapping("/{kslId}")
    @PreAuthorize("hasAuthority('user:show:like')")
    public ResponseResult cancelLikeShow(@PathVariable("kslId") Long kslId) {
        return showLikeService.cancelLikeShow(kslId);
    }
    @ApiOperation(value = "查看点赞情况")
    @SystemLog(businessName = "查看点赞情况")
    @GetMapping
    @PreAuthorize("hasAuthority('user:show:like')")
    public ResponseResult checkLike(CheckLikeDto checkLikeDto) {
        return showLikeService.checkLike(checkLikeDto);
    }
}
