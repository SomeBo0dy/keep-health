package team.keephealth.yjj.service.sport.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.TimeQueryMy;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.domain.vo.SportVo;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.mapper.sport.SportPlanMapper;
import team.keephealth.yjj.service.sport.SportQueryService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SportQueryServiceImpl extends ServiceImpl<SportMapper, Sport> implements SportQueryService {

    @Autowired
    private SportMapper sportMapper;
    @Autowired
    private SportServiceImpl sportService;
    @Autowired
    private SportPlanMapper planMapper;

    @Override
    public ResultVo<T> queryAll() {
        QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.orderByDesc("id");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVo(sportMapper.selectList(queryWrapper)));
    }

    @Override
    public ResultVo<T> queryByTime(TimeQueryMy dto) {
        if ((dto.getBeginTime() == null && dto.getEndTime() == null) || !sportService.isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.apply(StringUtils.hasText(dto.getBeginTime()),
                "date_format (begin_time, '%Y-%m-%d') >= date_format('" + dto.getBeginTime() + "','%Y-%m-%d')")
                .apply(StringUtils.hasText(dto.getEndTime()),
                        "date_format (end_time, '%Y-%m-%d') <= date_format('" + dto.getEndTime() + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVo(sportMapper.selectList(queryWrapper)));
    }

    @Override
    public ResultVo<T> queryNow() {
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        String today = p.format(new Date());
        QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", SecurityUtils.getUserId());
        queryWrapper.apply("date_format (begin_time, '%Y-%m-%d') <= date_format('" + today + "','%Y-%m-%d')")
                .apply("date_format (end_time, '%Y-%m-%d') >= date_format('" + today + "','%Y-%m-%d')");
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", getVo(sportMapper.selectList(queryWrapper)));
    }

    public List<SportVo> getVo(List<Sport> sports){
        List<SportVo> vos = new ArrayList<>();
        for (Sport sport : sports) {
            SportVo vo = new SportVo();
            BeanUtils.copyProperties(sport, vo);
            vo.setAccountId(null);
            if (sport.getPlanId() != null && planMapper.selectById(sport.getPlanId()) != null)
                vo.setSport(planMapper.selectById(sport.getPlanId()));
            vos.add(vo);
        }
        return vos;
    }
}
