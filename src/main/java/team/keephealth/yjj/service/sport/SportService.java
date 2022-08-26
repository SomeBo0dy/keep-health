package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.sport.SportInfoDto;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface SportService extends IService<Sport> {

    // 添加运动计划
    ResultVo<T> addSport(SportInfoDto dto);

    // 更改运动计划
    ResultVo<T> updateSport(SportInfoDto dto);

    // 获取某个运动计划信息(不知道有什么用？
    ResultVo<T> getDetail(Long sportId);

    // 删除运动计划
    ResultVo<T> deleteSport(Long sportId);

}
