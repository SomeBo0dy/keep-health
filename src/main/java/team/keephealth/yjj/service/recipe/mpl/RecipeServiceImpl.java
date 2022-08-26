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
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.recipe.FoodsMapper;
import team.keephealth.yjj.mapper.recipe.RecipeMapper;
import team.keephealth.yjj.service.recipe.RecipeService;
import team.keephealth.yjj.service.sport.mpl.SportServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements RecipeService {

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private FoodsMapper foodsMapper;
    @Autowired
    private SportServiceImpl sportService;
    @Autowired
    private PlanChooseMapper chooseMapper;

    @Override
    public ResultVo<T> addRecipe(RecipeInfoDto dto) {
        if (dto.getBeginTime() == null || dto.getEndTime() == null)
            dto = updateTime(dto);
        else if (!sportService.isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        if (dto.getRecipeName() == null || dto.getRecipeName().equals("")){
            QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", SecurityUtils.getUserId());
            dto.setRecipeName("我的饮食方案" + (recipeMapper.selectCount(queryWrapper) + 1));
        }
        System.out.println("HERE?");
        if (dto.getTolKcal() < 0) dto.setTolKcal(0);
        Recipe recipe = new Recipe(dto);
        if (recipe.getBeginTime().after(recipe.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        if (recipeMapper.insert(recipe) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(recipe), null);
        QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.orderByDesc("id");
        queryWrapper.last(" LIMIT 1");
        recipe = recipeMapper.selectOne(queryWrapper);

        Long bf = doSthWithFood(dto.getBreakfast(), dto.getBfPict(), dto.getBfKcal(), dto.getBfWords(), recipe.getId());
        Long lu = doSthWithFood(dto.getLunch(), dto.getLuPict(), dto.getLuKcal(), dto.getLuWords(), recipe.getId());
        Long dn = doSthWithFood(dto.getDinner(), dto.getDnPict(), dto.getDnKcal(), dto.getDnWords(), recipe.getId());
        if (bf > 0) recipe.setBreakfast(bf);
        if (lu > 0) recipe.setLunch(lu);
        if (dn > 0) recipe.setDinner(dn);
        if (recipeMapper.updateById(recipe) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(recipe), null);
        RecipeInfoDto vo = getVo(recipe.getId());
        if (recipe.getTolKcal() <= 0) {
            recipe.setTolKcal(vo.getBfKcal() + vo.getLuKcal() + vo.getDnKcal());
            if (recipeMapper.updateById(recipe) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(recipe), null);
            vo.setTolKcal(recipe.getTolKcal());
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
    public ResultVo<T> updateRecipe(RecipeInfoDto dto) {
        if (dto.getRecipeId() == null || dto.getRecipeId() < 1 || recipeMapper.selectById(dto.getRecipeId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_RECIPE_ID_ERROR, "id : "+dto.getRecipeId(), null);
        if ((dto.getBeginTime() != null || dto.getEndTime() != null) && !sportService.isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        Recipe orl = recipeMapper.selectById(dto.getRecipeId());
        if (!orl.getAccountId().equals(SecurityUtils.getUserId()))
            return new ResultVo<>(AppHttpCodeEnum.NO_OPERATOR_AUTH,
                    "recipe belong : "+orl.getAccountId()+"\nmy : "+SecurityUtils.getUserId(), null);
        Long bf = doSthWithUpdate(orl.getBreakfast(), dto.getBreakfast(), dto.getBfPict(), dto.getBfKcal(), dto.getBfWords(), orl.getId());
        Long lu = doSthWithUpdate(orl.getLunch(), dto.getLunch(), dto.getLuPict(), dto.getLuKcal(), dto.getLuWords(), orl.getId());
        Long dn = doSthWithUpdate(orl.getDinner(), dto.getDinner(), dto.getDnPict(), dto.getDnKcal(), dto.getDnWords(), orl.getId());
        if (bf > 0) orl.setBreakfast(bf);
        if (lu > 0) orl.setLunch(lu);
        if (dn > 0) orl.setDinner(dn);
        if (dto.getRecipeName() != null && !dto.getRecipeName().equals(orl.getRecipeName()))
            orl.setRecipeName(dto.getRecipeName());
        if (dto.getWords() != null && !dto.getWords().equals(orl.getWords()))
            orl.setWords(dto.getWords());
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        if (dto.getBeginTime() != null && !dto.getBeginTime().equals(p.format(orl.getBeginTime())))
            orl.setBeginTime(dto.getBeginTime());
        if (dto.getEndTime() != null && !dto.getEndTime().equals(p.format(orl.getEndTime())))
            orl.setEndTime(dto.getEndTime());
        if (dto.getEndTime() != null && dto.getBeginTime() != null && orl.getBeginTime().after(orl.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        if (recipeMapper.updateById(orl) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(orl),null);
        RecipeInfoDto vo = getVo(orl.getId());
        if (dto.getTolKcal() > 0 || orl.getTolKcal() != dto.getTolKcal()) orl.setTolKcal(dto.getTolKcal());
        else orl.setTolKcal(vo.getBfKcal() + vo.getLuKcal() + vo.getDnKcal());
        if (recipeMapper.updateById(orl) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(orl), null);
        vo.setTolKcal(orl.getTolKcal());
        if (bf < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "breakfast", vo);
        if (lu < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "lunch", vo);
        if (dn < 0)
            return new ResultVo(AppHttpCodeEnum.SET_FOOD_ERROR, "dinner", vo);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> deleteRecipe(Long recipeId) {
        if (recipeId == null || recipeId < 1 || recipeMapper.selectById(recipeId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_RECIPE_ID_ERROR, "id : "+recipeId, null);
        Recipe recipe = recipeMapper.selectById(recipeId);
        if (recipeMapper.deleteById(recipeId) > 0) {
            if (recipe.getPlanId() != null && recipe.getPlanId() > 0)
                chooseMapper.clearRecipe(recipe.getAccountId());
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "id : "+recipeId, null);
    }

    @Override
    public ResultVo<T> getDetail(Long recipeId) {
        if (recipeId == null || recipeId < 1 || recipeMapper.selectById(recipeId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_RECIPE_ID_ERROR, "id : "+recipeId, null);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVo(recipeId));
    }

    public RecipeInfoDto updateTime(RecipeInfoDto dto){
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String today = p.format(new Date());
            if (dto.getBeginTime() == null && dto.getEndTime() == null){
                dto.setBeginTime(today);
                dto.setEndTime(today);
            }else if (dto.getBeginTime() == null){
                if (p.parse(dto.getEndTime()).before(p.parse(today)))
                    dto.setBeginTime(dto.getEndTime());
                dto.setBeginTime(today);
            }else if (dto.getEndTime() == null){
                if (p.parse(dto.getBeginTime()).after(p.parse(today)))
                    dto.setEndTime(dto.getBeginTime());
                dto.setEndTime(today);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public Long doSthWithFood(String name, String pict, double kcal, String words, Long id){
        if (name == null && pict == null && kcal <= 0 && words == null) return 0L;
        if (kcal < 0) kcal = 0;
        Foods newFood = new Foods(name, kcal, words, pict, id);
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
                if (pict == null || pict.equals("")) newFood.setPict(food.getPict());
                if (kcal <= 0) newFood.setKcal(food.getKcal());
                if (words == null || words.equals("")) newFood.setWords(food.getWords());
                if (foodsMapper.insert(newFood) <= 0) return -1L;
                return foodsMapper.selectOne(get).getId();
            }
        }
        if (foodsMapper.insert(newFood) <= 0) return -1L;
        return foodsMapper.selectOne(get).getId();
    }

    public RecipeInfoDto getVo(Long recipeId){
        Recipe recipe = recipeMapper.selectById(recipeId);
        RecipeInfoDto vo = new RecipeInfoDto();
        BeanUtils.copyProperties(recipe, vo);
        vo.setRecipeId(recipeId);
        if (recipe.getBreakfast() != null && recipe.getBreakfast() > 0){
            Foods food = foodsMapper.selectById(recipe.getBreakfast());
            vo.setBreakfast(food.getFoodName());
            vo.setBfPict(food.getPict());
            vo.setBfKcal(food.getKcal());
            vo.setBfWords(food.getWords());
        }
        if (recipe.getLunch() != null && recipe.getLunch() > 0){
            Foods food = foodsMapper.selectById(recipe.getLunch());
            vo.setLunch(food.getFoodName());
            vo.setLuPict(food.getPict());
            vo.setLuKcal(food.getKcal());
            vo.setLuWords(food.getWords());
        }
        if (recipe.getDinner() != null && recipe.getDinner() > 0){
            Foods food = foodsMapper.selectById(recipe.getDinner());
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
        if (food.getFoodName().equals(name) && ((food.getPict() == null && pict == null) || food.getPict().equals(pict))
                && food.getKcal() == kcal && food.getWords().equals(words)) return 0L;
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
