package team.keephealth.yjj.domain.entity.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.dto.manage.InformDto;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("adm_comment")
@AllArgsConstructor
@NoArgsConstructor
public class InformComment {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 被举报的评论id
    @Column(name = "comment_id")
    private Long commentId;
    /**
     * type : 0: 其他，
     *        1: 违法违规， 2: 色情低俗， 3: 赌博诈骗
     *        4: 人身攻击， 5: 侵犯隐私，
     *        6: 广告， 7: 引战， 8: 不良信息， 9: 内容不相关， 10: 刷屏
     */
    private int type;
    // 附加信息
    private String message;
    // 消息处理 0：未处理， 1：已处理
    private int deal;

    public InformComment(InformDto dto){
        this.commentId = dto.getInformId();
        this.type = dto.getType();
        this.message = dto.getMessage();
        this.deal = 0;
    }
}
