package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.interact.KudosQueryService;

import javax.annotation.Resource;

@Api(tags = {"文章点赞信息获取"})
@RestController
@RequestMapping("/kudos")
public class ArticleKudosController {

    @Resource
    private KudosQueryService kudosQueryService;

    @ApiOperation(value = "文章收到的赞列表")
    @SystemLog(businessName = "文章收到的赞列表")
    @GetMapping("/list/{articleId}")
    public ResultVo articleKList(@PathVariable("articleId") Long articleId){
        return kudosQueryService.getArticleKudos(articleId);
    }

    @ApiOperation(value = "用户的点赞总赞数(文章）")
    @SystemLog(businessName = "用户的点赞总赞数(文章）")
    @GetMapping("/uNumber/{accountId}")
    public ResultVo userKNum(@PathVariable("accountId") Long accountId){
        return kudosQueryService.getUserKNum(accountId);
    }

    @ApiOperation(value = "文章的总赞数")
    @SystemLog(businessName = "文章的总赞数")
    @GetMapping("/aNumber/{articleId}")
    public ResultVo articleKNum(@PathVariable("articleId") Long articleId){
        return kudosQueryService.getArticleKNum(articleId);
    }
}
