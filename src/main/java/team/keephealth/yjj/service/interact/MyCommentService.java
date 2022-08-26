package team.keephealth.yjj.service.interact;

import team.keephealth.yjj.domain.dto.DeleteListDto;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface MyCommentService {

    // 这些不做分页
    // 我发出的全部评论
    ResultVo mineSendAll();

    // 我收到的全部评论
    ResultVo mineReceiveAll();

    // 批量删除评论
    ResultVo deleteByList(DeleteListDto dto);

    // 未读评论数量
    ResultVo newCommentNum();

    // 未读评论列，一经调用则默认变为已读
    ResultVo newCommentList();
}
