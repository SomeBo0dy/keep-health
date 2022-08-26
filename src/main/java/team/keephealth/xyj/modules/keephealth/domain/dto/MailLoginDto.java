package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailLoginDto {
    //邮箱
    @ApiModelProperty(value = "邮箱")
    private String email;
    //验证码
    @ApiModelProperty(value = "验证码")
    private String e_code;
    //登录角色类型
    @ApiModelProperty(value = "用户类型：0代表普通用户 1代表第三方工作人员，2代表管理员")
    private String type;
}
