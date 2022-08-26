package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.BeanCopyUtils;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CurProfileDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TarProfileDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserProfile;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserCurProfileVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserTarProfileVo;
import team.keephealth.xyj.modules.keephealth.mapper.UserProfileMapper;
import team.keephealth.xyj.modules.keephealth.service.UserProfileService;
import team.keephealth.xyj.modules.keephealth.utils.HomePageUtils;
import team.keephealth.yjj.service.action.mpl.AimServiceImpl;

import java.util.Date;
import java.util.Objects;

/**
 * 角色外形(UserProfile)表服务实现类
 *
 * @author xyj
 * @since 2022-07-31 10:54:42
 */
@Service("userProfileService")
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {

    @Autowired
    private AimServiceImpl aimService;

    @Override
    public ResponseResult addUserCurProfile(CurProfileDto profileDto) {
        Double userHeight = profileDto.getUserHeight();
        Double userWeight = profileDto.getUserWeight();
        Date userBirthday = profileDto.getUserBirthday();
        if (Objects.isNull(userHeight)){
            throw new SystemException(AppHttpCodeEnum.USER_HEIGHT_NOT_NULL);
        }
        if (Objects.isNull(userWeight)){
            throw new SystemException(AppHttpCodeEnum.USER_WEIGHT_NOT_NULL);
        }
        if (Objects.isNull(userBirthday)){
            throw new SystemException(AppHttpCodeEnum.USER_BIRTHDAY_NOT_NULL);
        }
        UserProfile userProfile = BeanCopyUtils.copeBean(profileDto, UserProfile.class);
        Long id = SecurityUtils.getLoginUser().getUser().getId();
        userProfile.setUserId(id);
        try{
            save(userProfile);
        }catch (DuplicateKeyException e){
            throw new SystemException(AppHttpCodeEnum.USER_PROFILE_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserCurProfile() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getUserId,userId);
        UserProfile profile = getOne(queryWrapper);
        if (Objects.isNull(profile)){
            throw new SystemException(AppHttpCodeEnum.USER_PROFILE_NOT_EXIST);
        }
        UserCurProfileVo userCurProfileVo = BeanCopyUtils.copeBean(profile, UserCurProfileVo.class);
        return ResponseResult.okResult(userCurProfileVo);
    }

    @Override
    public ResponseResult editUserCurProfile(CurProfileDto profileDto) {
        Double userHeight = profileDto.getUserHeight();
        Double userWeight = profileDto.getUserWeight();
        Date userBirthday = profileDto.getUserBirthday();
        if (Objects.isNull(userHeight)){
            throw new SystemException(AppHttpCodeEnum.USER_HEIGHT_NOT_NULL);
        }
        if (Objects.isNull(userWeight)){
            throw new SystemException(AppHttpCodeEnum.USER_WEIGHT_NOT_NULL);
        }
        if (Objects.isNull(userBirthday)){
            throw new SystemException(AppHttpCodeEnum.USER_BIRTHDAY_NOT_NULL);
        }
        Long id = SecurityUtils.getUserId();
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getUserId,id);
        UserProfile profile = getOne(queryWrapper);
        if (Objects.isNull(profile)){
            throw new SystemException(AppHttpCodeEnum.ERROR);
        }
        profile.setUserHeight(userHeight);
        profile.setUserWeight(userWeight);
        profile.setUserBirthday(userBirthday);
        boolean update = updateById(profile);
        if (!update){
            throw new SystemException(AppHttpCodeEnum.ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addUserTarProfile(TarProfileDto profileDto) {
        Long targetFinishTime = profileDto.getTargetFinishTime();
        Double targetWeight = profileDto.getTargetWeight();
        if (Objects.isNull(targetFinishTime)){
            throw new SystemException(AppHttpCodeEnum.TIME_NOT_NULL);
        }
        if (Objects.isNull(targetWeight)){
            throw new SystemException(AppHttpCodeEnum.USER_TARGET_WEIGHT_NOT_NULL);
        }
        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<UserProfile> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(UserProfile::getUserId,userId);
        queryWrapper.set(UserProfile::getUserTargetWeight,targetWeight);
        queryWrapper.set(UserProfile::getTargetFinishTime,targetFinishTime);
        boolean update = update(queryWrapper);
        if (!update){
            throw new SystemException(AppHttpCodeEnum.CUR_PROFILE_NULL);
        }
        aimService.kcalCount();
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserTarProfile() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<UserProfile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserProfile::getUserId,userId);
        UserProfile profile = getOne(queryWrapper);
        if (Objects.isNull(profile)){
            throw new SystemException(AppHttpCodeEnum.USER_PROFILE_NOT_EXIST);
        }
        UserTarProfileVo userTarProfileVo = BeanCopyUtils.copeBean(profile, UserTarProfileVo.class);
        if (Objects.isNull(userTarProfileVo.getUserTargetWeight())||Objects.isNull(userTarProfileVo.getTargetFinishTime())){
            throw new SystemException(AppHttpCodeEnum.USER_TAR_PROFILE_NOT_EXIST);
        }
        return ResponseResult.okResult(userTarProfileVo);
    }

    @Override
    public ResponseResult getUserTarCal() {
        return null;
    }
}

