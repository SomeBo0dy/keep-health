package team.keephealth.yjj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.manage.DoDeal;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.manage.ArticleVioService;

import javax.annotation.Resource;

@Api(tags = {"文章举报管理"})
@RestController
@RequestMapping("/manage/article")
public class ArticleVioController {

    @Resource
    private ArticleVioService vioService;

    @ApiOperation(value = "获取被举报的文章列表")
    @SystemLog(businessName = "获取被举报的文章列表")
    @GetMapping("/getVioList/{deal}")
    public ResultVo<T> getVioList(@PathVariable("deal") int deal){
        return vioService.getAll(deal);
    }

    @ApiOperation(value = "查看文章的所有举报信息")
    @SystemLog(businessName = "查看文章的所有举报信息")
    @GetMapping("/getArticleVio/{articleId}")
    public ResultVo<T> getArticleVio(@PathVariable("articleId") Long articleId){
        return vioService.getArticleVio(articleId);
    }

    @ApiOperation(value = "处理文章举报")
    @SystemLog(businessName = "处理文章举报")
    @PutMapping("/dealVio")
    public ResultVo<T> dealVio(@RequestBody DoDeal dto){
        return vioService.setDeal(dto);
    }
}
