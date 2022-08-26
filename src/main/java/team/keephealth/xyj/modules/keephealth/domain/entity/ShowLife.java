package team.keephealth.xyj.modules.keephealth.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户动态表(ShowLife)表实体类
 *
 * @author xyj
 * @since 2022-07-29 19:54:57
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_show_life")
public class ShowLife extends Model<ShowLife> {
    @TableId(type = IdType.AUTO)
    private Long kslId;
    //用户id
    private Long userId;

    private String text;
    //审核状态（0为未审核 1为通过审核）
    private String state;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}

