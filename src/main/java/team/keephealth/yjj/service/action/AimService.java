package team.keephealth.yjj.service.action;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.action.AimInfoDto;
import team.keephealth.yjj.domain.entity.action.Aim;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface AimService extends IService<Aim> {

    // 获取今日卡路里记录
    ResultVo<T> kcalMessage();

    // 获取目标列表(全身减脂,瘦腰,瘦胳膊...及编号）
    ResultVo<T> aims();

    // 更新目标
    ResultVo<T> updateAim(AimInfoDto dto);
}
