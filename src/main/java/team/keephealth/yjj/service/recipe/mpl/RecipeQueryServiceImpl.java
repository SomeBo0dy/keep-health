package team.keephealth.yjj.service.recipe.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.TimeQueryMy;
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;
import team.keephealth.yjj.domain.entity.recipe.Foods;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.recipe.FoodsMapper;
import team.keephealth.yjj.mapper.recipe.RecipeMapper;
import team.keephealth.yjj.service.recipe.RecipeQueryService;
import team.keephealth.yjj.service.sport.mpl.SportServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecipeQueryServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements RecipeQueryService {

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private FoodsMapper foodsMapper;
    @Autowired
    private SportServiceImpl sportService;

    @Override
    public ResultVo<T> queryAll() {
        QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.orderByDesc("id");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVoList(queryWrapper));
    }

    @Override
    public ResultVo<T> queryByTime(TimeQueryMy dto) {
        if ((dto.getBeginTime() == null && dto.getEndTime() == null) || !sportService.isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.apply(StringUtils.hasText(dto.getBeginTime()),
                "date_format (begin_time, '%Y-%m-%d') >= date_format('" + dto.getBeginTime() + "','%Y-%m-%d')")
                .apply(StringUtils.hasText(dto.getEndTime()),
                        "date_format (end_time, '%Y-%m-%d') <= date_format('" + dto.getEndTime() + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVoList(queryWrapper));
    }

    @Override
    public ResultVo<T> queryNow() {
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(new Date());
        QueryWrapper<Recipe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVoList(queryWrapper));
    }

    public List<RecipeInfoDto> getVoList(QueryWrapper<Recipe> queryWrapper){
        List<Recipe> recipes = recipeMapper.selectList(queryWrapper);
        List<RecipeInfoDto> list = new ArrayList<>();
        for (Recipe r : recipes) {
            RecipeInfoDto vo = new RecipeInfoDto();
            BeanUtils.copyProperties(r, vo);
            vo.setRecipeId(r.getId());
            if (r.getBreakfast() != null && r.getBreakfast() > 0){
                Foods food = foodsMapper.selectById(r.getBreakfast());
                vo.setBreakfast(food.getFoodName());
                vo.setBfPict(food.getPict());
                vo.setBfKcal(food.getKcal());
                vo.setBfWords(food.getWords());
            }
            if (r.getLunch() != null && r.getLunch() > 0){
                Foods food = foodsMapper.selectById(r.getLunch());
                vo.setLunch(food.getFoodName());
                vo.setLuPict(food.getPict());
                vo.setLuKcal(food.getKcal());
                vo.setLuWords(food.getWords());
            }
            if (r.getDinner() != null && r.getDinner() > 0){
                Foods food = foodsMapper.selectById(r.getDinner());
                vo.setDinner(food.getFoodName());
                vo.setDnPict(food.getPict());
                vo.setDnKcal(food.getKcal());
                vo.setDnWords(food.getWords());
            }
            list.add(vo);
        }
        return list;
    }
}
