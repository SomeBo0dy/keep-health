package team.keephealth.yjj.domain.entity.articles;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_article")
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;
    // 账号
    private String account;
    // 账号id
    @Column(name = "account_id")
    private Long accountId;
    // 文章内容id
    @Column(name = "content_id")
    private Long contentId;
    // 标题
    private String title;
    // 简要
    private String brief;
    // 审核: 0未审核， 1通过， 2不通过
    @Column(name = "article_check")
    private int articleCheck;
    // 审核评价
    @Column(name = "check_opinion")
    private String checkOpinion;
    // 点赞
    @Column(name = "kudos_number")
    private int kudosNumber;
    // 添加时间
    @Column(name = "create_time")
    private Date createTime;
    // 修改时间
    @Column(name = "update_time")
    private Date updateTime;

    // 更改
    public Article(Long id, String title, String brief){
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.articleCheck = 0;
        this.updateTime = new Date();
    }

    // 添加
    public Article(String account, Long accountId, String title, String brief, Date date){
        this.account = account;
        this.accountId = accountId;
        this.title = title;
        this.brief = brief;
        this.articleCheck = 0;
        this.kudosNumber = 0;
        this.createTime = date;
        this.updateTime = date;
    }

    // 更改审核信息
    public Article(ArticleCheckDto dto){
        this.id = dto.getArticleId();
        this.articleCheck = dto.getCheck();
        this.checkOpinion = dto.getOpinion();
    }
}
