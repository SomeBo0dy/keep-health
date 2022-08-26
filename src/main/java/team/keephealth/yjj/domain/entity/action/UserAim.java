package team.keephealth.yjj.domain.entity.action;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_aim_sta")
@AllArgsConstructor
@NoArgsConstructor
public class UserAim {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户id
    @Column(name = "account_id")
    private Long accountId;
    // 目标方向(未定义记0)
    private int aim;
    // 每日允许吸收热量
    private double absorb;
    // 每日计划消耗热量
    private double expend;

    public UserAim(Long id){
        this.accountId = id;
        this.aim = 0;
        this.absorb = 0;
        this.expend = 0;
    }
}
