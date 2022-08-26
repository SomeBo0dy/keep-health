package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.article.ArticleInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.article.ArticleService;

import javax.annotation.Resource;

@Api(tags = {"文章用户操作接口"})
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @ApiOperation(value = "添加文章")
    @SystemLog(businessName = "添加文章")
    @PutMapping("/add")
    public ResultVo addArticle(@RequestBody ArticleInfoDto article){
        return articleService.addArticle(article);
    }

    @ApiOperation(value = "更改文章信息")
    @SystemLog(businessName = "更改文章信息")
    @PutMapping("/update")
    public ResultVo updateArticle(@RequestBody ArticleInfoDto article){
        return articleService.updateArticle(article);
    }

    @ApiOperation(value = "删除文章")
    @SystemLog(businessName = "删除文章")
    @DeleteMapping("/delete/{articleId}")
    public ResultVo deleteArticle(@PathVariable("articleId") Long articleId){
        return articleService.deleteArticle(articleId);
    }
}
