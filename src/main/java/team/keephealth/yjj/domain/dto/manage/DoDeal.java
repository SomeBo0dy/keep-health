package team.keephealth.yjj.domain.dto.manage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoDeal {

    @ApiModelProperty(value = "处理的文章/评论id")
    private Long id;

    @ApiModelProperty(value = "0：违规处理（正确填写type信息）并设置文章审核不通过， 1：违规处理并删除文章（ps：评论只有删除，填01都是删除）" +
            "2：不做违规处理，也不删除举报信息， 3：不做违规处理，同时删除该文章所有举报信息")
    private Integer deal;

    @ApiModelProperty(value = "设定违规类别", notes = "详情查看 项目数据 中 举报项目列表")
    private Integer type;
}
