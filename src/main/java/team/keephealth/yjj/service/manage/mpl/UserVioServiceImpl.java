package team.keephealth.yjj.service.manage.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.mapper.UserMapper;
import team.keephealth.yjj.domain.entity.manage.UserViolation;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.mapper.manage.UserViolationMapper;
import team.keephealth.yjj.service.manage.UserViolationService;

@Service
public class UserVioServiceImpl extends ServiceImpl<UserViolationMapper, UserViolation> implements UserViolationService {

    @Autowired
    private UserViolationMapper violationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVo<T> getVio(Long accountId) {
        if (accountId == null || userMapper.selectById(accountId) == null)
            return new ResultVo<>(AppHttpCodeEnum.USER_ID_NOT_NULL, "id : "+accountId, null);
        QueryWrapper<UserViolation> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        return new ResultVo(AppHttpCodeEnum.SUCCESS, "", violationMapper.selectOne(wrapper));
    }

    @Override
    public UserViolation getUserVio(Long userId){
        QueryWrapper<UserViolation> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", userId);
        UserViolation userViolation = violationMapper.selectOne(wrapper);
        if (userViolation == null){
            UserViolation user = new UserViolation();
            user.setAccountId(userId);
            violationMapper.insert(user);
            userViolation = violationMapper.selectOne(wrapper);
        }
        return userViolation;
    }

    @Override
    public boolean countVio(UserViolation userViolation) {
        int count = userViolation.getArticleOne() * 100 + userViolation.getArticleTwo() * 70 +
                userViolation.getArticleThree() * 30 + userViolation.getCommentOne() * 50 +
                userViolation.getCommentTwo() * 30 + userViolation.getCommentThree() * 10;
        if (count >= 100){
            User user = userMapper.selectById(userViolation.getAccountId());
            user.setState("1");
            userMapper.updateById(user);
            return true;
        }
        return false;
    }
}
