package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.CurProfileDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.TarProfileDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserProfile;


/**
 * 角色外形(UserProfile)表服务接口
 *
 * @author xyj
 * @since 2022-07-31 10:54:42
 */
public interface UserProfileService extends IService<UserProfile> {

    ResponseResult addUserCurProfile(CurProfileDto profileDto);

    ResponseResult getUserCurProfile();

    ResponseResult editUserCurProfile(CurProfileDto profileDto);

    ResponseResult addUserTarProfile(TarProfileDto profileDto);

    ResponseResult getUserTarProfile();

    ResponseResult getUserTarCal();
}

