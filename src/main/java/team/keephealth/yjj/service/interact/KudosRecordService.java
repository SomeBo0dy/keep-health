package team.keephealth.yjj.service.interact;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.entity.articles.KudosRecord;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface KudosRecordService extends IService<KudosRecord> {

    // 不做分页
    // 未查看的点赞消息, 一但访问即表示已读, 或者需要另外的接口表示？
    ResultVo getNewKudos();

    // 未查看的点赞消息数量
    ResultVo getNKudosNum();

    // 我收到的赞列表, 包含全部已读未读消息，访问该方法后未读消息自动改为已读
    ResultVo getMyRKudos();


}
