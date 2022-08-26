package team.keephealth.yjj.mapper.sport;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.vo.SportPlanVo;

@Mapper
@Repository
public interface SportPlanMapper extends BaseMapper<SportPlan> {

    @Select("SELECT p.id as pid, p.account_id as uid, u.nick_name as name, u.avatar as photo,  " +
            "p.aim, p.title, p.words, p.kcal, p.add_time as time  " +
            "FROM p_sport as p " +
            "LEFT JOIN sys_user as u " +
            "ON(p.account_id = u.id) " +
            "WHERE p.id = #{planId}")
    SportPlanVo getPlan(@Param("planId") Long planId);

}
