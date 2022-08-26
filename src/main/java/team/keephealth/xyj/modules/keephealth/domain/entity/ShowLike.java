package team.keephealth.xyj.modules.keephealth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (ShowLike)表实体类
 *
 * @author xyj
 * @since 2022-08-05 15:50:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kh_show_like")
public class ShowLike extends Model<ShowLike> {

    private Long showId;

    private Long userId;


}

