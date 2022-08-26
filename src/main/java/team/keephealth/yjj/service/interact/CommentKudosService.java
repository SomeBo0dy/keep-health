package team.keephealth.yjj.service.interact;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.entity.articles.CommentKudos;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface CommentKudosService extends IService<CommentKudos> {

    // 点赞
    ResultVo addKudos(Long commentId);

    // 取消赞
    ResultVo deleteKudos(Long commentId);

    // 是否点赞
    ResultVo kudosState(Long commentId);
}
