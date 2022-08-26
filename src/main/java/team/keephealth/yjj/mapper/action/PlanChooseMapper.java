package team.keephealth.yjj.mapper.action;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.action.PlanChoose;

@Mapper
@Repository
public interface PlanChooseMapper extends BaseMapper<PlanChoose> {

    // 运动计划选择清零
    @Select("UPDATE p_choose  " +
            "SET sport_plan = -1 " +
            "WHERE account_id = #{accountId}")
    void clearSport(@Param("accountId") Long accountId);

    // 食谱选择清零
    @Select("UPDATE p_choose  " +
            "SET recipe_plan = -1 " +
            "WHERE account_id = #{accountId}")
    void clearRecipe(@Param("accountId") Long accountId);
}
