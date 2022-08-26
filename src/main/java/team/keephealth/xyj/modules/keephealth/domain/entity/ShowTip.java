package team.keephealth.xyj.modules.keephealth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态举报表(ShowTip)表实体类
 *
 * @author xyj
 * @since 2022-08-04 10:34:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_show_tip")
public class ShowTip extends Model<ShowTip> {
    @TableId(type = IdType.AUTO)
    private Long kstId;
    //动态id
    private Long kslId;

    //举报内容
    private String tipContent;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
