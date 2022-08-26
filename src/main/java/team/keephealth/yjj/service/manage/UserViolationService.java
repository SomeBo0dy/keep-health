package team.keephealth.yjj.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.manage.UserViolation;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface UserViolationService extends IService<UserViolation> {

    // 获取用户违规数据
    ResultVo<T> getVio(Long accountId);

    // 获取实例
    UserViolation getUserVio(Long userId);

    // 计算违规并封号
    boolean countVio(UserViolation userViolation);
}
