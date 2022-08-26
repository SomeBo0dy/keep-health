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
@TableName("msg_comment")
@AllArgsConstructor
@NoArgsConstructor
public class CommentRecord {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 收到消息的用户
    @Column(name = "account_id")
    private Long accountId;

    // 文章的评论
    @Column(name = "article_comment")
    private Long articleComment;

    // 评论的回复
    @Column(name = "comment_reply")
    private Long commentReply;

    // 是否已读 0：未 1：已
    @Column(name = "msg_read")
    private int msgRead;

    public void setComment(Long accountId, Long commentId){
        this.accountId = accountId;
        this.articleComment = commentId;
        this.msgRead = 0;
    }

    public void setReply(Long accountId, Long commentId){
        this.accountId = accountId;
        this.commentReply = commentId;
        this.msgRead = 0;
    }
}
