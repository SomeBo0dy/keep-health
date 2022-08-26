package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.article.MArticleKeyNumDto;
import team.keephealth.yjj.domain.dto.article.MArticleQKeyDto;
import team.keephealth.yjj.domain.dto.article.MArticleQueryDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.article.ArticleNumberService;
import team.keephealth.yjj.service.article.MyArticleQueService;

import javax.annotation.Resource;

@Api(tags = {"我的文章列表获取"})
@RestController
@RequestMapping("/myArticle")
public class MArticleController {

    @Resource
    private MyArticleQueService myArticleQueService;
    @Resource
    private ArticleNumberService articleNumberService;

    @ApiOperation(value = "获取文章总数")
    @SystemLog(businessName = "获取文章总数")
    @GetMapping("/number/{state}")
    public ResultVo getNumber(@PathVariable("state") int state){
        return articleNumberService.ANumber(state);
    }

    @ApiOperation(value = "关键词获取文章总数")
    @SystemLog(businessName = "关键词获取文章总数")
    @GetMapping("/keyNumber")
    public ResultVo getNumber(MArticleKeyNumDto dto){
        return articleNumberService.AKeyNum(dto);
    }

    @ApiOperation(value = "我的文章列表总览")
    @SystemLog(businessName = "我的文章列表总览")
    @GetMapping("/show")
    public ResultVo queryAllArticle(MArticleQueryDto dto){
        return myArticleQueService.queryMAAll(dto);
    }

    @ApiOperation(value = "总列关键词搜索")
    @SystemLog(businessName = "总列关键词搜索")
    @GetMapping("/keyword")
    public ResultVo queryByKeyword(MArticleQKeyDto dto){
        return myArticleQueService.queryMAKey(dto);
    }
}
