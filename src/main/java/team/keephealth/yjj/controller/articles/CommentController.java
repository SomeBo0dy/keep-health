package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.CommentInfoDto;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.interact.CommentKudosService;
import team.keephealth.yjj.service.interact.CommentService;

import javax.annotation.Resource;

@Api(tags = {"评论操作接口"})
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private CommentKudosService kudosService;

    @ApiOperation(value = "添加评论")
    @SystemLog(businessName = "添加评论")
    @PutMapping("/add")
    public ResultVo addComment(@RequestBody CommentInfoDto dto){
        return commentService.addComment(dto);
    }

    @ApiOperation(value = "回复评论")
    @SystemLog(businessName = "回复评论")
    @PutMapping("/reply")
    public ResultVo replyComment(@RequestBody CommentInfoDto dto){
        return commentService.replyComment(dto);
    }

    @ApiOperation(value = "删除评论")
    @SystemLog(businessName = "删除评论")
    @DeleteMapping("/delete/{commentId}")
    public ResultVo deleteComment(@PathVariable("commentId") Long commentId){
        return commentService.deleteComment(commentId);
    }

    @ApiOperation(value = "举报评论")
    @SystemLog(businessName = "举报评论")
    @PutMapping("/inform")
    public ResultVo informComment(@RequestBody InformDto inform){
        return commentService.complaintComment(inform);
    }

    @ApiOperation(value = "点赞评论")
    @SystemLog(businessName = "点赞评论")
    @PutMapping("/addKudos/{commentId}")
    public ResultVo kudosComment(@PathVariable("commentId") Long commentId){
        return kudosService.addKudos(commentId);
    }

    @ApiOperation(value = "取消点赞")
    @SystemLog(businessName = "取消点赞")
    @DeleteMapping("/delKudos/{commentId}")
    public ResultVo deleteKudos(@PathVariable("commentId") Long commentId){
        return kudosService.deleteKudos(commentId);
    }

    @ApiOperation(value = "查询当前用户是否点赞该评论")
    @SystemLog(businessName = "查询当前用户是否点赞该评论")
    @GetMapping("/isKudos/{commentId}")
    public ResultVo isKudos(@PathVariable("commentId") Long commentId){
        return kudosService.kudosState(commentId);
    }
}
