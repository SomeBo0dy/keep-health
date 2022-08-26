package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CheckLikeDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.ShowLikeDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLike;


/**
 * (ShowLike)表服务接口
 *
 * @author xyj
 * @since 2022-08-05 15:50:49
 */
public interface ShowLikeService extends IService<ShowLike> {

    ResponseResult likeShow(ShowLikeDto showLikeDto);

    ResponseResult cancelLikeShow(Long kslId);

    ResponseResult checkLike(CheckLikeDto checkLikeDto);
}

