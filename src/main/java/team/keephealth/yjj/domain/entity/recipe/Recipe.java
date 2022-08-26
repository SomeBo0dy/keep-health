package team.keephealth.yjj.domain.entity.recipe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_recipe")
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    // 我的饮食方案

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 食谱隶属用户
    @Column(name = "account_id")
    private Long accountId;
    // 食谱名称
    @Column(name = "recipe_name")
    private String recipeName;
    // 食谱简介
    private String words;
    // 食谱热量
    @Column(name = "tol_kcal")
    private double tolKcal;

    // 早餐内容
    private Long breakfast;
    // 午餐内容
    private Long lunch;
    // 晚餐内容
    private Long dinner;

    // 隶属的推荐计划，无则null
    @Column(name = "plan_id")
    private Long planId;

    // 计划开始时间
    @Column(name = "begin_time")
    private Date beginTime;
    // 计划结束时间
    @Column(name = "end_time")
    private Date endTime;

    public Recipe(RecipeInfoDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.recipeName = dto.getRecipeName();
        this.tolKcal = dto.getTolKcal();
        this.words = dto.getWords();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.beginTime = format.parse(dto.getBeginTime()+" 00:00:00");
            this.endTime = format.parse(dto.getEndTime()+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Recipe(RecipePlan plan, RecipePlanDetail detail){
        this.accountId = SecurityUtils.getUserId();
        this.recipeName = plan.getTitle();
        this.tolKcal = plan.getKcal();
        this.words = plan.getMsg();
        this.breakfast = detail.getBreakfast();
        this.lunch = detail.getLunch();
        this.dinner = detail.getDinner();
        this.planId = plan.getId();
    }

    public void setBeginTime(String begin) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.beginTime = format.parse(begin+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEndTime(String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.endTime = format.parse(end+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
