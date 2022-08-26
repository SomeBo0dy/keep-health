package team.keephealth.yjj.service.recipe;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;
import team.keephealth.yjj.domain.entity.recipe.Recipe;
import team.keephealth.yjj.domain.vo.ResultVo;

public interface RecipeService extends IService<Recipe> {

    // 添加我的食谱
    ResultVo<T> addRecipe(RecipeInfoDto dto);

    // 更改我的食谱
    ResultVo<T> updateRecipe(RecipeInfoDto dto);

    // 删除食谱
    ResultVo<T> deleteRecipe(Long recipeId);

    // 获取某个食谱信息（不知道有什么用但是写了
    ResultVo<T> getDetail(Long recipeId);

}
