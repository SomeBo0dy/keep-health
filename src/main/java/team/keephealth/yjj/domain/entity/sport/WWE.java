package team.keephealth.yjj.domain.entity.sport;

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
@TableName("sys_wwe")
@AllArgsConstructor
@NoArgsConstructor
public class WWE {

    // 运动项目记录

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 运动名称
    private String wwe;
    // 运动每分钟消耗能量
    private double energy;
    // 运动图片
    private String pict;
}
