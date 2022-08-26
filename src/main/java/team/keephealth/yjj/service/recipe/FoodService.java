package team.keephealth.yjj.service.recipe;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface FoodService extends IService<Foods> {

    // 获取公开食物列表
    ResultVo<T> getFoods();
}
