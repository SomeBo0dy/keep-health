package team.keephealth.yjj.service.sport.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.Aims;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.action.PlanChoose;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.entity.sport.WWE;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.SportPlanDetailVo;
import team.keephealth.yjj.domain.vo.SportPlanVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.mapper.sport.SportPlanMapper;
import team.keephealth.yjj.mapper.sport.SportPlanStateMapper;
import team.keephealth.yjj.mapper.sport.WWEMapper;
import team.keephealth.yjj.service.sport.SportPlanCService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class SportPlanCServiceImpl  extends ServiceImpl<SportPlanMapper, SportPlan> implements SportPlanCService {

    @Autowired
    private SportPlanMapper planMapper;
    @Autowired
    private PlanChooseMapper chooseMapper;
    @Autowired
    private SportMapper sportMapper;
    @Autowired
    private WWEMapper wweMapper;
    @Autowired
    private SportPlanStateMapper stateMapper;

    @Override
    public ResultVo<T> choosePlan(Long planId) {
        if (planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        Long id = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        PlanChoose c = chooseMapper.selectOne(queryWrapper);
        if (!(c == null || c.getSportPlan() == null || c.getSportPlan() < 1))
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_ERROR, JSONObject.toJSONString(c), null);
        if (c == null){
            PlanChoose choose = new PlanChoose();
            choose.setAccountId(id);
            choose.setSportPlan(planId);
            if ( chooseMapper.insert(choose) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(choose), null);
        }else {
            c.setSportPlan(planId);
            if (chooseMapper.updateById(c) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(c), null);
        }

        SportPlanVo planVo = planDetails(planId);
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String begin = p.format(new Date());
        for (SportPlanDetailVo vo : planVo.getList()) {
            Calendar calendar = Calendar.getInstance();
            String end = begin;
            try {
                calendar.setTime(p.parse(end));
                calendar.add(Calendar.DATE, vo.getDays());
                end = p.format(calendar.getTime());
                Sport sport = new Sport(vo, begin, end);
                QueryWrapper<WWE> wq = new QueryWrapper<>();
                wq.eq("wwe", vo.getSport());
                sport.setSportType(wweMapper.selectOne(wq).getId());
                sport.setPlanId(planId);
                if (sportMapper.insert(sport) <= 0)
                    return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(sport), null);
                calendar.add(Calendar.DATE, 1);
                begin = p.format(calendar.getTime());
            } catch (ParseException e) {
                log.debug(String.valueOf(e));
            }
        }
        return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
    }

    @Override
    public ResultVo<T> deleteChoose(Long planId) {
        if (planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        Long id = SecurityUtils.getUserId();
        QueryWrapper<PlanChoose> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sport_plan", planId);
        queryWrapper.eq("account_id", id);
        PlanChoose c = chooseMapper.selectOne(queryWrapper);
        if (c == null)
            return new ResultVo<>(AppHttpCodeEnum.GET_CHOOSE_NOR, "id : "+planId, null);
        QueryWrapper<Sport> delete = new QueryWrapper<>();
        delete.eq("account_id", id);
        delete.eq("plan_id", planId);
        if (sportMapper.delete(delete) > 0) {
            chooseMapper.clearSport(id);
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "", null);
    }

    public SportPlanVo planDetails(Long planId){
        SportPlanVo planVo = planMapper.getPlan(planId);
        planVo.setAim(Aims.getName(Integer.parseInt(planVo.getAim())));
        planVo.setList(stateMapper.getDetails(planId));
        return planVo;
    }
}
