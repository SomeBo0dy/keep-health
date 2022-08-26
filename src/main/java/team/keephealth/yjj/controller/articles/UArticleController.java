package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.article.ArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.ArticleQueryDto;
import team.keephealth.yjj.domain.dto.article.UArticleKeyNumDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.article.ArticleNumberService;
import team.keephealth.yjj.service.article.ArticleQueryService;

import javax.annotation.Resource;

@Api(tags = {"用户的文章列表获取"})
@RestController
@RequestMapping("/uArticle")
public class UArticleController {

    @Resource
    private ArticleQueryService articleQueryService;
    @Resource
    private ArticleNumberService articleNumberService;

    @ApiOperation(value = "获取用户文章总数")
    @SystemLog(businessName = "获取用户文章总数")
    @GetMapping("/number/{accountId}")
    public ResultVo getNumber(@PathVariable("accountId") Long accountId){
        return articleNumberService.UNumber(accountId);
    }

    @ApiOperation(value = "用户关键词文章总数")
    @SystemLog(businessName = "用户关键词文章总数")
    @GetMapping("/keyNumber")
    public ResultVo getNumber(UArticleKeyNumDto dto){
        return articleNumberService.KeyUNumber(dto);
    }

    @ApiOperation(value = "文章列表总览")
    @SystemLog(businessName = "文章列表总览")
    @GetMapping("/show")
    public ResultVo queryAllArticle(ArticleQueryDto articleQueryDto){
        return articleQueryService.queryByUser(articleQueryDto);
    }

    @ApiOperation(value = "总列关键词搜索")
    @SystemLog(businessName = "总列关键词搜索")
    @GetMapping("/keyword")
    public ResultVo queryByKeyword(ArticleQKeyDto articleQKeyDto){
        return articleQueryService.queryByUKey(articleQKeyDto);
    }
}
