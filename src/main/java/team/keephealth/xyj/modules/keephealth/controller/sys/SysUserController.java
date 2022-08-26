package team.keephealth.xyj.modules.keephealth.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectUserPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.StateDto;
import team.keephealth.xyj.modules.keephealth.service.UserService;

@Api(value = "SysUserControllerApi", tags = {"管理员操作用户接口"})
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "管理员分页获取用户信息(num和size必传，其余字段可组合使用作为查询条件，不需要就不传)")
    @SystemLog(businessName = "管理员分页获取用户信息")
    @GetMapping("/info")
    public ResponseResult getUserInfoByPage(SelectUserPageDto userPageDto) {
        return userService.getUserInfoByPage(userPageDto);
    }

    @ApiOperation(value = "管理员根据用户id改变用户状态（0正常 1停用）")
    @SystemLog(businessName = "管理员根据用户id改变用户状态（0正常 1停用）")
    @PutMapping("/state")
    public ResponseResult setUserState(@RequestBody StateDto stateDto) {
        return userService.setUserState(stateDto);
    }

    @ApiOperation(value = "管理员通过id注销用户")
    @SystemLog(businessName = "管理员通过id注销用户")
    @DeleteMapping("/{userId}")
    public ResponseResult sysCancelAccount(@PathVariable("userId") Long userId) {
        return userService.sysCancelAccount(userId);
    }

}
