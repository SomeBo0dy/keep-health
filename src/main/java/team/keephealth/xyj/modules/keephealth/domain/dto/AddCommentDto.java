package team.keephealth.xyj.modules.keephealth.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCommentDto {
    //动态id
    @ApiModelProperty(value = "动态id")
    private Long showId;
    //根评论id
    @ApiModelProperty(value = "根评论id，本身是根评论则传-1")
    private Long rootId;
    //评论内容
    @ApiModelProperty(value = "评论内容")
    private String content;
    //所回复的目标评论的userid
    @ApiModelProperty(value = "所回复的目标评论的userid,本身是根评论则传-1")
    private Long toCommentUserId;
    //回复目标评论id
    @ApiModelProperty(value = "回复目标评论id,本身是根评论则传-1")
    private Long toCommentId;

}
