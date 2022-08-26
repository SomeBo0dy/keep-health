package team.keephealth.yjj.domain.vo.articles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {

    @ApiModelProperty(name = "文章id")
    private Long id;
    // 账号
    @ApiModelProperty(name = "账号")
    private String account;
    // 账号id
    @ApiModelProperty(name = "账号id")
    private Long accountId;
    // 文章内容id
    @ApiModelProperty(name = "文章内容id")
    private Long contentId;
    // 标题
    @ApiModelProperty(name = "标题")
    private String title;
    // 简要
    @ApiModelProperty(name = "简要")
    private String brief;
    // 审核: 0未审核， 1通过， 2不通过
    @ApiModelProperty(name = "审核: 0未审核， 1通过， 2不通过")
    private int articleCheck;
    // 审核评价
    @ApiModelProperty(name = "审核评价")
    private String checkOpinion;

    // 当前用户是否点赞
    @ApiModelProperty(name = "当前用户是否点赞")
    private Boolean isKudos;
    // 点赞
    @ApiModelProperty(name = "点赞")
    private int kudosNumber;
    // 添加时间
    @ApiModelProperty(name = "添加时间")
    private Date createTime;
    // 修改时间
    @ApiModelProperty(name = "修改时间")
    private Date updateTime;
}
