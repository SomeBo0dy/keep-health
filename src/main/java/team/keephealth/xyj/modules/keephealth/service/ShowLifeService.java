package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.*;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowLife;


/**
 * 用户动态表(ShowLife)表服务接口
 *
 * @author xyj
 * @since 2022-07-29 19:54:57
 */
public interface ShowLifeService extends IService<ShowLife> {

    ResponseResult addShowLife(ShowLifeDto showLifeDto);

    ResponseResult deleteShowLife(Long kslId);

    ResponseResult getShowLifeById(Long kslId);

    ResponseResult getShowLifeByUserId(UserSelectShowDto showDto);

    ResponseResult getShowInfoByPage(SysSelectShowDto sysSelectShowDto);

    ResponseResult getShowLife(MyShowLifeDto showLifeDto);

    ResponseResult setShowState(StateDto stateDto);

    ResponseResult sysCancelShow(Long kslId);
}

