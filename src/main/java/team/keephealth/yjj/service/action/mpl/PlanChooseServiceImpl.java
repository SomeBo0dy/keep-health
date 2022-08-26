package team.keephealth.yjj.service.action.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.recipe.RecipeMapper;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.service.action.PlanChooseService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PlanChooseServiceImpl extends ServiceImpl<PlanChooseMapper, PlanChoose> implements PlanChooseService {

    @Autowired
    private PlanChooseMapper chooseMapper;
    @Autowired
    private SportMapper sportMapper;
    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public ResultVo<T> isUseSport() {
        Long userId = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> chooseQuery = new QueryWrapper<>();
        chooseQuery.eq("account_id", userId);
        PlanChoose choose = chooseMapper.selectOne(chooseQuery);
        if (choose == null || choose.getSportPlan() == null || choose.getSportPlan() < 1)
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_NOR, "", null);

        QueryWrapper<Sport> sportQuery = new QueryWrapper<>();
        sportQuery.eq("account_id", userId);
        sportQuery.eq("plan_id", choose.getSportPlan());
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(new Date());
        sportQuery.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", sportMapper.selectOne(sportQuery));
    }

    @Override
    public ResultVo<T> isUseRecipe() {
        Long userId = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> chooseQuery = new QueryWrapper<>();
        chooseQuery.eq("account_id", userId);
        PlanChoose choose = chooseMapper.selectOne(chooseQuery);
        if (choose == null || choose.getRecipePlan() == null || choose.getRecipePlan() < 1)
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_NOR, "", null);

        QueryWrapper<Recipe> recipeQuery = new QueryWrapper<>();
        recipeQuery.eq("account_id", userId);
        recipeQuery.eq("plan_id", choose.getRecipePlan());
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(new Date());
        recipeQuery.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", recipeMapper.selectOne(recipeQuery));

    }
}
