package team.keephealth.xyj.modules.keephealth.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评论表(ShowComment)表实体类
 *
 * @author xyj
 * @since 2022-08-01 16:30:14
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_show_comment")
public class ShowComment extends Model<ShowComment> {
    @TableId(type = IdType.AUTO)
    private Long id;
    //文章id
    private Long showId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
