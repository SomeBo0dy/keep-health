package team.keephealth.xyj.modules.keephealth.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class ShowTipVo {
    //举报id
    @Column(name = "kst_id")
    private Long kstId;
    //动态id
    @Column(name = "ksl_id")
    private Long kslId;
    //举报内容
    @Column(name = "tip_content")
    private String tipContent;
    @Column(name = "ksl_user_id")
    private Long kslUserId;
    @Column(name = "ksl_content")
    private String kslContent;
    @Column(name = "ksl_user_name")
    private String kslUserName;
    @Column(name = "creat_by")
    private Long createBy;
    @Column(name = "tipper_name")
    private String tipperName;
    @Column(name = "create_time")
    private Date createTime;
}
