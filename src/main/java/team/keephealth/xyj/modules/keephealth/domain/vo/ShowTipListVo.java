package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ShowTipListVo {
    @Column(name = "ksl_id")
    private Long kslId;
    @Column(name = "ksl_user_id")
    private Long kslUserId;
    @Column(name = "ksl_user_name")
    private String kslUserName;
    @Column(name = "ksl_content")
    private String kslContent;
    @Column(name = "tip_count")
    private Integer tipCount;
}
