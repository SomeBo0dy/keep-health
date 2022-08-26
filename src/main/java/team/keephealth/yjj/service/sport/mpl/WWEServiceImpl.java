package team.keephealth.yjj.service.sport.mpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.yjj.domain.entity.sport.WWE;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.sport.WWEMapper;
import team.keephealth.yjj.service.sport.WWEService;

@Service
public class WWEServiceImpl extends ServiceImpl<WWEMapper, WWE> implements WWEService {

    @Autowired
    private WWEMapper wweMapper;

    @Override
    public ResultVo<T> getWWE() {
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", wweMapper.selectList(null));
    }
}
