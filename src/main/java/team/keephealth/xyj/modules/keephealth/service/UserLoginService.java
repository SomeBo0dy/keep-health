package team.keephealth.xyj.modules.keephealth.service;

import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.PasswordUserDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.LoginUser;

public interface UserLoginService {

    ResponseResult logout();

    LoginUser getByEmailAndType(String email, String type);

    LoginUser getByPasswordAndType(String account, String password, String type);

    ResponseResult register(PasswordUserDto userRegisterDto);
}
