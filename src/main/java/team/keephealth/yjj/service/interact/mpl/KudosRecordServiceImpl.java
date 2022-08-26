package team.keephealth.yjj.service.interact.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.yjj.domain.entity.articles.KudosRecord;
import team.keephealth.yjj.domain.vo.articles.NewKudosList;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.articles.KudosMapper;
import team.keephealth.yjj.mapper.articles.KudosRecordMapper;
import team.keephealth.yjj.service.interact.KudosRecordService;

import java.util.List;

@Service
public class KudosRecordServiceImpl extends ServiceImpl<KudosRecordMapper, KudosRecord> implements KudosRecordService {

    @Autowired
    private KudosRecordMapper kudosRecordMapper;
    @Autowired
    private KudosMapper kudosMapper;

    @Override
    public ResultVo getNewKudos() {
        Long id = SecurityUtils.getUserId();
        List<NewKudosList> list = kudosRecordMapper.newKudos(id);
        kudosRecordMapper.updateRead(id);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }

    @Override
    public ResultVo getNKudosNum() {
        Long id = SecurityUtils.getUserId();
        QueryWrapper<KudosRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", id);
        queryWrapper.eq("msg_read", 0);
        int num = kudosRecordMapper.selectCount(queryWrapper);
        if (num < 0)
            return new ResultVo(AppHttpCodeEnum.DATA_WRONG, "number : "+num, null);
        else
            return new ResultVo(AppHttpCodeEnum.SUCCESS, "", num);
    }

    @Override
    public ResultVo getMyRKudos() {
        Long id = SecurityUtils.getUserId();
        List<NewKudosList> list = kudosRecordMapper.allKudos(id);
        kudosRecordMapper.updateRead(id);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", list);
    }
}
