package team.keephealth.yjj.mapper.action;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import team.keephealth.yjj.domain.entity.action.PunchAcc;

@Mapper
@Repository
public interface PunchAccMapper extends BaseMapper<PunchAcc> {

    @Select("UPDATE msg_punch SET " +
            "re_last = NULL ")
    void reLastNull();

    @Select("UPDATE msg_punch SET " +
            "sp_last = NULL ")
    void spLastNull();
}
