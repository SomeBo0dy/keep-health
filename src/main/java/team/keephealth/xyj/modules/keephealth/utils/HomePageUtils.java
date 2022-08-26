package team.keephealth.xyj.modules.keephealth.utils;

import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.xyj.modules.keephealth.domain.dto.BMIDto;
import team.keephealth.xyj.modules.keephealth.domain.vo.BMIVo;

import java.util.Date;
import java.util.Objects;

import static team.keephealth.common.constants.BusinessConstants.*;

public class HomePageUtils {
    public static BMIVo getBMI(BMIDto bmiDto) {
        Double height = bmiDto.getHeight();
        Double weight = bmiDto.getWeight();
        if (Objects.isNull(height)) {
            throw new SystemException(AppHttpCodeEnum.USER_HEIGHT_NOT_NULL);
        }
        if (Objects.isNull(weight)) {
            throw new SystemException(AppHttpCodeEnum.USER_WEIGHT_NOT_NULL);
        }
        Double bmi = weight / Math.pow(height / 100, 2);
        BMIVo bmiVo  = new BMIVo();
        bmiVo.setBmi(bmi);
        if (bmi <= TOO_LIGHT){
            bmiVo.setState("体重不足");
        } else if (bmi <= NORMAL){
            bmiVo.setState("健康");
        } else if (bmi <= FAT) {
            bmiVo.setState("超重");
        } else {
            bmiVo.setState("肥胖");
        }
        return bmiVo;
    }
}
