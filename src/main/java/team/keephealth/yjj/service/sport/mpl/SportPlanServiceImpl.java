package team.keephealth.yjj.service.sport.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.Aims;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.sport.SportPlanDInfoDto;
import team.keephealth.yjj.domain.dto.sport.SportPlanInfoDto;
import team.keephealth.yjj.domain.entity.action.Memory;
import team.keephealth.yjj.domain.entity.sport.SportPlan;
import team.keephealth.yjj.domain.entity.sport.SportPlanDetail;
import team.keephealth.yjj.domain.entity.sport.SportPlanState;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.SportPlanDetailVo;
import team.keephealth.yjj.domain.vo.SportPlanVo;
import team.keephealth.yjj.mapper.action.MemoryMapper;
import team.keephealth.yjj.mapper.sport.SportPlanDetailMapper;
import team.keephealth.yjj.mapper.sport.SportPlanMapper;
import team.keephealth.yjj.mapper.sport.SportPlanStateMapper;
import team.keephealth.yjj.mapper.sport.WWEMapper;
import team.keephealth.yjj.service.sport.SportPlanService;

import java.util.List;

@Service
public class SportPlanServiceImpl extends ServiceImpl<SportPlanMapper, SportPlan> implements SportPlanService {

    @Autowired
    private SportPlanMapper planMapper;
    @Autowired
    private SportPlanDetailMapper detailMapper;
    @Autowired
    private SportPlanStateMapper stateMapper;
    @Autowired
    private WWEMapper wweMapper;
    @Autowired
    private SportPlanCServiceImpl cService;
    @Autowired
    private MemoryMapper memoryMapper;

