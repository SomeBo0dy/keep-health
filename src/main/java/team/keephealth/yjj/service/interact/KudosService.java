package team.keephealth.yjj.service.interact;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.yjj.domain.entity.articles.Kudos;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface KudosService extends IService<Kudos> {

    // 点赞
    ResultVo addKudos(Long articleId);

    // 取消赞
    ResultVo deleteKudos(Long articleId);

    // 是否点赞
    ResultVo kudosState(Long articleId);
}
