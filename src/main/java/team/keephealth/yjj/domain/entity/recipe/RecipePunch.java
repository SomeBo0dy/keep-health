package team.keephealth.yjj.domain.entity.recipe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.action.RecipePunchDto;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pu_recipe")
@AllArgsConstructor
@NoArgsConstructor
public class RecipePunch {

    // 自定义打卡

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 打卡的用户
    @Column(name = "account_id")
    private Long accountId;

    // 隶属打卡id
    @Column(name = "punch_id")
    private Long punchId;
    // 餐饮类别（未定义：0， 早餐：1， 午餐：2， 晚餐：3 ）
    private int cate;
    // 选择的食物
    private String meal;
    // 或者选择上传图片识别
    private String pict;
    // 上传食物的名称
/*
    @Column(name = "p_name")
    private String pName;
*/
    // 卡路里量
    private double kcal;

    public RecipePunch(RecipePunchDto dto){
        this.accountId = SecurityUtils.getUserId();
        this.cate = dto.getCate();
        this.meal = dto.getMeal();
        this.pict = dto.getPict();
        this.kcal = dto.getKcal();
    }

}
