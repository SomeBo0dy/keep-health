package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.article.ArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.ArticleQueryDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.article.ArticleNumberService;
import team.keephealth.yjj.service.article.ArticleQueryService;

import javax.annotation.Resource;

@Api(tags = {"全部文章列表获取"})
@RestController
@RequestMapping("/allArticle")
public class AllArticleController {

    @Resource
    private ArticleQueryService articleQueryService;
    @Resource
    private ArticleNumberService articleNumberService;

    @ApiOperation(value = "获取文章总数")
    @SystemLog(businessName = "获取文章总数")
    @GetMapping("/number")
    public ResultVo getNumber(){
        return articleNumberService.Number();
    }

    @ApiOperation(value = "关键词获取文章总数")
    @SystemLog(businessName = "关键词获取文章总数")
    @GetMapping("/keyNumber/{keyword}")
    public ResultVo getNumber(@PathVariable("keyword") String keyword){
        return articleNumberService.KeyNumber(keyword);
    }

    @ApiOperation(value = "文章列表总览")
    @SystemLog(businessName = "文章列表总览")
    @GetMapping("/show")
    public ResultVo queryAllArticle(ArticleQueryDto articleQueryDto){
        return articleQueryService.queryByAll(articleQueryDto);
    }

    @ApiOperation(value = "总列关键词搜索")
    @SystemLog(businessName = "总列关键词搜索")
    @GetMapping("/keyword")
    public ResultVo queryByKeyword(ArticleQKeyDto articleQKeyDto){
        return articleQueryService.queryByKeyword(articleQKeyDto);
    }
}
