package team.keephealth.yjj.service.sport.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.Aims;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.model.DataBase;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.dto.sport.SportPlanQDto;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.entity.sport.SportPlanDetail;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.SportPlanQVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.sport.SportPlanDetailMapper;
import team.keephealth.yjj.mapper.sport.SportPlanMapper;
import team.keephealth.yjj.service.sport.SportPlanQueryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportPlanQServiceImpl extends ServiceImpl<SportPlanMapper, SportPlan> implements SportPlanQueryService {

    @Autowired
    private SportPlanMapper planMapper;
    @Autowired
    private SportPlanDetailMapper detailMapper;
    @Autowired
    private PlanChooseMapper chooseMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo<T> queryAll(SportPlanQDto dto) {
        if (dto.getUp() < dto.getDown())
            return new ResultVo<>(AppHttpCodeEnum.DATA_KCAL_RANGE_ERROR, "up : "+ dto.getUp()
                    +"\ndown : "+ dto.getDown(), null );
        List<SportPlanQVo> list = query(dto, false);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo<T> queryByAc(SportPlanQDto dto) {
        if (dto.getUp() < dto.getDown())
            return new ResultVo<>(AppHttpCodeEnum.DATA_KCAL_RANGE_ERROR, "up : "+ dto.getUp()
                    +"\ndown : "+ dto.getDown(), null );
        if (dto.getAccountId() == null || userMapper.selectById(dto.getAccountId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.WATCHED_USER_NOT_EXIST, "id : "+dto.getAccountId(), null);
        List<SportPlanQVo> list = query(dto, true);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo<T> getRecommend() {
        DataBase dataBase = new DataBase();
        List<Long> ids = dataBase.getScore(SecurityUtils.getUserId());
        List<SportPlan> list = new ArrayList<>();
        for (Long id : ids) list.add(planMapper.selectById(id));
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", voMsg(copy(list)));
    }

    public List<SportPlanQVo> query(SportPlanQDto dto, boolean byUser){
        QueryWrapper<SportPlan> queryWrapper = new QueryWrapper<>();
        if (dto.getAim() > 0 && dto.getAim() <= Aims.values().length)
            queryWrapper.eq("aim", dto.getAim());
        if (byUser)
            queryWrapper.eq("account_id", dto.getAccountId());
        if (dto.getDown() > 0)
            queryWrapper.ge("kcal", dto.getDown());
        if (dto.getUp() > 0)
            queryWrapper.le("kcal", dto.getUp());
        queryWrapper.orderByDesc("add_time");
        List<SportPlan> l = planMapper.selectList(queryWrapper);
        List<SportPlanQVo> list = voMsg(copy(l));
        return list;
    }

    public List<SportPlanQVo> voMsg(List<SportPlanQVo> list){
        for (SportPlanQVo vo : list) {
            QueryWrapper<SportPlanDetail> query = new QueryWrapper<>();
            query.eq("pid", vo.getId());
            query.eq("plan_state", 1);
            if (detailMapper.selectOne(query) != null) vo.setPict(detailMapper.selectOne(query).getPict());
            QueryWrapper<PlanChoose> chooseQuery = new QueryWrapper<>();
            chooseQuery.eq("sport_plan", vo.getId());
            chooseQuery.eq("account_id", SecurityUtils.getUserId());
            vo.setChoose(chooseMapper.selectOne(chooseQuery) != null);
        }
        return list;
    }

    public List<SportPlanQVo> copy(List<SportPlan> plans){
        List<SportPlanQVo> vos = new ArrayList<>();
        for (SportPlan plan : plans) {
            SportPlanQVo vo = new SportPlanQVo();
            BeanUtils.copyProperties(plan, vo);
            vos.add(vo);
        }
        return vos;
    }
}
