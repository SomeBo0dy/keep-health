package team.keephealth.xyj.modules.keephealth.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色外形(UserProfile)表实体类
 *
 * @author xyj
 * @since 2022-07-31 10:54:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_user_profile")
public class UserProfile extends Model<UserProfile> {

    @TableId(type = IdType.AUTO)
    private Long kupId;

    private Long userId;
    //用户出生日期
    private Date userBirthday;
    //体重（kg/公斤）
    private Double userWeight;
    //用户身高（cm）
    private Double userHeight;
    //目标体重（kg/公斤）
    private Double userTargetWeight;
    //目标达成速度
    private Long targetFinishTime;

}
