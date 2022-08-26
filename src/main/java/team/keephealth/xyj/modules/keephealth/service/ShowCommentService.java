package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.AddCommentDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetCommentDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowComment;

/**
 * 评论表(ShowComment)表服务接口
 *
 * @author xyj
 * @since 2022-08-01 16:30:14
 */
public interface ShowCommentService extends IService<ShowComment> {

    ResponseResult commentList(GetCommentDto commentDto);

    ResponseResult addComment(AddCommentDto addCommentDto);
}

