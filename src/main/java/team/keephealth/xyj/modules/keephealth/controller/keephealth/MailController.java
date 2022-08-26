package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.BindEmailDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.EmailLoginDto;
import team.keephealth.xyj.modules.keephealth.service.MailService;

@Api(value = "MailControllerApi", tags = {"邮件操作接口"})
@RestController
@RequestMapping("/email")
public class MailController {
    @Autowired
    private MailService mailService;

    @ApiOperation(value = "发送绑定邮箱验证码")
    @SystemLog(businessName = "发送绑定邮箱验证码")
    @GetMapping("/send/code/{email}")
    @PreAuthorize("hasAuthority('user:mail:bind')")
    public ResponseResult sendEmailCode(@PathVariable("email") String email) {
        return mailService.sendEmailCode(email);
    }
    @ApiOperation(value = "发送邮箱登录验证码")
    @SystemLog(businessName = "发送邮箱登录验证码")
    @GetMapping("/send/code/login")
    public ResponseResult sendLoginEmailCode(EmailLoginDto emailLoginDto) {
        return mailService.sendLoginEmailCode(emailLoginDto);
    }

    @ApiOperation(value = "绑定邮箱")
    @SystemLog(businessName = "绑定邮箱")
    @PostMapping("/bind")
    @PreAuthorize("hasAuthority('user:mail:bind')")
    public ResponseResult bindEmail(@RequestBody BindEmailDto bindEmailDto) {
        return mailService.bindEmail(bindEmailDto);
    }

}
