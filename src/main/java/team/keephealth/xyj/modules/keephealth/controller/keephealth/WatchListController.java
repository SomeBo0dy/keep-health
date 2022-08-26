package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.FollowDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectOtherWatchPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectUserPageDto;
import team.keephealth.xyj.modules.keephealth.service.WatchListService;


@Api(value = "FollowControllerApi", tags = {"关注操作接口"})
@RestController
@RequestMapping("/follow")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @ApiOperation(value = "关注用户")
    @SystemLog(businessName = "关注用户")
    @PostMapping
    @PreAuthorize("hasAuthority('user:follow:add')")
    public ResponseResult followUserById(@RequestBody FollowDto followDto) {
        return watchListService.followUserById(followDto);
    }

    @ApiOperation(value = "取消关注")
    @SystemLog(businessName = "取消关注")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:follow:delete')")
    public ResponseResult unFollowUserById(@PathVariable("userId") Long userId) {
        return watchListService.unFollowUserById(userId);
    }

    @ApiOperation(value = "获取自己的关注列表")
    @SystemLog(businessName = "获取自己的关注列表")
    @GetMapping
    @PreAuthorize("hasAuthority('user:follow:list')")
    public ResponseResult getWatchList(SelectUserPageDto userPageDto) {
        return watchListService.getWatchList(userPageDto);
    }
    @ApiOperation(value = "获取别人的关注列表(num和size,用户id必传，其余字段可组合使用作为查询条件，不需要就不传)")
    @SystemLog(businessName = "获取别人的关注列表")
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('user:follow:list')")
    public ResponseResult getWatchListById(SelectOtherWatchPageDto userPageDto) {
        return watchListService.getWatchListById(userPageDto);
    }
}
