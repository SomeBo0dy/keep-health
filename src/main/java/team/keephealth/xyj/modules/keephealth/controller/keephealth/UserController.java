package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.AvatarDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.PasswordDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.UserInfoDto;
import team.keephealth.xyj.modules.keephealth.service.UserService;

@Api(value = "UserControllerApi", tags = {"用户操作接口(除业务以外的公共接口)"})
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "更新用户头像")
    @SystemLog(businessName = "更新用户头像")
    @PutMapping("/avatar")
    @PreAuthorize("hasAuthority('user:info:update')")
    public ResponseResult editAvatar(@RequestBody AvatarDto avatarDto) {
        return userService.editAvatar(avatarDto);
    }

    @ApiOperation(value = "改密码")
    @SystemLog(businessName = "改密码")
    @PutMapping("/password")
    @PreAuthorize("hasAuthority('user:info:update')")
    public ResponseResult editPassword(@RequestBody PasswordDto passwordDto) {
        return userService.editPassword(passwordDto);
    }

    @ApiOperation(value = "更新用户信息")
    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/info")
    @PreAuthorize("hasAuthority('user:info:update')")
    public ResponseResult editUserInfo(@RequestBody UserInfoDto userInfoDto) {
        return userService.editUserInfo(userInfoDto);
    }

    @ApiOperation(value = "获取用户个人信息")
    @SystemLog(businessName = "获取用户个人信息")
    @GetMapping("/info")
    @PreAuthorize("hasAuthority('user:info:list')")
    public ResponseResult getUserInfo() {
        return userService.getUserInfo();
    }

    @ApiOperation(value = "用户注销")
    @SystemLog(businessName = "用户注销")
    @DeleteMapping
    @PreAuthorize("hasAuthority('user:account:delete')")
    public ResponseResult cancelAccount() {
        return userService.cancelAccount();
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @SystemLog(businessName = "根据用户id获取用户信息")
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAuthority('user:info:list')")
    public ResponseResult getUserInfoById(@PathVariable("userId") Long id) {
        return userService.getUserInfoById(id);
    }
}
