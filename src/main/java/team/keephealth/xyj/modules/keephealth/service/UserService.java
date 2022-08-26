package team.keephealth.xyj.modules.keephealth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.*;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;

/**
 * 用户表(User)表服务接口
 */
public interface UserService extends IService<User> {


    ResponseResult editAvatar(AvatarDto avatarDto);

    ResponseResult editUserInfo(UserInfoDto userInfoDto);

    ResponseResult editPassword(PasswordDto passwordDto);

    ResponseResult getUserInfo();

    ResponseResult getUserInfoById(Long id);

    ResponseResult getUserInfoByPage(SelectUserPageDto userPageDto);

    ResponseResult setUserState(StateDto stateDto);

    ResponseResult cancelAccount();

    ResponseResult sysCancelAccount(Long userId);
}
