package team.keephealth.yjj.domain.entity.action;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.action.AimInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_aim")
@AllArgsConstructor
@NoArgsConstructor
public class Aim {

    // 目标每日记录

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    @Column(name = "account_id")
    private Long accountId;
    // 目标方向(未定义记0)
    private int aim;
    // 今日允许吸收热量
    @Column(name = "absorb_kcal")
    private double absorbKcal;
    // 今日计划消耗热量
    @Column(name = "expend_kcal")
    private double expendKcal;

    // 今日已吸收热量
    private double absorbed;
    // 今日已消耗热量
    private double expended;
    // 记录日期
    private Date today;

    public Aim(UserAim aim, Date today){
        this.accountId = SecurityUtils.getUserId();
        this.aim = aim.getAim();
        this.absorbKcal = aim.getAbsorb();
        this.expendKcal = aim.getExpend();
        this.absorbed = 0;
        this.expended = 0;
        this.today = today;
    }

    public void updateMsg(UserAim userAim){
        this.aim = userAim.getAim();
        this.absorbKcal = userAim.getAbsorb();
        this.expendKcal = userAim.getExpend();
    }

}
