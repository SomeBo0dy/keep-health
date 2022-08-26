package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CurProfileDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TarProfileDto;
import team.keephealth.xyj.modules.keephealth.service.UserProfileService;

@Api(value = "UserProfileControllerApi", tags = {"用户身形档案操作接口"})
@RestController
@RequestMapping("/profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @ApiOperation(value = "添加用户当前体重档案")
    @SystemLog(businessName = "添加用户当前体重档案")
    @PostMapping("/cur")
    @PreAuthorize("hasAuthority('user:profile:add')")
    public ResponseResult addUserCurProfile(@RequestBody CurProfileDto profileDto) {
        return userProfileService.addUserCurProfile(profileDto);
    }
    @ApiOperation(value = "查看个人当前体重档案")
    @SystemLog(businessName = "查看个人当前体重档案")
    @GetMapping("/cur")
    @PreAuthorize("hasAuthority('user:profile:list')")
    public ResponseResult getUserCurProfile() {
        return userProfileService.getUserCurProfile();
    }

    @ApiOperation(value = "编辑用户当前体重档案")
    @SystemLog(businessName = "编辑用户当前体重档案")
    @PutMapping("/cur")
    @PreAuthorize("hasAuthority('user:profile:update')")
    public ResponseResult editUserCurProfile(@RequestBody CurProfileDto profileDto) {
        return userProfileService.editUserCurProfile(profileDto);
    }
    @ApiOperation(value = "添加/编辑用户目标体重档案")
    @SystemLog(businessName = "添加/编辑用户目标体重档案")
    @PostMapping("/tar")
    @PreAuthorize("hasAuthority('user:profile:add')")
    public ResponseResult addUserTarProfile(@RequestBody TarProfileDto profileDto) {
        return userProfileService.addUserTarProfile(profileDto);
    }
    @ApiOperation(value = "查看个人目标体重档案")
    @SystemLog(businessName = "查看个人目标体重档案")
    @GetMapping("/tar")
    @PreAuthorize("hasAuthority('user:profile:list')")
    public ResponseResult getUserTarProfile() {
        return userProfileService.getUserTarProfile();
    }


}
