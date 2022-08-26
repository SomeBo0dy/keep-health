package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.TimeQueryMy;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface SportQueryService extends IService<Sport> {

    // 获取我的运动计划列表（无分页
    ResultVo<T> queryAll();

    // 查询起止时间范围内的计划
    ResultVo<T> queryByTime(TimeQueryMy dto);

    // 查询当前时间的运动计划
    ResultVo<T> queryNow();
}
