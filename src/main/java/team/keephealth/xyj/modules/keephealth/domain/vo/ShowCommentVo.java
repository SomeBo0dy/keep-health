package team.keephealth.xyj.modules.keephealth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowCommentVo {
    private Long id;
    //文章id
    private Long showId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复目标评论的用户名
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private String userName;


    private Date createTime;

    private List<ShowCommentVo> children;
}
