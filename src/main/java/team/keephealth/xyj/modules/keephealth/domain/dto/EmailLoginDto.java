package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmailLoginDto {
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户类型：0代表普通用户 1代表第三方工作人员，2代表管理员")
    private String type;
}
