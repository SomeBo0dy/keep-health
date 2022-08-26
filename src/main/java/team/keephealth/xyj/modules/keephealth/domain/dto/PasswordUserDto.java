package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PasswordUserDto {
    //账号
    @ApiModelProperty(value = "账号")
    private String account;
    //密码
    @ApiModelProperty(value = "密码")
    private String password;
    //用户类型
    @ApiModelProperty(value = "用户类型：0代表普通用户 1代表第三方工作人员，2代表管理员")
    private String type;
}
