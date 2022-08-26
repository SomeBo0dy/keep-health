package team.keephealth.yjj.domain.vo.articles;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ArticleBriefVo {

    private Long id;
    private String account;
    private String title;
    private String brief;
    private String kudos;
    private Date createTime;
    private Date updateTime;
}
