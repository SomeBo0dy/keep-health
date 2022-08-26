package team.keephealth.yjj.mapper.recipe;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.recipe.Recipe;

@Mapper
@Repository
public interface RecipeMapper extends BaseMapper<Recipe> {
}
