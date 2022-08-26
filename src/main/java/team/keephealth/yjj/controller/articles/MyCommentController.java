package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.DeleteListDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.interact.CommentService;
import team.keephealth.yjj.service.interact.MyCommentService;

import javax.annotation.Resource;

@Api(tags = {"我的评论管理"})
@RestController
@RequestMapping("/myComment")
public class MyCommentController {

    @Resource
    private MyCommentService myCommentService;
    @Resource
    private CommentService commentService;

    @ApiOperation(value = "我发出的全部评论")
    @SystemLog(businessName = "我发出的全部评论")
    @GetMapping("/allSend")
    public ResultVo allSend(){
        return myCommentService.mineSendAll();
    }

    @ApiOperation(value = "我收到的全部评论")
    @SystemLog(businessName = "我收到的全部评论")
    @GetMapping("/allReceive")
    public ResultVo allReceive(){
        return myCommentService.mineReceiveAll();
    }

    @ApiOperation(value = "批量删除评论")
    @SystemLog(businessName = "批量删除评论")
    @DeleteMapping("/deleteList")
    public ResultVo deleteList(@RequestBody DeleteListDto listDto){
        return myCommentService.deleteByList(listDto);
    }

    @ApiOperation(value = "未读评论数量")
    @SystemLog(businessName = "未读评论数量")
    @GetMapping("/newComNum")
    public ResultVo newComNum(){
        return myCommentService.newCommentNum();
    }

    @ApiOperation(value = "未读评论列")
    @SystemLog(businessName = "未读评论列")
    @GetMapping("/newComList")
    public ResultVo newComList(){
        return myCommentService.newCommentList();
    }

    @ApiOperation(value = "跳转评论所在位置")
    @SystemLog(businessName = "跳转评论所在位置")
    @GetMapping("/visitCom/{commentId}")
    public ResultVo visitComment(@PathVariable("commentId") Long commentId){
        return commentService.visitComment(commentId);
    }
}
