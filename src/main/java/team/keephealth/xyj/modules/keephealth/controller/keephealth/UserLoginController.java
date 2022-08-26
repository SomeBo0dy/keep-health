package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.MailLoginDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.PasswordUserDto;
import team.keephealth.xyj.modules.keephealth.service.UserLoginService;

@Api(value = "UserLoginControllerApi", tags = {"登录注册操作接口"})
@RestController
public class UserLoginController {

    @Autowired
    private UserLoginService userloginService;

    @ApiOperation(value = "用户账号密码登录")
    @SystemLog(businessName = "用户登录")
    @PostMapping("/login/password")
    public ResponseResult loginPassword(@RequestBody PasswordUserDto userLoginDto) {
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "用户邮箱登录")
    @SystemLog(businessName = "用户邮箱登录")
    @PostMapping("/login/email")
    public ResponseResult loginMail(@RequestBody MailLoginDto mailLoginDto) {
        return ResponseResult.okResult();
    }

    @ApiOperation(value = "用户退出登录")
    @SystemLog(businessName = "用户退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return userloginService.logout();
    }

    @ApiOperation(value = "用户注册")
    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody PasswordUserDto userRegisterDto) {
        return userloginService.register(userRegisterDto);
    }

}
