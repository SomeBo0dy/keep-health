package team.keephealth.yjj.service.recipe.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.recipe.FoodsMapper;
import team.keephealth.yjj.service.recipe.FoodService;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodsMapper, Foods> implements FoodService {

    @Autowired
    private FoodsMapper foodsMapper;

    @Override
    public ResultVo<T> getFoods() {
        QueryWrapper<Foods> wrapper = new QueryWrapper<>();
        wrapper.isNull("belong");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", foodsMapper.selectList(wrapper));
    }
}
