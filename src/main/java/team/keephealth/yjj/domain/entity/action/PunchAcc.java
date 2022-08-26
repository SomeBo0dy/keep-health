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
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_punch")
@AllArgsConstructor
@NoArgsConstructor
public class PunchAcc {

    // 打卡累计

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 打卡用户id
    @Column(name = "account_id")
    private Long accountId;

    // 减肥餐累计打卡天数
    @Column(name = "re_count")
    private int reCount;
    // 运动累计打卡天数
    @Column(name = "sp_count")
    private int spCount;
    // 减肥餐上次打卡时间
    @Column(name = "re_last")
    private Date reLast;
    // 运动上次打卡时间
    @Column(name = "sp_last")
    private Date spLast;
    // 减肥餐历史最高连续打卡天数
    @Column(name = "re_high")
    private int reHigh;
    // 运动历史最高连续打卡天数
    @Column(name = "sp_high")
    private int spHigh;
}
