package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String token;
    private UserInfoVo userInfoVo;
}
