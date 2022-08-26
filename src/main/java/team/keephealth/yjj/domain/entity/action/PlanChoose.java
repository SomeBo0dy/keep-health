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
@TableName("p_choose")
@AllArgsConstructor
@NoArgsConstructor
public class PlanChoose {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 被选择的运动计划
    @Column(name = "sport_plan")
    private Long sportPlan;
    // 被选择的饮食计划
    @Column(name = "recipe_plan")
    private Long recipePlan;
    // 选择计划的用户
    @Column(name = "account_id")
    private Long accountId;

}
