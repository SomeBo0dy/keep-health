package team.keephealth.yjj.domain.entity.recipe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("p_recipe")
@AllArgsConstructor
@NoArgsConstructor
public class RecipePlan {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 发表用户id
    private Long accountId;
    // 碳水化合物摄入量g
    @Column(name = "carbs_wt")
    private Double carbsWt;
    // 蛋白质摄入量g
    @Column(name = "pro_wt")
    private Double proWt;
    // 脂肪摄入量g
    @Column(name = "fat_wt")
    private Double fatWt;
    // 标题
    private String title;
    // 总卡路里摄入量
    private double kcal;
    // 描述
    private String msg;
    // 发表时间
    @Column(name = "add_time")
    private Date addTime;

    // 添加计划
    public RecipePlan(RecipePlanInfoDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.carbsWt = dto.getCarbs();
        this.proWt = dto.getPro();
        this.fatWt = dto.getFat();
        this.title = dto.getTitle();
        this.kcal = dto.getKcal();
        this.msg = dto.getMsg();
        this.addTime = new Date();
    }
}
