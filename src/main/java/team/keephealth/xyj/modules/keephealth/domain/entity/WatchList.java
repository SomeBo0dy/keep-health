package team.keephealth.xyj.modules.keephealth.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注列表(WatchList)表实体类
 *
 * @author xyj
 * @since 2022-07-29 14:30:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_watch_list")
public class WatchList extends Model<WatchList> {
    //粉丝
    private Long followerId;
    //被关注者
    private Long watchedId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public WatchList(Long followerId, Long userId) {
        this.followerId = followerId;
        this.watchedId = userId;
    }
}

