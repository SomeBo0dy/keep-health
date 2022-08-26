package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoDto {
    //昵称
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    //用户性别
    @ApiModelProperty(value = "性别")
    private String sex;

    //头像
    @ApiModelProperty(value = "用户头像url")
    private String avatar;

    //地址
    @ApiModelProperty(value = "用户地址")
    private String location;

    //个人简介
    @ApiModelProperty(value = "用户个人简介")
    private String introduction;

}
