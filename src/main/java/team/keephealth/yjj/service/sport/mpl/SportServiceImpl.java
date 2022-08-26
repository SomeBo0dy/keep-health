package team.keephealth.yjj.service.sport.mpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.dto.sport.SportInfoDto;
import team.keephealth.yjj.domain.entity.sport.Sport;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.action.PlanChooseMapper;
import team.keephealth.yjj.mapper.sport.SportMapper;
import team.keephealth.yjj.mapper.sport.WWEMapper;
import team.keephealth.yjj.service.sport.SportService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SportServiceImpl extends ServiceImpl<SportMapper, Sport> implements SportService {

    @Autowired
    private SportMapper sportMapper;
    @Autowired
    private WWEMapper wweMapper;
    @Autowired
    private PlanChooseMapper chooseMapper;

    @Override
    public ResultVo<T> addSport(SportInfoDto dto) {
        if (dto.getSportType() < 1 || wweMapper.selectById(dto.getSportType()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_WWE_ERROR, "id : "+dto.getSportType(), null);
        if (dto.getSportTime() <= 0 || dto.getSportTime() > 720)
            return new ResultVo<>(AppHttpCodeEnum.DATA_TIME_ERROR, "time : "+dto.getSportTime(), null);
        if (dto.getBeginTime() == null || dto.getEndTime() == null)
            dto = updateTime(dto);
        else if (!isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        if (dto.getEnergy() < 1)
            dto.setEnergy(wweMapper.selectById(dto.getSportType()).getEnergy() * dto.getSportTime());
        if (dto.getTitle() == null || dto.getTitle().equals("")) {
            QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", SecurityUtils.getUserId());
            dto.setTitle("我的运动计划" + (sportMapper.selectCount(queryWrapper) + 1));
        }
        Sport sport = new Sport(dto);
        if (sport.getBeginTime().after(sport.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);

        if (sportMapper.insert(sport) > 0) {
            QueryWrapper<Sport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", SecurityUtils.getUserId());
            queryWrapper.orderByDesc("id");
            queryWrapper.last(" LIMIT 1");
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", sportMapper.selectOne(queryWrapper));
        }else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(sport), null);
    }

    @Override
    public ResultVo<T> updateSport(SportInfoDto dto) {
        if (dto.getSportId() < 1 || sportMapper.selectById(dto.getSportId()) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_SPORT_ID_ERROR, "id : "+dto.getSportId(), null);
        Sport sport = sportMapper.selectById(dto.getSportId());
        if (!sport.getAccountId().equals(SecurityUtils.getUserId()))
            return new ResultVo<>(AppHttpCodeEnum.NO_OPERATOR_AUTH, "sport user : "+sport.getAccountId()+
                    "\nmy : "+ SecurityUtils.getUserId(), null);
        if (dto.getSportType() < 1 || wweMapper.selectById(dto.getSportType()) == null)
            dto.setSportType(sport.getSportType());
        if (dto.getSportTime() < 0 || dto.getSportTime() > 720)
            return new ResultVo<>(AppHttpCodeEnum.DATA_TIME_ERROR, "time : "+dto.getSportTime(), null);
        if (dto.getSportTime() == 0)
            dto.setSportTime(sport.getSportTime());
        if (dto.getEnergy() < 1)
            dto.setEnergy(sport.getEnergy());
        if ((dto.getBeginTime() != null || dto.getEndTime() != null) && !isTime(dto.getBeginTime(), dto.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);

        Sport up = new Sport(dto);
        if (sport.getBeginTime().after(sport.getEndTime()))
            return new ResultVo<>(AppHttpCodeEnum.DATA_END_TIME_ERROR, "begin : "+dto.getBeginTime()
                    +"\nend : "+dto.getEndTime(), null);
        up.setId(dto.getSportId());
        if (sportMapper.updateById(up) > 0)
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", sportMapper.selectById(dto.getSportId()));
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, JSONObject.toJSONString(up), null);
    }

    @Override
    public ResultVo<T> getDetail(Long sportId) {
        if (sportId < 1 || sportMapper.selectById(sportId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_SPORT_ID_ERROR, "id : "+sportId, null);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", sportMapper.selectById(sportId));
    }

    @Override
    public ResultVo<T> deleteSport(Long sportId) {
        if (sportId < 1 || sportMapper.selectById(sportId) == null)
            return new ResultVo<>(AppHttpCodeEnum.DATA_SPORT_ID_ERROR, "id : "+sportId, null);
        Sport sport = sportMapper.selectById(sportId);
        if (sportMapper.deleteById(sportId) > 0) {
            if (sport.getPlanId() != null && sport.getPlanId() > 0){
                QueryWrapper<Sport> wrapper = new QueryWrapper<>();
                wrapper.eq("account_id", sport.getAccountId());
                wrapper.eq("plan_id", sport.getPlanId());
                if (sportMapper.selectCount(wrapper) == 1) chooseMapper.clearSport(sport.getAccountId());
            }
            return new ResultVo<>(AppHttpCodeEnum.SUCCESS, "", null);
        }
        else
            return new ResultVo<>(AppHttpCodeEnum.ERROR, "id : "+sportId, null);
    }

    public SportInfoDto updateTime(SportInfoDto dto){
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String today = p.format(new Date());
            if (dto.getBeginTime() == null && dto.getEndTime() == null){
                dto.setBeginTime(today);
                dto.setEndTime(today);
            }else if (dto.getBeginTime() == null){
                if (p.parse(dto.getEndTime()).before(p.parse(today)))
                    dto.setBeginTime(dto.getEndTime());
                dto.setBeginTime(today);
            }else if (dto.getEndTime() == null){
                if (p.parse(dto.getBeginTime()).after(p.parse(today)))
                    dto.setEndTime(dto.getBeginTime());
                dto.setEndTime(today);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public boolean isTime(String start, String end){
        SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd");
        boolean jd = true;
        try {
            p.setLenient(false);
            if (start!=null) p.parse(start);
            if (end!=null) p.parse(end);
        } catch (ParseException e) {
            jd = false;
        }
        if (start!=null){
            String year = start.split("-")[0];
            if(year.startsWith("0") || year.length()!=4) jd = false;
        }
        if (end!=null){
            String year = end.split("-")[0];
            if(year.startsWith("0") || year.length()!=4) jd = false;
        }
        return jd;
    }
}
