package team.keephealth.yjj.service.action.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.action.PunchDeleteDto;
import team.keephealth.yjj.domain.dto.action.RecipePunchDto;
import team.keephealth.yjj.domain.dto.action.SportPunchDto;
import team.keephealth.yjj.domain.entity.action.Aim;
import team.keephealth.yjj.domain.entity.action.Punch;
import team.keephealth.yjj.domain.entity.action.PunchAcc;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.entity.recipe.RecipePunch;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.entity.sport.SportPunch;
import team.keephealth.yjj.domain.entity.sport.WWE;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.AimMapper;
import team.keephealth.yjj.mapper.action.PunchAccMapper;
import team.keephealth.yjj.mapper.action.PunchMapper;
import team.keephealth.yjj.mapper.recipe.FoodsMapper;
import team.keephealth.yjj.mapper.recipe.RecipeMapper;
import team.keephealth.yjj.mapper.recipe.RecipePunchMapper;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.mapper.sport.SportPunchMapper;
import team.keephealth.yjj.mapper.sport.WWEMapper;
import team.keephealth.yjj.service.action.PunchService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PunchServiceImpl extends ServiceImpl<PunchMapper, Punch> implements PunchService {

    @Autowired
    private PunchMapper punchMapper;
    @Autowired
    private AimServiceImpl aimService;
    @Autowired
    private AimMapper aimMapper;
    @Autowired
    private SportMapper sportMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private WWEMapper wweMapper;
    @Autowired
    private FoodsMapper foodsMapper;
    @Autowired
    private SportPunchMapper sportPunchMapper;
    @Autowired
    private RecipePunchMapper recipePunchMapper;
    @Autowired
    private PunchAccMapper accMapper;

    @Override
    public ResultVo<T> addSportPunch(SportPunchDto dto) {
        Punch punch = getToday();
        int spOrg = punch.getSportPunch();
        Sport sport = sportMapper.selectById(dto.getPlanId());
        if (sport == null && dto.getSport() == null)
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, JSONObject.toJSONString(dto), null);
        if (dto.getPlanId() != null && dto.getPlanId() > 0 && sport != null){
            if (punch.getSportPlan() != null && punch.getSportPlan() > 0)
                return new ResultVo<>(AppHttpCodeEnum.PLAN_PUNCH_EXIST, JSONObject.toJSONString(punch), null);
            punch.setSportPlan(dto.getPlanId());
            if (dto.getEnergy() == null || dto.getEnergy() <= 0) dto.setEnergy(sport.getEnergy());
        }else {
// 自定义
            WWE wwe = wweMapper.selectById(dto.getSport());
            if (wwe == null){
                if (dto.getEnergy() == null)
                    return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, JSONObject.toJSONString(dto), null);
                dto.setSport(0L);
            }else {
                if ((dto.getSportTime() == null || dto.getSportTime() <= 0) && (dto.getEnergy() == null || dto.getEnergy() <= 0))
                    return new ResultVo<>(AppHttpCodeEnum.DATA_TIME_ERROR, "time : "+dto.getSportTime(), null);
                if (dto.getEnergy() == null || dto.getEnergy() < 0)
                    dto.setEnergy(wwe.getEnergy() * dto.getSportTime());
            }
            SportPunch sportPunch = new SportPunch(dto);
            sportPunch.setPunchId(punch.getId());
            if (sportPunchMapper.insert(sportPunch) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(sportPunch), null);
            if (punch.getCpSport() == null || punch.getCpSport() <= 0) punch.setCpSport(1L);
        }

        Aim aim = aimService.getToday();
        aim.setExpended(aim.getExpended() + dto.getEnergy());
        if (aimMapper.updateById(aim) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);
        if (aim.getExpended() >= aim.getExpendKcal()) punch.setSportPunch(1);
        else punch.setSportPunch(2);
        if (punchMapper.updateById(punch) > 0) {
            accRecord(punch, false, spOrg != punch.getSportPunch());
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getToday());
        }
        else {
            aim.setExpended(aim.getExpended() - dto.getEnergy());
            if (aim.getExpended() < aim.getExpendKcal()) punch.setSportPunch(2);
            aimMapper.updateById(aim);
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(punch), null);
        }
    }

    @Override
    public ResultVo<T> addRecipePunch(RecipePunchDto dto) {
        if (dto.getCate() < 0 || dto.getCate() > 3)
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, JSONObject.toJSONString(dto), null);
        Punch punch = getToday();
        int reOrg = punch.getRecipePunch();
        if (dto.getCate() != 0){
            if (dto.getCate() == 1 && recipeExist(punch.getRecipePlanBf(), punch.getCpRecipeBf()))
                return new ResultVo<>(AppHttpCodeEnum.BREAKFAST_EXIST, "", null);
            if (dto.getCate() == 2 && recipeExist(punch.getRecipePlanLu(), punch.getCpRecipeLu()))
                return new ResultVo<>(AppHttpCodeEnum.LUNCH_EXIST, "", null);
            if (dto.getCate() == 3 && recipeExist(punch.getRecipePlanDn(), punch.getCpRecipeDn()))
                return new ResultVo<>(AppHttpCodeEnum.DINNER_EXIST, "", null);
        }

        if (dto.getPlanId() != null && recipeMapper.selectById(dto.getPlanId()) != null){
            if (dto.getCate() == 0)
                return new ResultVo<>(AppHttpCodeEnum.EMPTY_DETAIL_LIST, "cate : "+dto.getCate(), null);
            Foods food = new Foods();
            Recipe recipe = recipeMapper.selectById(dto.getPlanId());
            switch (dto.getCate()){
                case 1: punch.setRecipePlanBf(dto.getPlanId());
                food = foodsMapper.selectById(recipe.getBreakfast());break;
                case 2: punch.setRecipePlanLu(dto.getPlanId());
                    food = foodsMapper.selectById(recipe.getLunch());break;
                case 3: punch.setRecipePlanDn(dto.getPlanId());
                    food = foodsMapper.selectById(recipe.getDinner());break;
            }
            if (food == null)
                return new ResultVo<>(AppHttpCodeEnum.SET_FOOD_ERROR, JSONObject.toJSONString(dto), null);
            if (dto.getKcal() == null || dto.getKcal() <= 0) dto.setKcal(food.getKcal());
        }else{
            QueryWrapper<Foods> wrapper = new QueryWrapper<>();
            wrapper.eq("food_name", dto.getMeal());
            wrapper.isNull("belong");
            Foods food = foodsMapper.selectOne(wrapper);
            if (food != null && (dto.getKcal() == null || dto.getKcal() <= 0))
                dto.setKcal(food.getKcal());
            if (food == null && (dto.getKcal() == null || dto.getKcal() <= 0))
                return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, JSONObject.toJSONString(dto), null);
            RecipePunch recipePunch = new RecipePunch(dto);
            recipePunch.setPunchId(punch.getId());
            if (recipePunchMapper.insert(recipePunch) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(recipePunch), null);
            QueryWrapper<RecipePunch> query = new QueryWrapper<>();
            query.eq("account_id", SecurityUtils.getUserId());
            query.orderByDesc("id");
            query.last(" LIMIT 1");
            switch (dto.getCate()){
                case 1: punch.setCpRecipeBf(dto.getPlanId());break;
                case 2: punch.setCpRecipeLu(dto.getPlanId());break;
                case 3: punch.setCpRecipeDn(dto.getPlanId());break;
                case 0: punch.setCpRecipe(1L);break;
            }
        }

        Aim aim = aimService.getToday();
        aim.setAbsorbed(aim.getAbsorbed() + dto.getKcal());
        if (aimMapper.updateById(aim) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);
        if (aim.getAbsorbed() > aim.getAbsorbKcal()) punch.setRecipePunch(2);
        else punch.setRecipePunch(1);
        if (punchMapper.updateById(punch) > 0) {
            accRecord(punch,reOrg != punch.getRecipePunch(), false);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getToday());
        }
        else {
            aim.setAbsorbed(aim.getAbsorbed() - dto.getKcal());
            if (aim.getAbsorbed() <= aim.getAbsorbKcal()) punch.setSportPunch(1);
            aimMapper.updateById(aim);
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(punch), null);
        }
    }

    @Override
    public ResultVo<T> deletePunch(PunchDeleteDto dto) {
        Long userId = SecurityUtils.getUserId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        QueryWrapper<Punch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", userId);
        queryWrapper.apply("DATE(punch_time) = STR_TO_DATE('"+ today+"','%Y-%m-%d')");
        Punch punch = punchMapper.selectOne(queryWrapper);
        if (punch == null)
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, "day : "+ today, null);
        int reOrg = punch.getRecipePunch();
        int spOrg = punch.getSportPunch();

        QueryWrapper<Aim> wrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", userId);
        queryWrapper.apply("DATE(punch_time) = STR_TO_DATE('"+today+"','%Y-%m-%d')");
        Aim aim = aimMapper.selectOne(wrapper);

        if (dto.getPlan() != null){
            if (dto.getPlan() == 1 && sportMapper.selectById(punch.getSportPlan()) != null){
                Sport sport = sportMapper.selectById(punch.getSportPlan());
                punch.setSportPlan(0L);
                aim.setExpended(aim.getExpended() - sport.getEnergy());
                if (aim.getExpended() < aim.getExpendKcal()) punch.setSportPunch(2);
                if (aimMapper.updateById(aim) <= 0)
                    return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);

            }else if (dto.getPlan() == 2){
                Recipe recipe = new Recipe();
                switch (dto.getCate()){
                    case 1 : recipe = recipeMapper.selectById(punch.getRecipePlanBf());
                    punch.setRecipePlanBf(0L);break;
                    case 2 : recipe = recipeMapper.selectById(punch.getRecipePlanLu());
                    punch.setRecipePlanLu(0L);break;
                    case 3 : recipe = recipeMapper.selectById(punch.getRecipePlanDn());
                    punch.setRecipePlanDn(0L);break;
                }
                if (recipe != null){
                    aim.setAbsorbed(aim.getAbsorbed() - recipe.getTolKcal());
                    if (aim.getAbsorbed() <= aim.getAbsorbKcal()) punch.setSportPunch(1);
                    if (aimMapper.updateById(aim) <= 0)
                        return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);
                }
            }
        }
        SportPunch sportPunch = sportPunchMapper.selectById(dto.getSportCP());
        RecipePunch recipePunch = recipePunchMapper.selectById(dto.getRecipeCP());
        if (recipePunch != null){
            if (punch.getCpRecipeBf().equals(dto.getRecipeCP())) punch.setCpRecipeBf(0L);
            else if (punch.getCpRecipeLu().equals(dto.getRecipeCP())) punch.setCpRecipeLu(0L);
            else if (punch.getCpRecipeDn().equals(dto.getRecipeCP())) punch.setCpRecipeDn(0L);
            aim.setAbsorbed(aim.getAbsorbed() - recipePunch.getKcal());
            if (aim.getAbsorbed() <= aim.getAbsorbKcal()) punch.setSportPunch(1);
            if (aimMapper.updateById(aim) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);
            recipePunchMapper.deleteById(recipePunch.getId());
        }
        if (sportPunch != null && sportPunch.getPunchId().equals(punch.getId())){
            aim.setExpended(aim.getExpended() - sportPunch.getEnergy());
            if (aim.getExpended() < aim.getExpendKcal()) punch.setSportPunch(2);
            if (aimMapper.updateById(aim) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(aim), null);
            sportPunchMapper.deleteById(sportPunch.getId());
        }
        if (punchMapper.updateById(punch) > 0) {
            accRecord(punch, reOrg != punch.getRecipePunch(), spOrg != punch.getSportPunch());
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(punch), null);
    }

    @Override
    public ResultVo<T> deleteAll(String day) {
        Long userId = SecurityUtils.getUserId();
        QueryWrapper<Punch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", userId);
        queryWrapper.apply("DATE(punch_time) = STR_TO_DATE('"+day+"','%Y-%m-%d')");
        Punch punch = punchMapper.selectOne(queryWrapper);
        if (punch == null)
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_PUNCH_MSG, "day : "+day, null);
        int reOrg = punch.getRecipePunch();
        int spOrg = punch.getSportPunch();
        Punch zero = new Punch(punch);
        if (punchMapper.updateById(zero) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(zero), null);
        if (punch.getCpSport() != null && punch.getCpSport() > 0){
            QueryWrapper<SportPunch> wrapper = new QueryWrapper<>();
            wrapper.eq("account_id", userId);
            wrapper.eq("punch_id", punch.getId());
            if (sportPunchMapper.delete(wrapper) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, "CP sport delete error.", null);
        }
        if (recipePunchMapper.selectById(punch.getCpRecipeBf()) != null)
            recipePunchMapper.deleteById(recipePunchMapper.selectById(punch.getCpRecipeBf()).getId());
        if (recipePunchMapper.selectById(punch.getCpRecipeLu()) != null)
            recipePunchMapper.deleteById(recipePunchMapper.selectById(punch.getCpRecipeLu()).getId());
        if (recipePunchMapper.selectById(punch.getCpRecipeDn()) != null)
            recipePunchMapper.deleteById(recipePunchMapper.selectById(punch.getCpRecipeDn()).getId());

        QueryWrapper<RecipePunch> reWrapper = new QueryWrapper<>();
        reWrapper.eq("account_id", punch.getAccountId());
        reWrapper.eq("punch_id", punch.getId());
        recipePunchMapper.delete(reWrapper);

        QueryWrapper<Aim> wrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", userId);
        queryWrapper.apply("DATE(punch_time) = STR_TO_DATE('"+day+"','%Y-%m-%d')");
        Aim aim = aimMapper.selectOne(wrapper);
        aim.setAbsorbed(0);
        aim.setExpended(0);
        if (aimMapper.updateById(aim) > 0) {
            accRecord(zero, reOrg != punch.getRecipePunch(), spOrg != punch.getSportPunch());
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "day : "+day, null);
    }

    public Punch getToday(){
        Long id = SecurityUtils.getUserId();
        Date date = new Date();
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(date);
        QueryWrapper<Punch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        queryWrapper.apply("DATE(punch_time) = STR_TO_DATE('"+today+"','%Y-%m-%d')");
        Punch punch = punchMapper.selectOne(queryWrapper);
        if (punch == null){
            punch = new Punch(id, date);
            punchMapper.insert(punch);
            punch = punchMapper.selectOne(queryWrapper);
        }
        return punch;
    }

    public boolean recipeExist(Long plan, Long self){
        return recipeMapper.selectById(plan) != null || recipePunchMapper.selectById(self) != null;
    }

    public void accRecord(Punch punch, boolean isReFail, boolean isSpFail){
        QueryWrapper<PunchAcc> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", punch.getAccountId());
        PunchAcc acc = accMapper.selectOne(wrapper);
        if (acc == null){
            acc = new PunchAcc();
            acc.setAccountId(punch.getAccountId());
            accMapper.insert(acc);
            acc = accMapper.selectOne(wrapper);
        }
        if (acc.getReCount() < 0 ) acc.setReCount(0);
        if (acc.getSpCount() < 0 ) acc.setSpCount(0);
        if (acc.getReHigh() < 0) acc.setReHigh(0);
        if (acc.getSpHigh() < 0) acc.setSpHigh(0);
        if (punch.getRecipePunch() == 1 && (isReFail)) {
            acc.setReLast(punch.getPunchTime());
            acc.setReCount(acc.getReCount() + 1);
            if (acc.getReCount() > acc.getReHigh()) acc.setReHigh(acc.getReCount());
        }
        if (punch.getSportPunch() == 1 && (isSpFail)) {
            acc.setSpLast(punch.getPunchTime());
            acc.setSpCount(acc.getSpCount() + 1);
            if (acc.getSpCount() > acc.getSpHigh()) acc.setSpHigh(acc.getSpCount());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        String punchTime = format.format(punch.getPunchTime());
        List<Punch> records = new ArrayList<>();
        if (punch.getRecipePunch() != 1 && isReFail){
            QueryWrapper<Punch> punchQuery = new QueryWrapper<>();
            punchQuery.eq("account_id", punch.getAccountId());
            punchQuery.orderByDesc("punch_time");
            if (acc.getReLast().equals(punch.getPunchTime())) {
                punchQuery.apply("date_format (punch_time, '%Y-%m-%d') < date_format('" + punchTime + "','%Y-%m-%d')");
                records = punchMapper.selectList(punchQuery);
                if (records.size() == 0){
                    if (acc.getReHigh() == acc.getReCount()) acc.setReHigh(0);
                    acc.setReCount(0);
                    accMapper.updateById(acc);
                    accMapper.reLastNull();
                    return;
                }
            }else {
                punchQuery.apply("date_format (punch_time, '%Y-%m-%d') > date_format('" + punchTime + "','%Y-%m-%d')")
                        .apply("date_format (end_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')");
                records = punchMapper.selectList(punchQuery);
            }
            Calendar calendar = Calendar.getInstance();
            acc.setReLast(records.get(0).getPunchTime());
            calendar.setTime(acc.getReLast());
            int count = 1;
            for (Punch record : records) {
                if (record.getPunchTime().equals(acc.getReLast())) continue;
                calendar.add(Calendar.DATE, -1);
                try {
                    if (calendar.getTime().equals(format.parse(format.format(record.getPunchTime()))) && record.getRecipePunch() == 1)
                        count++;
                    else break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (acc.getReHigh() == acc.getReCount()) acc.setReHigh(count);
            acc.setReCount(count);
        }
        if (punch.getSportPunch() != 1 && isSpFail){
            QueryWrapper<Punch> punchQuery = new QueryWrapper<>();
            punchQuery.eq("account_id", punch.getAccountId());
            punchQuery.orderByDesc("punch_time");
            if (acc.getSpLast().equals(punch.getPunchTime())) {
                punchQuery.apply("date_format (punch_time, '%Y-%m-%d') < date_format('" + punchTime + "','%Y-%m-%d')");
                records = punchMapper.selectList(punchQuery);
                if (records.size() == 0){
                    if (acc.getSpHigh() == acc.getSpCount()) acc.setSpHigh(0);
                    acc.setSpCount(0);
                    accMapper.updateById(acc);
                    accMapper.spLastNull();
                    return;
                }
            }else {
                punchQuery.apply("date_format (punch_time, '%Y-%m-%d') > date_format('" + punchTime + "','%Y-%m-%d')")
                        .apply("date_format (end_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')");
                records = punchMapper.selectList(punchQuery);
            }
            Calendar calendar = Calendar.getInstance();
            acc.setSpLast(records.get(0).getPunchTime());
            calendar.setTime(acc.getSpLast());
            int count = 1;
            for (Punch record : records) {
                if (record.getPunchTime().equals(acc.getSpLast())) continue;
                calendar.add(Calendar.DATE, -1);
                try {
                    if (calendar.getTime().equals(format.parse(format.format(record.getPunchTime()))) && record.getSportPunch() == 1)
                        count++;
                    else break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (acc.getSpHigh() == acc.getSpCount()) acc.setSpHigh(count);
            acc.setSpCount(count);
        }
        accMapper.updateById(acc);
    }
}
