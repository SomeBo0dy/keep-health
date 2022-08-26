package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.manage.InformDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.article.ContentService;
import team.keephealth.yjj.service.interact.KudosService;

import javax.annotation.Resource;

@Api(tags = {"文章信息管理接口"})
@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    private ContentService contentService;
    @Resource
    private KudosService kudosService;

    @ApiOperation(value = "获取文章内容")
    @SystemLog(businessName = "获取文章内容")
    @GetMapping("/{articleId}")
    public ResultVo getContent(@PathVariable("articleId") Long articleId){
        return contentService.getDetail(articleId);
    }

    @ApiOperation(value = "举报文章")
    @SystemLog(businessName = "举报文章")
    @PutMapping("/inform")
    public ResultVo informArticle(@RequestBody InformDto dto){
        return contentService.complaintArticle(dto);
    }

    @ApiOperation(value = "点赞")
    @SystemLog(businessName = "点赞")
    @PutMapping("/kudos/{articleId}")
    public ResultVo addKudos(@PathVariable("articleId") Long articleId){
        return kudosService.addKudos(articleId);
    }

    @ApiOperation(value = "取消点赞")
    @SystemLog(businessName = "取消点赞")
    @DeleteMapping("/kudosDel/{articleId}")
    public ResultVo deleteKudos(@PathVariable("articleId") Long articleId){
        return kudosService.deleteKudos(articleId);
    }

    @ApiOperation(value = "查询是否点赞")
    @SystemLog(businessName = "查询是否点赞")
    @GetMapping("/isKudos/{articleId}")
    public ResultVo isKudos(@PathVariable("articleId") Long articleId){
        return kudosService.kudosState(articleId);
    }
}
