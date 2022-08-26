package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserCurProfileVo {
    //用户出生日期
    private Date userBirthday;
    //体重（kg/公斤）
    private Double userWeight;
    //用户身高（cm）
    private Double userHeight;
}
