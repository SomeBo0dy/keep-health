package team.keephealth.yjj.service.action;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.action.PunchDeleteDto;
import team.keephealth.yjj.domain.dto.action.RecipePunchDto;
import team.keephealth.yjj.domain.dto.action.SportPunchDto;
import team.keephealth.yjj.domain.entity.action.Punch;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface PunchService {

    // 添加运动打卡
    ResultVo<T> addSportPunch(SportPunchDto dto);

    // 添加减肥餐打卡
    ResultVo<T> addRecipePunch(RecipePunchDto dto);

    // 删除打卡记录
    ResultVo<T> deletePunch(PunchDeleteDto dto);

    // 删除一日所有打卡记录 yyyy-MM-dd
    ResultVo<T> deleteAll(String day);
}
