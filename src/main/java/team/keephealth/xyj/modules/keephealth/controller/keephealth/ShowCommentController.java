package team.keephealth.xyj.modules.keephealth.controller.keephealth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.AddCommentDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetCommentDto;
import team.keephealth.xyj.modules.keephealth.service.ShowCommentService;

@Api(value = "ShowCommentControllerApi", tags = {"动态二级评论操作接口"})
@RestController
@RequestMapping("/comment/show")
public class ShowCommentController {
    @Autowired
    private ShowCommentService showCommentService;

    @ApiOperation(value = "获取动态评论")
    @SystemLog(businessName = "获取动态评论")
    @GetMapping
    @PreAuthorize("hasAuthority('user:comment:list')")
    public ResponseResult commentList(GetCommentDto commentDto) {
        return showCommentService.commentList(commentDto);
    }
    @ApiOperation(value = "发布动态评论")
    @SystemLog(businessName = "发布动态评论")
    @PostMapping
    @PreAuthorize("hasAuthority('user:comment:add')")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto) {
        return showCommentService.addComment(addCommentDto);
    }

}
