package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BindEmailDto {
    @ApiModelProperty(value = "邮箱地址")
    private String email;
    @ApiModelProperty(value = "验证码")
    private String e_code;
}
