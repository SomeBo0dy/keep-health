package team.keephealth.yjj.domain.entity.sport;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.action.SportPunchDto;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pu_sport")
@AllArgsConstructor
@NoArgsConstructor
public class SportPunch {

    // 自定义打卡

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 隶属打卡id
    @Column(name = "punch_id")
    private Long punchId;

    // 打卡的用户
    @Column(name = "account_id")
    private Long accountId;
    // 运动类型
    private Long sport;
    // 运动时长(分钟)
    @Column(name = "sport_time")
    private int sportTime;
    // 运动消耗能量（也是千卡）
    private double energy;
    // 运动图片
    private String pict;

    public SportPunch(SportPunchDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.sport = dto.getSport();
        this.sportTime = dto.getSportTime();
        this.energy = dto.getEnergy();
        this.pict = dto.getPict();
    }
}
