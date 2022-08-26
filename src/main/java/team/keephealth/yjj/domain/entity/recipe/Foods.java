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
@TableName("sys_foods")
@AllArgsConstructor
@NoArgsConstructor
public class Foods {

    // 餐饮食物详情

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 食物名字
    @Column(name = "food_name")
    private String foodName;
    // 该套餐热量
    private double kcal;
    // 介绍
    private String words;
    // 图片
    private String pict;
    // 关联个人添加
    private Long belong;

    public Foods(String name, double kcal, String words, String pict, Long bl){
        this.foodName = name;
        this.kcal = kcal;
        this.words = words;
        this.pict = pict;
        this.belong = bl;
    }

    public Foods(String name, double kcal, String words, String pict){
        this.foodName = name;
        this.kcal = kcal;
        this.words = words;
        this.pict = pict;
    }

}
