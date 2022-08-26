package team.keephealth.yjj.mapper.sport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.sport.SportPlanState;
import team.keephealth.yjj.domain.vo.SportPlanDetailVo;

import java.util.List;

@Mapper
@Repository
public interface SportPlanStateMapper extends BaseMapper<SportPlanState> {

    @Select("SELECT s.plan_state as state, s.state_name as name, s.total_kcal as tol, s.msg, s.tips, " +
            "d.total_day as days, d.round_exercise as exercise, d.round_relax as relax, w.wwe as sport, d.sport_time as time, d.energy, d.pict  " +
            "FROM p_sport_sta as s " +
            "LEFT JOIN p_sport_dtl as d " +
            "ON(d.pid = #{planId} AND d.plan_state = s.plan_state) " +
            "LEFT JOIN sys_wwe as w " +
            "ON(d.sport_type = w.id) " +
            "WHERE s.pid = #{planId} " +
            "ORDER BY s.plan_state")
    List<SportPlanDetailVo> getDetails(@Param("planId") Long planId);

}
