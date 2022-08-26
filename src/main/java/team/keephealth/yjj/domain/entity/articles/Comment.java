package team.keephealth.yjj.domain.entity.articles;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.CommentInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 评论人昵称
    private String account;
    // 评论人id
    @Column(name = "account_id")
    private Long accountId;
    // 文章id
    @Column(name = "article_id")
    private Long articleId;
    // 评论内容
    private String words;
    // 回复的评论id
    private Long reply;
    // 评论点赞数
    @Column(name = "kudos_number")
    private int kudosNumber;
    // 评论添加时间
    @Column(name = "add_time")
    private Date addTime;

    // 添加评论
    public void addC(CommentInfoDto dto){
        addUser();
        this.articleId = dto.getArticleId();
        this.words = dto.getWords();
        this.kudosNumber = 0;
        this.addTime = new Date();
    }

    //添加回复的评论
    public void addReply(CommentInfoDto dto){
        addUser();
        this.articleId = dto.getArticleId();
        this.words = dto.getWords();
        this.reply = dto.getReply();
        this.kudosNumber = 0;
        this.addTime = new Date();
    }

    // 添加用户信息
    public void addUser(){
        this.account = SecurityUtils.getLoginUser().getUsername();
        this.accountId = SecurityUtils.getUserId();
    }
}
