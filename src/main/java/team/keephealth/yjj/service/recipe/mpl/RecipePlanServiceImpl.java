package team.keephealth.yjj.service.recipe.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.recipe.RecipePlanInfoDto;
import team.keephealth.yjj.domain.entity.action.Memory;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.entity.recipe.RecipePlan;
import team.keephealth.yjj.domain.entity.recipe.RecipePlanDetail;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.MemoryMapper;
import team.keephealth.yjj.mapper.recipe.FoodsMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanDetailMapper;
import team.keephealth.yjj.mapper.recipe.RecipePlanMapper;
import team.keephealth.yjj.service.recipe.RecipePlanService;

@Service
public class RecipePlanServiceImpl extends ServiceImpl<RecipePlanMapper, RecipePlan> implements RecipePlanService {

    @Autowired
    private RecipePlanMapper planMapper;
    @Autowired
    private RecipePlanDetailMapper detailMapper;
    @Autowired
    private FoodsMapper foodsMapper;
    @Autowired
    private RecipePlanCServiceImpl cService;

    @Override
    public ResultVo<T> addPlan(RecipePlanInfoDto dto) {
        if (dto.getBfKcal() <= 0 && !hasFood(dto.getBreakfast()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_BF_KCAL_ERROR, "kcal : "+dto.getBfKcal(), null);
        if (dto.getLuKcal() <= 0 && !hasFood(dto.getLunch()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_LU_KCAL_ERROR, "kcal : "+ dto.getLuKcal(), null);
        if (dto.getDnKcal() <= 0 && !hasFood(dto.getDinner()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_DN_KCAL_ERROR, "kcal : "+ dto.getDnKcal(), null);
        if (dto.getCarbs() == null || dto.getCarbs() < 0) dto.setCarbs(0D);
        if (dto.getPro() == null || dto.getPro() < 0) dto.setPro(0D);
        if (dto.getFat() == null || dto.getFat() < 0) dto.setFat(0D);
        if (dto.getTitle() == null || dto.getTitle().equals(""))
            dto.setTitle("减肥套餐" + (planMapper.selectCount(null) + 1));
        RecipePlan plan = new RecipePlan(dto);
        if (planMapper.insert(plan) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(plan), null);
        QueryWrapper<RecipePlan> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", SecurityUtils.getUserId());
        wrapper.orderByDesc("id");
        wrapper.last(" LIMIT 1");
        plan = planMapper.selectOne(wrapper);

        RecipePlanDetail detail = new RecipePlanDetail();
        detail.setPid(plan.getId());
        Long bf = doSthWithFood(dto.getBreakfast(), dto.getBfPict(), dto.getBfKcal(), dto.getBfWords(), plan.getId());
        Long lu = doSthWithFood(dto.getLunch(), dto.getLuPict(), dto.getLuKcal(), dto.getLuWords(), plan.getId());
        Long dn = doSthWithFood(dto.getDinner(), dto.getDnPict(), dto.getDnKcal(), dto.getDnWords(), plan.getId());
        if (bf > 0) detail.setBreakfast(bf);
        if (lu > 0) detail.setLunch(lu);
        if (dn > 0) detail.setDinner(dn);
        if (detailMapper.insert(detail) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(detail), null);
        RecipePlanInfoDto vo = getVo(plan.getId());
        if (plan.getKcal() <= 0) {
            plan.setKcal(vo.getBfKcal() + vo.getLuKcal() + vo.getDnKcal());
            if (planMapper.updateById(plan) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(plan), null);
            vo.setKcal(plan.getKcal());
        }
        if (bf < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "breakfast", vo);
        if (lu < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "lunch", vo);
        if (dn < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "dinner", vo);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> updatePlan(RecipePlanInfoDto dto) {
        if (dto.getPlanId() == null || dto.getPlanId() < 1 || planMapper.selectById(dto.getPlanId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+dto.getPlanId(), null);
        if (!planMapper.selectById(dto.getPlanId()).getAccountId().equals(SecurityUtils.getUserId()))
            return new ResultVo<>(AppHttpCodeEnum.NO_OPERATOR_AUTH,
                    "recipe belong : "+planMapper.selectById(dto.getPlanId()).getAccountId()+"\nmy : "+SecurityUtils.getUserId(), null);

        RecipePlan orl = planMapper.selectById(dto.getPlanId());
        if (dto.getCarbs() != null && dto.getCarbs() <= 0) dto.setCarbs(null);
        if (dto.getPro() != null && dto.getPro() <= 0) dto.setPro(null);
        if (dto.getFat() != null && dto.getFat() <= 0) dto.setFat(null);
        if (dto.getTitle() != null && dto.getTitle().equals("")) dto.setTitle(null);
        if (dto.getMsg() != null && dto.getMsg().equals("")) dto.setMsg(null);
        RecipePlan newPlan = new RecipePlan(dto);
        newPlan.setId(dto.getPlanId());
        newPlan.setAddTime(null);
        if (planMapper.updateById(newPlan) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(newPlan), null);

        QueryWrapper<RecipePlanDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", dto.getPlanId());
        RecipePlanDetail detail = detailMapper.selectOne(wrapper);
        Long bf = doSthWithUpdate(detail.getBreakfast(), dto.getBreakfast(), dto.getBfPict(), dto.getBfKcal(), dto.getBfWords(), dto.getPlanId());
        Long lu = doSthWithUpdate(detail.getLunch(), dto.getLunch(), dto.getLuPict(), dto.getLuKcal(), dto.getLuWords(), dto.getPlanId());
        Long dn = doSthWithUpdate(detail.getDinner(), dto.getDinner(), dto.getDnPict(), dto.getDnKcal(), dto.getDnWords(), dto.getPlanId());
        if (bf > 0) detail.setBreakfast(bf);
        if (lu > 0) detail.setLunch(lu);
        if (dn > 0) detail.setDinner(dn);
        if (detailMapper.updateById(detail) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(detail), null);
        RecipePlanInfoDto vo = getVo(dto.getPlanId());
        if (dto.getKcal() <= 0 || orl.getKcal() == dto.getKcal())
            newPlan.setKcal(vo.getBfKcal() + vo.getLuKcal() + vo.getDnKcal());
        if (planMapper.updateById(newPlan) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(newPlan), null);
        vo.setKcal(newPlan.getKcal());
        if (bf < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "breakfast", vo);
        if (lu < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "lunch", vo);
        if (dn < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "dinner", vo);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> deletePlan(Long planId) {
        if (planId == null || planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        if (planMapper.deleteById(planId) > 0) {
            cService.deleteChoose(planId);
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "id : "+planId, null);
    }

    @Override
    public ResultVo<T> getPlan(Long planId) {
        if (planId == null || planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVo(planId));
    }

    public Long doSthWithFood(String name, String pict, double kcal, String words, Long id){
        if (name == null && pict == null && kcal <= 0 && words == null) return 0L;
        if (kcal < 0) kcal = 0;
        Foods newFood = new Foods(name, kcal, words, pict);
        QueryWrapper<Foods> get = new QueryWrapper<>();
        get.eq("belong", id);
        get.orderByDesc("id");
        get.last(" LIMIT 1");
        if (name != null){
            QueryWrapper<Foods> q = new QueryWrapper<>();
            q.eq("food_name", name);
            q.isNull("belong");
            Foods food = foodsMapper.selectOne(q);
            if (food!=null && (pict == null && kcal <= 0 && words == null)) return food.getId();
            if (food != null){
                newFood.setBelong(id);
                if (pict == null || pict.equals("")) newFood.setPict(food.getPict());
                if (kcal <= 0) newFood.setKcal(food.getKcal());
                if (words == null || words.equals("")) newFood.setWords(food.getWords());
                if (foodsMapper.insert(newFood) <= 0) return -1L;
                return foodsMapper.selectOne(get).getId();
            }else {
                if (foodsMapper.insert(newFood) <= 0) return -1L;
                return foodsMapper.selectOne(q).getId();
            }
        }
        newFood.setBelong(id);
        if (foodsMapper.insert(newFood) <= 0) return -1L;
        return foodsMapper.selectOne(get).getId();
    }

    public boolean hasFood(String name){
        QueryWrapper<Foods> wrapper = new QueryWrapper<>();
        wrapper.eq("food_name", name);
        wrapper.isNull("belong");
        return foodsMapper.selectOne(wrapper) != null;
    }

    public RecipePlanInfoDto getVo(Long planId){
        RecipePlan plan = planMapper.selectById(planId);
        RecipePlanInfoDto vo = new RecipePlanInfoDto(plan);
        vo = setMeals(vo, planId);
        return vo;
    }

    public RecipePlanInfoDto setMeals(RecipePlanInfoDto vo, Long planId){
        QueryWrapper<RecipePlanDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", planId);
        RecipePlanDetail detail = detailMapper.selectOne(wrapper);
        if (detail.getBreakfast() != null && detail.getBreakfast() > 0){
            Foods food = foodsMapper.selectById(detail.getBreakfast());
            vo.setBreakfast(food.getFoodName());
            vo.setBfPict(food.getPict());
            vo.setBfKcal(food.getKcal());
            vo.setBfWords(food.getWords());
        }
        if (detail.getLunch() != null && detail.getLunch() > 0){
            Foods food = foodsMapper.selectById(detail.getLunch());
            vo.setLunch(food.getFoodName());
            vo.setLuPict(food.getPict());
            vo.setLuKcal(food.getKcal());
            vo.setLuWords(food.getWords());
        }
        if (detail.getDinner() != null && detail.getDinner() > 0){
            Foods food = foodsMapper.selectById(detail.getDinner());
            vo.setDinner(food.getFoodName());
            vo.setDnPict(food.getPict());
            vo.setDnKcal(food.getKcal());
            vo.setDnWords(food.getWords());
        }
        return vo;
    }

    public Long doSthWithUpdate(Long org, String name, String pict, double kcal, String words, Long id){
        if (name == null && pict == null && kcal <= 0 && words == null) return 0L;
        if (org == null || org < 1 || foodsMapper.selectById(org) == null)
            return doSthWithFood(name, pict, kcal, words, id);
        if (kcal < 0) kcal = 0;
        Foods food = foodsMapper.selectById(org);
        if (food.getFoodName().equals(name) && food.getPict().equals(pict) && food.getKcal() == kcal && food.getWords().equals(words)) return 0L;
        if (!food.getFoodName().equals(name)){
            Long code = doSthWithFood(name, pict, kcal, words, id);
            if (code > 0 && food.getBelong() != null) foodsMapper.deleteById(org);
            return code;
        }
        // name相同
        if (food.getBelong() == null){
            Foods newFood = new Foods();
            BeanUtils.copyProperties(food, newFood);
            newFood.setId(null);
            newFood.setBelong(id);
            if (pict != null && !pict.equals("")) newFood.setPict(pict);
            if (kcal > 0) newFood.setKcal(kcal);
            if (words != null && !words.equals("")) newFood.setWords(words);
            if (foodsMapper.insert(newFood) <= 0) return -1L;
            QueryWrapper<Foods> get = new QueryWrapper<>();
            get.eq("belong", id);
            get.orderByDesc("id");
            get.last(" LIMIT 1");
            return foodsMapper.selectOne(get).getId();
        }
        if (pict != null && !pict.equals("")) food.setPict(pict);
        if (kcal > 0) food.setKcal(kcal);
        if (words != null && !words.equals("")) food.setWords(words);
        if (foodsMapper.updateById(food) > 0) return 0L;
        return -1L;
    }
}
