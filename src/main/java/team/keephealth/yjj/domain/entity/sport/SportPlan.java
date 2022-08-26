package team.keephealth.yjj.domain.entity.sport;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.sport.SportPlanInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_sport")
@AllArgsConstructor
@NoArgsConstructor
public class SportPlan {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 发布人id
    @Column(name = "account_id")
    private Long accountId;
    // 训练目标 1：全身减脂 2：瘦腰 3：瘦胳膊 4：瘦腿
    private int aim;
    // 标题
    private String title;
    // 简介
    private String words;
    // 共消耗kcal
    private double kcal;
    // 发布时间
    @Column(name = "add_time")
    private Date addTime;

    public SportPlan(SportPlanInfoDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.aim = dto.getAim();
        this.title = dto.getTitle();
        this.words = dto.getWords();
        this.kcal = dto.getKcal();
        this.addTime = new Date();
    }
}