    @Override
    public ResultVo<T> addPlan(SportPlanInfoDto dto) {
        boolean addKcal = false;
        if (dto.getAim() < 1 || !Aims.isExist(dto.getAim()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_AIM_ERROR, "aim : "+dto.getAim(), null);
        if (dto.getKcal() < 0)
            addKcal = true;
        if (dto.getList().size() == 0)
            return new ResultVo<>(AppHttpCodeEnum.EMPTY_DETAIL_LIST, "", null);
        for (SportPlanDInfoDto d : dto.getList()) {
            if (d.getSport() < 1 || wweMapper.selectById(d.getSport()) == null)
                return new ResultVo<>(AppHttpCodeEnum.DATA_WWE_ERROR, "id : "+d.getSport(), null);
        }

        if (dto.getTitle() == null || dto.getTitle().equals(""))
            dto.setTitle("运动计划" + (planMapper.selectCount(null)+1));
        SportPlan plan = new SportPlan(dto);
        if (planMapper.insert(plan) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(plan), null);
        QueryWrapper<SportPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.orderByDesc("id");
        queryWrapper.last(" LIMIT 1");
        plan = planMapper.selectOne(queryWrapper);
        for (int i = 0; i < dto.getList().size(); ++i) {
            SportPlanDInfoDto d = dto.getList().get(i);
            if (d.getState() < 1) d.setState(i+1);
            if (d.getName() == null || d.getName().equals("")) d.setName("第"+(i+1)+"阶段");

            if (d.getDays() < 0) d.setDays(0);
            if (d.getExercise() < 0) d.setExercise(d.getDays());
            if (d.getExercise() > d.getDays()){
                d.setExercise(d.getDays());
                d.setRelax(0);
            }
            if (d.getRelax() < 0 || d.getRelax() > (d.getDays() - d.getExercise())) d.setRelax(0);
            if (d.getTime() < 0 || d.getTime() > 720) d.setTime(0);
            if (d.getEnergy() < 0) d.setEnergy(wweMapper.selectById(d.getSport()).getEnergy() * d.getSport());

            if (d.getTol() < 0) {
                int days;
                if (d.getRelax() == 0 || d.getExercise() == d.getDays()) days = d.getDays();
                else {
                    days = d.getDays() / (d.getExercise() + d.getRelax()) * d.getExercise();
                    days += Math.min((d.getDays() % (d.getExercise() + d.getRelax())), d.getExercise());
                }
                d.setTol(d.getEnergy() * days);
            }
            if (addKcal) plan.setKcal(plan.getKcal() + d.getTol());
            SportPlanState state = new SportPlanState(d);
            state.setPid(plan.getId());
            if (stateMapper.insert(state) <= 0){
                planMapper.deleteById(plan.getId());
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(state), null);
            }
            SportPlanDetail detail = new SportPlanDetail(d);
            detail.setPid(plan.getId());
            if (detailMapper.insert(detail) <= 0 )
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(detail), null);
        }
        if (addKcal) planMapper.updateById(plan);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", planDetails(plan.getId()));
    }

    @Override
    public ResultVo<T> updatePlan(SportPlanInfoDto dto) {
        if (dto.getPlanId() == null || dto.getPlanId() < 1 || planMapper.selectById(dto.getPlanId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+dto.getPlanId(), null);
        SportPlan planOrg = planMapper.selectById(dto.getPlanId());
        if (!planOrg.getAccountId().equals(SecurityUtils.getUserId()))
            return new ResultVo<>(AppHttpCodeEnum.NO_OPERATOR_AUTH, "plan writer : "+planOrg.getAccountId()
            + "\nmy : "+SecurityUtils.getUserId(), null);

        if (!(dto.getAim() <= 0 && dto.getTitle() == null && dto.getWords() == null && dto.getKcal() < 0) ){
            SportPlan plan = new SportPlan(dto);
            plan.setId(planOrg.getId());
            if (plan.getAim() < 1 || !Aims.isExist(plan.getAim())) plan.setAim(planOrg.getAim());
            if (plan.getTitle() == null || plan.getTitle().equals("")) plan.setTitle(planOrg.getTitle());
            if (plan.getWords() == null || plan.getWords().equals("")) plan.setWords(planOrg.getWords());
            if (plan.getKcal() < 0) plan.setKcal(planOrg.getKcal());
            if (planMapper.updateById(plan) <= 0) {
                planMapper.updateById(planOrg);
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(plan), null);
            }
        }

        QueryWrapper<SportPlanState> sq = new QueryWrapper<>();
        sq.eq("pid", dto.getPlanId());
        QueryWrapper<SportPlanDetail> dq = new QueryWrapper<>();
        dq.eq("pid", dto.getPlanId());
        String message = "";
        for (SportPlanDInfoDto d : dto.getList()) {
            sq.eq("plan_state", d.getState());
            SportPlanState stateOrg = stateMapper.selectOne(sq);
            if (stateOrg == null){
                message = message.concat("state参数错误 ： "+d.getState()+"\n");
                break;
            }
            if (!(d.getName() == null && d.getTol() < 0 && d.getMsg() == null && d.getTips() == null)){
                if (d.getName() == null || d.getName().equals("")) d.setName(stateOrg.getStateName());
                if (d.getTol() < 0) d.setTol(stateOrg.getTotalKcal());
                if (d.getMsg() == null || d.getMsg().equals("")) d.setMsg(stateOrg.getMsg());
                if (d.getTips() == null || d.getTips().equals("")) d.setTips(stateOrg.getTips());
                SportPlanState state = new SportPlanState(d);
                state.setId(stateOrg.getId());
                if (stateMapper.updateById(state) <= 0)
                    return new ResultVo<>(AppHttpCodeEnum.ERROR, message+"\n"+JSONObject.toJSONString(state), null);
            }
            dq.eq("plan_state", d.getState());
            SportPlanDetail detailOrg = detailMapper.selectOne(dq);
            if (!(d.getDays() < 0 && d.getExercise() < 0 && d.getRelax() < 0 && d.getSport() < 1 && d.getTime() < 0 && d.getEnergy() < 0 && d.getPict() == null))
                if (d.getDays() < 0 ) d.setDays(detailOrg.getTotalDay());
                if (d.getExercise() < 0 ) d.setExercise(detailOrg.getRoundExercise());
                if (d.getRelax() < 0 ) d.setRelax(detailOrg.getRoundRelax());
                if (d.getSport() < 1 || wweMapper.selectById(d.getSport()) == null)
                    d.setSport(detailOrg.getSportType());
                if (d.getTime() < 0) d.setTime(detailOrg.getSportTime());
                if (d.getEnergy() < 0) d.setEnergy(detailOrg.getEnergy());
                SportPlanDetail detail = new SportPlanDetail(d);
                detail.setId(detailOrg.getId());
                if (detailMapper.updateById(detail) <= 0)
                    return new ResultVo<>(AppHttpCodeEnum.ERROR, message+"\n" + JSONObject.toJSONString(detail), null);

        }
        SportPlanVo vo = planDetails(dto.getPlanId());
        if (dto.getKcal() < 0){
            double num = 0;
            for (SportPlanDetailVo detail : vo.getList()) num += detail.getTol();
            SportPlan addK = planMapper.selectById(dto.getPlanId());
            addK.setKcal(num);
            if (planMapper.updateById(addK) <= 0)
                return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(addK),null);
            vo.setKcal(num);
        }
        return new ResultVo(AppHttpCodeEnum.SUCCESS, message, vo);
    }

    @Override
    public ResultVo<T> deletePlan(Long planId) {
        if (planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        if (planMapper.deleteById(planId) > 0) {
            cService.deleteChoose(planId);
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "id : "+planId, null);
    }

    @Override
    public ResultVo<T> getPlan(Long planId) {
        if (planId < 1 || planMapper.selectById(planId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_PLAN_ID_ERROR, "id : "+planId, null);
        Memory memory = new Memory(SecurityUtils.getUserId(), planId);
        memoryMapper.insert(memory);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", planDetails(planId));
    }

    public SportPlanVo planDetails(Long planId){
        SportPlanVo planVo = planMapper.getPlan(planId);
        planVo.setAim(Aims.getName(Integer.parseInt(planVo.getAim())));
        planVo.setList(stateMapper.getDetails(planId));
        return planVo;
    }
}
