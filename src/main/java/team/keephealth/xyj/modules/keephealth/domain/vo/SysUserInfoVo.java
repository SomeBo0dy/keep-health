package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.Data;

@Data
public class SysUserInfoVo {
    private Long id;
    //账号
    private String account;
    private String nickName;
    private String avatar;
    private String sex;
    private String email;
    private String type;
    private String typeName;
    //账号状态（0正常 1停用）
    private String state;
    //手机号
    private String phoneNumber;
    //地址
    private String location;
    //个人简介
    private String introduction;
}
