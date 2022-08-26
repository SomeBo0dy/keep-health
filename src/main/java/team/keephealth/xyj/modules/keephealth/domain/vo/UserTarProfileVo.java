package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.Data;

@Data
public class UserTarProfileVo {
    //目标体重（kg/公斤）
    private Double userTargetWeight;
    //目标达成速度
    private Long targetFinishTime;
}
