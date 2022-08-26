package team.keephealth.yjj.domain.entity.recipe;

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
@TableName("p_recipe_dtl")
@AllArgsConstructor
@NoArgsConstructor
public class RecipePlanDetail {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 计划编号
    private Long pid;

    // 早餐内容
    private Long breakfast;
    // 午餐内容
    private Long lunch;
    // 晚餐内容
    private Long dinner;
}
