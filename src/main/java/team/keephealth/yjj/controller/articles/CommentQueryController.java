package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.CommentQueryDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.interact.CommentQueryService;

import javax.annotation.Resource;

@Api(tags = {"评论获取接口"})
@RestController
@RequestMapping("/commentQuery")
public class CommentQueryController {

    @Resource
    private CommentQueryService commentQueryService;

    @ApiOperation(value = "评论精选1-3")
    @SystemLog(businessName = "评论精选1-3")
    @GetMapping("/select/{articleId}")
    public ResultVo highComment(@PathVariable("articleId") Long articleId){
        return commentQueryService.highComment(articleId);
    }

    @ApiOperation(value = "不包含回复的全部评论数量")
    @SystemLog(businessName = "不包含回复的全部评论数量")
    @GetMapping("/numNoReply/{articleId}")
    public ResultVo noReplyNumber(@PathVariable("articleId") Long articleId){
        return commentQueryService.getNumber(articleId);
    }

    @ApiOperation(value = "包含回复的全部评论数量")
    @SystemLog(businessName = "包含回复的全部评论数量")
    @GetMapping("/numAll/{articleId}")
    public ResultVo allNumber(@PathVariable("articleId") Long articleId){
        return commentQueryService.getNumberA(articleId);
    }

    @ApiOperation(value = "某评论回复的全部评论数量")
    @SystemLog(businessName = "某评论回复的全部评论数量")
    @GetMapping("/numReply/{commentId}")
    public ResultVo replyNumber(@PathVariable("commentId") Long commentId){
        return commentQueryService.getReplyNumber(commentId);
    }

    @ApiOperation(value = "热度排序获取全部评论")
    @SystemLog(businessName = "热度排序获取全部评论")
    @GetMapping("/byHeat")
    public ResultVo listByHeat(CommentQueryDto dto){
        return commentQueryService.queryByHeat(dto);
    }

    @ApiOperation(value = "时间排序获取全部评论")
    @SystemLog(businessName = "时间排序获取全部评论")
    @GetMapping("/byTime")
    public ResultVo listByTime(CommentQueryDto dto){
        return commentQueryService.queryByTime(dto);
    }

    @ApiOperation(value = "某评论的全部回复评论")
    @SystemLog(businessName = "某评论的全部回复评论")
    @GetMapping("/reply")
    public ResultVo listReply(CommentQueryDto dto){
        return commentQueryService.queryReply(dto);
    }
}
