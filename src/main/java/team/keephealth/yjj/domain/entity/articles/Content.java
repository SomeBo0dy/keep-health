package team.keephealth.yjj.domain.entity.articles;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import team.keephealth.yjj.domain.dto.article.ArticleInfoDto;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_content")
@AllArgsConstructor
@NoArgsConstructor
public class Content {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;
    // 账号
    private String account;
    // 账号id
    @Column(name = "account_id")
    private Long accountId;
    // 标题
    private String title;
    // 内容
    private String words;
    // 图片
    private String pict;

    public Content(ArticleInfoDto articleInfoDto){
        this.title = articleInfoDto.getTitle();
        this.words = articleInfoDto.getWords();
        this.pict = articleInfoDto.getPict();
    }

    public void setUser(String account, Long accountId){
        this.account = account;
        this.accountId = accountId;
    }
}
