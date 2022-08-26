package team.keephealth.xyj.modules.keephealth.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    private Long id;
    private String nickName;
    private String avatar;
    private String sex;
    private String email;
    private String type;
    @Column(name = "type_name")
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
