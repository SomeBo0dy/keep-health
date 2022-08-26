package team.keephealth.yjj.controller.articles;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.interact.KudosQueryService;
import team.keephealth.yjj.service.interact.KudosRecordService;

import javax.annotation.Resource;

@Api(tags = {"我的点赞数据获取"})
@RestController
@RequestMapping("/myKudos")
public class MyKudosMessageController {

    @Resource
    private KudosQueryService kudosQueryService;
    @Resource
    private KudosRecordService recordService;

    @ApiOperation(value = "我的点赞总赞数(文章）")
    @SystemLog(businessName = "我的点赞总赞数(文章）")
    @GetMapping("/myAllNum")
    public ResultVo myAllKNum(){
        return kudosQueryService.getMyKNum();
    }

    @ApiOperation(value = "我获得的赞列表(文章）")
    @SystemLog(businessName = "我获得的赞列表(文章）")
    @GetMapping("/myAllList")
    public ResultVo myAllKList(){
        return kudosQueryService.getMyKList();
    }

    @ApiOperation(value = "我收到的赞总数(文章）")
    @SystemLog(businessName = "我收到的赞总数(文章）")
    @GetMapping("/myAllANum")
    public ResultVo myAllANum(){
        return kudosQueryService.getMyANum();
    }

    @ApiOperation(value = "我收到的赞列表")
    @SystemLog(businessName = "我收到的赞列表")
    @GetMapping("/myAllAList")
    public ResultVo myAllAList(){
        return recordService.getMyRKudos();
    }

    @ApiOperation(value = "未查看的点赞消息")
    @SystemLog(businessName = "未查看的点赞消息")
    @GetMapping("/newList")
    public ResultVo myNewKList(){
        return recordService.getNewKudos();
    }

    @ApiOperation(value = "未查看的点赞消息数量")
    @SystemLog(businessName = "未查看的点赞消息数量")
    @GetMapping("/newNumber")
    public ResultVo myNewKNum(){
        return recordService.getNKudosNum();
    }
}
