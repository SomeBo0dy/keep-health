package team.keephealth.yjj.service.action.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.Aims;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserProfile;
import team.keephealth.xyj.modules.keephealth.mapper.UserProfileMapper;
import team.keephealth.yjj.domain.dto.action.AimInfoDto;
import team.keephealth.yjj.domain.entity.action.Aim;
import team.keephealth.yjj.domain.entity.action.UserAim;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.action.AimVo;
import team.keephealth.yjj.mapper.action.AimMapper;
import team.keephealth.yjj.mapper.action.UserAimMapper;
import team.keephealth.yjj.service.action.AimService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AimServiceImpl extends ServiceImpl<AimMapper, Aim> implements AimService {

    @Autowired
    private AimMapper aimMapper;
    @Autowired
    private UserAimMapper userAimMapper;
    @Autowired
    private UserProfileMapper profileMapper;

    @Override
    public ResultVo<T> kcalMessage() {
        Aim aim = getToday();
        AimVo vo = new AimVo();
        BeanUtils.copyProperties(aim, vo);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
    }

    @Override
    public ResultVo<T> aims() {
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", Aims.getAims());
    }

    @Override
    public ResultVo<T> updateAim(AimInfoDto dto) {
        Aim org = getToday();
        QueryWrapper<UserAim> q = new QueryWrapper<>();
        q.eq("account_id", SecurityUtils.getUserId());
        UserAim userAim = userAimMapper.selectOne(q);
        if (dto.getAim() >= 0 || dto.getAim() <= Aims.values().length)
            userAim.setAim(dto.getAim());
        if (dto.getAbsorbKcal() >= 0) userAim.setAbsorb(dto.getAbsorbKcal());
        if (dto.getExpendKcal() >= 0) userAim.setExpend(dto.getExpendKcal());
        if (userAimMapper.updateById(userAim) <= 0)
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(userAim), null);

        org.updateMsg(userAim);
        if (aimMapper.updateById(org) > 0){
            AimVo vo = new AimVo();
            BeanUtils.copyProperties(getToday(), vo);
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", vo);
        }else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(org), null);
    }

    public Aim getToday(){
        Long id = SecurityUtils.getUserId();
        Date date = new Date();
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(date);
        QueryWrapper<Aim> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", id);
        wrapper.apply("DATE(today) = STR_TO_DATE('"+today+"','%Y-%m-%d')");
        Aim aim = aimMapper.selectOne(wrapper);
        if (aim != null) return aim;
        QueryWrapper<UserAim> q = new QueryWrapper<>();
        q.eq("account_id", id);
        UserAim userAim = userAimMapper.selectOne(q);
        if (userAim == null){
            userAim = new UserAim(id);
            userAimMapper.insert(userAim);
        }
        Aim newAim = new Aim(userAim, date);
        aimMapper.insert(newAim);
        return aimMapper.selectOne(wrapper);
    }

    public void kcalCount(){
        Long id = SecurityUtils.getUserId();
        QueryWrapper<UserProfile> proWrapper = new QueryWrapper<>();
        proWrapper.eq("user_id", id);
        UserProfile profile = profileMapper.selectOne(proWrapper);
        if (profile == null) return;
        double absorb = profile.getUserHeight() * (profile.getUserWeight() - profile.getUserTargetWeight()) * profile.getTargetFinishTime()/ 70;
        double expend = profile.getUserHeight() * (profile.getUserWeight() - profile.getUserTargetWeight()) * 50 / profile.getTargetFinishTime();
        QueryWrapper<UserAim> aimWrapper = new QueryWrapper<>();
        aimWrapper.eq("account_id", id);
        UserAim userAim = userAimMapper.selectOne(aimWrapper);
        if (userAim == null){
            userAim = new UserAim(id);
            userAim.setAim(0);
            userAim.setAbsorb(absorb);
            userAim.setExpend(expend);
            userAimMapper.insert(userAim);
        }else {
            userAim.setAbsorb(absorb);
            userAim.setExpend(expend);
            userAimMapper.updateById(userAim);
        }
        Aim aim = getToday();
        if (aim.getAbsorbKcal() == absorb && aim.getExpendKcal() == expend) return;
        aim.setAbsorbKcal(absorb);
        aim.setExpendKcal(expend);
        aimMapper.updateById(aim);
    }
}
