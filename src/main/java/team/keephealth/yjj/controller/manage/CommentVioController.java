package team.keephealth.yjj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.manage.CommentVioService;

import javax.annotation.Resource;

@Api(tags = {"评论举报管理"})
@RestController
@RequestMapping("/manage/comment")
public class CommentVioController {

    @Resource
    private CommentVioService vioService;

    @ApiOperation(value = "获取被举报的评论列表")
    @SystemLog(businessName = "获取被举报的评论列表")
    @GetMapping("/getVioList/{deal}")
    public ResultVo<T> getVioList(@PathVariable("deal") int deal){
        return vioService.getAll(deal);
    }

    @ApiOperation(value = "查看评论的所有举报信息")
    @SystemLog(businessName = "查看评论的所有举报信息")
    @GetMapping("/getCommentVio/{commentId}")
    public ResultVo<T> getCommentVio(@PathVariable("commentId") Long commentId){
        return vioService.getArticleVio(commentId);
    }

    @ApiOperation(value = "处理评论举报")
    @SystemLog(businessName = "处理评论举报")
    @PutMapping("/dealVio")
    public ResultVo<T> dealVio(@RequestBody DoDeal dto){
        return vioService.setDeal(dto);
    }
}
