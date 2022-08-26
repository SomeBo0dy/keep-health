package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.GetTipPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TipShowDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.ShowTip;

/**
 * 动态举报表(ShowTip)表服务接口
 *
 * @author xyj
 * @since 2022-08-04 10:34:50
 */
public interface ShowTipService extends IService<ShowTip> {

    ResponseResult addShowLifeTip(TipShowDto tipShowDto);

    ResponseResult tipList(GetPageDto getTipDto);

    ResponseResult getTipById(Long id);

    ResponseResult deleteTip(Long kstId);

    ResponseResult getTipByShowId(GetTipPageDto tipPageDto);

    ResponseResult deleteTipByKslId(Long kslId);
}

