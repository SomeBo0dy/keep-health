package team.keephealth.yjj.service.recipe.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanQDto;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.vo.RecipePlanQVo;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanDetailMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanMapper;
import team.keephealth.yjj.service.recipe.RecipePlanQueryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipePlanQServiceImpl extends ServiceImpl<RecipePlanMapper, RecipePlan> implements RecipePlanQueryService {

    @Autowired
    private RecipePlanMapper planMapper;
    @Autowired
    private RecipePlanServiceImpl planService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanChooseMapper chooseMapper;

    @Override
    public ResultVo<T> queryAll(RecipePlanQDto dto) {
        if (dto.getUp() < dto.getDown())
            return new ResultVo<>(AppHttpCodeEnum.DATA_KCAL_RANGE_ERROR, "up : "+ dto.getUp()
                    +"\ndown : "+ dto.getDown(), null );
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", query(dto, false));
    }

    @Override
    public ResultVo<T> queryByAc(RecipePlanQDto dto) {
        if (dto.getAccountId() < 1 || userMapper.selectById(dto.getAccountId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST, "id : "+dto.getAccountId(), null);
        if (dto.getUp() < dto.getDown())
            return new ResultVo<>(AppHttpCodeEnum.DATA_KCAL_RANGE_ERROR, "up : "+ dto.getUp()
                    +"\ndown : "+ dto.getDown(), null );
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", query(dto, true));
    }

    public List<RecipePlanQVo> query(RecipePlanQDto dto, boolean byUser){
        QueryWrapper<RecipePlan> wrapper = new QueryWrapper<>();
        if (byUser)
            wrapper.eq("account_id", dto.getAccountId());
        if (dto.getDown() > 0)
            wrapper.ge("kcal", dto.getDown());
        if (dto.getUp() > 0)
            wrapper.le("kcal", dto.getUp());
        wrapper.orderByDesc("add_time");
        List<RecipePlan> l = planMapper.selectList(wrapper);
        List<RecipePlanQVo> list = new ArrayList<>();
        for (RecipePlan plan : l) {
            RecipePlanInfoDto vo = new RecipePlanInfoDto(plan);
            vo = planService.setMeals(vo, plan.getId());
            RecipePlanQVo qVo = new RecipePlanQVo();
            BeanUtils.copyProperties(vo, qVo);
            QueryWrapper<PlanChoose> chooseQuery = new QueryWrapper<>();
            chooseQuery.eq("recipe_plan", vo.getPlanId());
            chooseQuery.eq("account_id", SecurityUtils.getUserId());
            qVo.setChoose(chooseMapper.selectOne(chooseQuery) != null);
            list.add(qVo);
        }
        return list;
    }
}
