package team.keephealth.yjj.domain.dto.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleQueryDto {

    /**
     * @param kind : 0-最后更改时间， 1-发布时间， 2-点赞数
     * @param direction ：0-倒序， 1-正序
     * @return
     */
    @ApiModelProperty(value = "0-最后更改时间， 1-发布时间， 2-点赞数")
    private int kind;
    @ApiModelProperty(value = "0-倒序， 1-正序")
    private int direction;
    // 用户id
    @ApiModelProperty(value = "用户id")
    private Long id;
    // 每页数据量
    @ApiModelProperty(value = "每页数据量")
    private int pageSize;
    // 当前页数
    @ApiModelProperty(value = "当前页数")
    private int currentPage;

    //发布时间
    @ApiModelProperty(value = "开始时间  格式（yyyy-MM-dd）")
    private String startTime;
    @ApiModelProperty(value = "结束时间  格式（yyyy-MM-dd）")
    private String endTime;
}
