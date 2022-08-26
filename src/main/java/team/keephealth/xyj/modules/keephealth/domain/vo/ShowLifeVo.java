package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ShowLifeVo {

    private Long kslId;
    //用户id
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_avatar")
    private String userAvatar;

    private String text;
    //0正常 1被锁定
    private String state;

    private Date createTime;

    private Date updateTime;

    private Boolean flag;
}
