package team.keephealth.yjj.service.recipe.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.recipe.PlanChooseDto;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.entity.recipe.RecipePlanDetail;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.recipe.RecipeMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanDetailMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanMapper;
import team.keephealth.yjj.service.recipe.RecipePlanCService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class RecipePlanCServiceImpl extends ServiceImpl<RecipePlanMapper, RecipePlan> implements RecipePlanCService {

    @Autowired
    private RecipePlanMapper planMapper;
    @Autowired
    private PlanChooseMapper chooseMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipePlanDetailMapper detailMapper;


    @Override
    public ResultVo<T> choosePlan(PlanChooseDto dto) {
        if (dto.getPlanId() == null || dto.getPlanId() < 1 || planMapper.selectById(dto.getPlanId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+dto.getPlanId(), null);
        if (dto.getDays() < 0) dto.setDays(0);
        Long id = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        PlanChoose c = chooseMapper.selectOne(queryWrapper);
        if (!(c == null || c.getRecipePlan() == null || c.getRecipePlan() < 1))
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_ERROR, JSONObject.toJSONString(c), null);
        if (c == null){
            PlanChoose choose = new PlanChoose();
            choose.setAccountId(id);
            choose.setRecipePlan(dto.getPlanId());
            if ( chooseMapper.insert(choose) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(choose), null);
        }else {
            c.setRecipePlan(dto.getPlanId());
            if (chooseMapper.updateById(c) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(c), null);
        }

        RecipePlan plan = planMapper.selectById(dto.getPlanId());
        QueryWrapper<RecipePlanDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", dto.getPlanId());
        RecipePlanDetail detail = detailMapper.selectOne(wrapper);
        Recipe recipe = new Recipe(plan, detail);
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String begin = p.format(new Date());
        String end = begin;
        try {
            calendar.setTime(p.parse(begin));
            calendar.add(Calendar.DATE, dto.getDays());
            end = p.format(calendar.getTime());
        } catch (ParseException e) {
            log.debug(String.valueOf(e));
        }
        recipe.setBeginTime(begin);
        recipe.setEndTime(end);
        if (recipeMapper.insert(recipe) > 0)
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(recipe), null);
    }

    @Override
    public ResultVo<T> deleteChoose(Long planId) {
        if (planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        Long id = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recipe_plan", planId);
        queryWrapper.eq("account_id", id);
        PlanChoose c = chooseMapper.selectOne(queryWrapper);
        if (c == null)
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_NOR, "id : "+planId, null);
        QueryWrapper<Recipe> delete = new QueryWrapper<>();
        delete.eq("account_id", id);
        delete.eq("plan_id", planId);
        if (recipeMapper.delete(delete) > 0) {
            chooseMapper.clearRecipe(id);
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "", null);
    }
}
