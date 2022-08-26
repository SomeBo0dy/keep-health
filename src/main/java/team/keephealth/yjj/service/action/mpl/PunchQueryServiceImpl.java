package team.keephealth.yjj.service.action.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.action.Aim;
import team.keephealth.yjj.domain.entity.action.Punch;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.action.PunchVo;
import team.keephealth.yjj.domain.vo.action.TodayPunch;
import team.keephealth.yjj.domain.vo.action.TodayRPunch;
import team.keephealth.yjj.domain.vo.action.TodaySPunch;
import team.keephealth.yjj.mapper.action.PunchMapper;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.service.action.PunchQueryService;
import team.keephealth.yjj.service.recipe.RecipeQueryService;
import team.keephealth.yjj.service.recipe.mpl.RecipeQueryServiceImpl;
import team.keephealth.yjj.service.recipe.mpl.RecipeServiceImpl;
import team.keephealth.yjj.service.sport.mpl.SportQueryServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PunchQueryServiceImpl extends ServiceImpl<PunchMapper, Punch> implements PunchQueryService {

    @Autowired
    private PunchMapper punchMapper;
    @Autowired
    private AimServiceImpl aimService;
    @Autowired
    private PunchServiceImpl punchService;
    @Autowired
    private RecipeQueryServiceImpl recipeQueryService;
    @Autowired
    private SportMapper sportMapper;

    @Override
    public ResultVo<T> todayRecipe() {
        Punch punch = punchService.getToday();
        TodayRPunch vo = new TodayRPunch();
        vo.setBreakfast(punchService.recipeExist(punch.getRecipePlanBf(), punch.getCpRecipeBf()));
        vo.setLunch(punchService.recipeExist(punch.getRecipePlanLu(), punch.getCpRecipeLu()));
        vo.setDinner(punchService.recipeExist(punch.getRecipePlanDn(), punch.getCpRecipeDn()));
        if (!(vo.isBreakfast() && vo.isLunch() && vo.isDinner())) {
            SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
            String today = p.format(new Date());
            QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", SecurityUtils.getUserId());
            queryWrapper.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                    .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
            vo.setList(recipeQueryService.getVoList(queryWrapper));
        }
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> todaySport() {
        Punch punch = punchService.getToday();
        TodaySPunch vo = new TodaySPunch();
        vo.setPlan(sportMapper.selectById(punch.getSportPlan()) != null);
        if (!vo.isPlan()) {
            SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
            String today = p.format(new Date());
            QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", SecurityUtils.getUserId());
            queryWrapper.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                    .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
            vo.setList(sportMapper.selectList(queryWrapper));
        }
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> recordCheck() {
        Punch punch = punchService.getToday();
        TodayPunch vo = new TodayPunch();
        vo.setRecipe(punch.getRecipePunch() == 1);
        vo.setSport(punch.getSportPunch() == 1);
        vo.setBreakfast(punchService.recipeExist(punch.getRecipePlanBf(), punch.getCpRecipeBf()));
        vo.setLunch(punchService.recipeExist(punch.getRecipePlanLu(), punch.getCpRecipeLu()));
        vo.setDinner(punchService.recipeExist(punch.getRecipePlanDn(), punch.getCpRecipeDn()));
        vo.setPlan(sportMapper.selectById(punch.getSportPlan()) != null);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> queryMonth() {
        QueryWrapper<Punch> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", SecurityUtils.getUserId());
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM");
        String month = p.format(new Date());
        wrapper.apply("DATE_FORMAT(punch_time,'%Y-%m') = DATE_FORMAT('"+month+"-01"+"','%Y-%m')");
        wrapper.orderByAsc("punch_time");
        List<Punch> list = punchMapper.selectList(wrapper);
        List<PunchVo> vos = new ArrayList<>();
        for (Punch punch : list) {
            PunchVo vo = new PunchVo();
            vo.setRecipe(punch.getRecipePunch() == 1);
            vo.setSport(punch.getSportPunch() == 1);
            vo.setTime(punch.getPunchTime());
            vos.add(vo);
        }
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vos);
    }
}
