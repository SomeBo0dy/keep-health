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
@TableName("sys_punch")
@AllArgsConstructor
@NoArgsConstructor
public class Punch {

    // 打卡

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 打卡用户id
    @Column(name = "account_id")
    private Long accountId;
    // 今日减肥餐打卡情况（未打卡：0/无punch数据 打卡成功：1 打卡失败：2
    @Column(name = "recipe_punch")
    private int recipePunch;
    // 今日运动打卡情况
    @Column(name = "sport_punch")
    private int sportPunch;
    // 减肥餐计划打卡
    @Column(name = "recipe_plan_bf")
    private Long recipePlanBf;
    @Column(name = "recipe_plan_lu")
    private Long recipePlanLu;
    @Column(name = "recipe_plan_dn")
    private Long recipePlanDn;
    // 运动计划打卡
    @Column(name = "sport_plan")
    private Long sportPlan;
    // 减肥餐自定义打卡（非
    @Column(name = "cp_recipe_bf")
    private Long cpRecipeBf;
    @Column(name = "cp_recipe_lu")
    private Long cpRecipeLu;
    @Column(name = "cp_recipe_dn")
    private Long cpRecipeDn;
    @Column(name = "cp_recipe")
    private Long cpRecipe;
    // 运动自定义打卡(不表示id，>0即表示有自定义打卡）
    @Column(name = "cp_sport")
    private Long cpSport;
    // 打卡日期
    @Column(name = "punch_time")
    private Date punchTime;

    public Punch(Long accountId, Date date){
        this.accountId = accountId;
        this.recipePunch = 0;
        this.sportPunch = 0;
        this.punchTime = date;
    }

    //清零
    public Punch(Punch orl){
        this.id = orl.id;
        this.accountId = orl.id;
        this.sportPunch = 0;
        this.recipePunch = 0;
        this.punchTime = orl.punchTime;
    }
}
