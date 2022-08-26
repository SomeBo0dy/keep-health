package team.keephealth.yjj.service.sport;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.sport.WWE;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface WWEService extends IService<WWE> {

    // 获取运动项目列表
    ResultVo<T> getWWE();
}
