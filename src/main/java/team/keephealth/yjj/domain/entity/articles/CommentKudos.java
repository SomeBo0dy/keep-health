package team.keephealth.yjj.domain.entity.articles;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_com_kudos")
@AllArgsConstructor
@NoArgsConstructor
public class CommentKudos {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 被赞的评论id
    @Column(name = "comment_id")
    private Long commentId;

    // 点赞账号的id
    @Column(name = "visitor_id")
    private Long visitorId;

    // 被赞账号id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "add_time")
    private Date addTime;

    public CommentKudos(Comment comment){
        this.commentId = comment.getId();
        this.visitorId = SecurityUtils.getUserId();
        this.accountId = comment.getAccountId();
        this.addTime = new Date();
    }
}
