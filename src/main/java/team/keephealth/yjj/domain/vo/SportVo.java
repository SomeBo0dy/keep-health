package team.keephealth.yjj.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.entity.sport.SportPlan;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SportVo {

    private Long id;
    // 计划标题
    private String title;
    // 留言
    private String words;
    // 运动类型
    private Long sportType;
    // 运动时长(分钟)
    private int sportTime;
    // 运动消耗能量（也是千卡）
    private double energy;
    // 运动图片
    private String pict;
    private Date beginTime;
    private Date endTime;

    // 隶属的推荐计划，无则null
    private Long planId;
    // 发布人id
    private Long accountId;
    // 训练目标 1：全身减脂 2：瘦腰 3：瘦胳膊 4：瘦腿
    private int aim;
    // 标题
    private String planTitle;
    // 简介
    private String planWords;
    // 共消耗kcal
    private double kcal;

    public void setSport(SportPlan plan){
        this.accountId = plan.getAccountId();
        this.aim = plan.getAim();
        this.planTitle = plan.getTitle();
        this.planWords = plan.getWords();
        this.kcal = plan.getKcal();
    }
}
