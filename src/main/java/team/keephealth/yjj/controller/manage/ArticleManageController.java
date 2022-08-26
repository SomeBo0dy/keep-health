package team.keephealth.yjj.controller.manage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.CheckInfoDto;
import team.keephealth.yjj.domain.dto.manage.ArticleCheckDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.manage.ArticleManageService;

import javax.annotation.Resource;

@Api(tags = {"文章审核管理"})
@RestController
@RequestMapping("/manage/article")
public class ArticleManageController {

    @Resource
    private ArticleManageService articleManageService;

    @ApiOperation(value = "查看未审核文章列表")
    @SystemLog(businessName = "查看未审核文章列表")
    @GetMapping("/uncheckList")
    public ResultVo<T> getUncheckList(){
        return articleManageService.getList(0);
    }

    @ApiOperation(value = "查看审核未通过文章列表")
    @SystemLog(businessName = "查看审核未通过文章列表")
    @GetMapping("/unPassList")
    public ResultVo<T> getUnPassList(){
        return articleManageService.getList(2);
    }

    @ApiOperation(value = "查看审核通过文章列表")
    @SystemLog(businessName = "查看审核通过文章列表")
    @GetMapping("/passList")
    public ResultVo<T> getPassList(){
        return articleManageService.getList(1);
    }

    @ApiOperation(value = "修改审核信息")
    @SystemLog(businessName = "修改审核信息")
    @PutMapping("/updateCheck")
    public ResultVo<T> updateCheck(@RequestBody ArticleCheckDto dto){
        return articleManageService.updateCheck(dto);
    }
}
