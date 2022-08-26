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
import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_kudos")
@TableName("sys_kudos")
@AllArgsConstructor
@NoArgsConstructor
public class Kudos {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    // 点赞账号的id
    @Column(name = "visitor_id")
    private Long visitorId;

    // 被赞账号id
    @Column(name = "account_id")
    private Long accountId;

    // 被赞文章id
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "add_time")
    private Date addTime;

    public void setArticleMes(Article article){
        this.accountId = article.getAccountId();
        this.articleId = article.getId();
        this.addTime = new Date();
    }
}
