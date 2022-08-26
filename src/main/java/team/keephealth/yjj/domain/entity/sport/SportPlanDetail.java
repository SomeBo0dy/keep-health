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
@TableName("p_sport_dtl")
@AllArgsConstructor
@NoArgsConstructor
public class SportPlanDetail {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 计划编号
    private Long pid;
    // 计划阶段
    @Column(name = "plan_state")
    private int planState;
    // 该阶段共执行天数
    @Column(name = "total_day")
    private int totalDay;
    // 每轮锻炼持续天数
    @Column(name = "round_exercise")
    private int roundExercise;
    // 每轮锻炼后休息天数
    @Column(name = "round_relax")
    private int roundRelax;
    // 该阶段运动类型
    @Column(name = "sport_type")
    private int sportType;
    // 每次运动时长
    @Column(name = "sport_time")
    private int sportTime;
    // 每次运动消耗能量
    private double energy;
    // 配图
    private String pict;

    public SportPlanDetail(SportPlanDInfoDto dto){
        this.planState = dto.getState();
        this.totalDay = dto.getDays();
        this.roundExercise = dto.getExercise();
        this.roundRelax = dto.getRelax();
        this.sportType = dto.getSport();
        this.sportTime = dto.getTime();
        this.energy = dto.getEnergy();
        this.pict = dto.getPict();
    }

}
