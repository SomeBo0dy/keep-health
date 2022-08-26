package team.keephealth.yjj.domain.entity.articles;

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
@TableName("msg_kudos")
@AllArgsConstructor
@NoArgsConstructor
public class KudosRecord {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 被赞账号id
    @Column(name = "account_id")
    private Long accountId;
    // 文章赞信息的id
    @Column(name = "article_kudos")
    private Long articleKudos;
    // 评论赞信息的id
    @Column(name = "comment_kudos")
    private Long commentKudos;
    // 已读 0:未 1：已
    @Column(name = "msg_read")
    private int msgRead;

    // 文章点赞信息添加
    public KudosRecord(Kudos kudos){
        this.accountId = kudos.getAccountId();
        this.articleKudos = kudos.getId();
        this.msgRead = 0;
    }

    // 评论点赞信息添加
    public KudosRecord(CommentKudos commentKudos){
        this.accountId = commentKudos.getAccountId();
        this.commentKudos = commentKudos.getId();
        this.msgRead = 0;
    }
}
