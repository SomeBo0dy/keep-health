package team.keephealth.yjj.domain.entity.sport;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.dto.sport.SportPlanDInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_sport_sta")
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanState {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 计划编号
    private Long pid;
    // 计划阶段
    @Column(name = "plan_state")
    private int planState;
    // 阶段名称
    @Column(name = "state_name")
    private String stateName;
    // 阶段预计消耗能量
    @Column(name = "total_kcal")
    private double totalKcal;
    // 阶段说明
    private String msg;
    // 阶段注意事项
    private String tips;

    public SportPlanState(SportPlanDInfoDto dto){
        this.planState = dto.getState();
        this.stateName = dto.getName();
        this.totalKcal = dto.getTol();
        this.msg = dto.getMsg();
        this.tips = dto.getTips();
    }
}
