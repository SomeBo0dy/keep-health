package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.BeanCopyUtils;
import team.keephealth.common.utils.RedisCache;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.PasswordUserDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.LoginUser;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.domain.entity.UserRole;
import team.keephealth.xyj.modules.keephealth.mapper.MenuMapper;
import team.keephealth.xyj.modules.keephealth.mapper.UserRoleMapper;
import team.keephealth.xyj.modules.keephealth.service.UserLoginService;
import team.keephealth.xyj.modules.keephealth.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static team.keephealth.common.constants.SystemConstants.ROLE_MAP;
import static team.keephealth.common.constants.SystemConstants.TYPE_MAP;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userId
        Long userId = loginUser.getUser().getId();
        //删除redis中的id
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }

    @Override
    public LoginUser getByEmailAndType(String email, String type) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.eq(User::getType, type);
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            return null;
        }
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user, list);
    }

    @Override
    public LoginUser getByPasswordAndType(String account, String password, String type) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.eq(User::getType, type);
        User user = userService.getOne(queryWrapper);
        //对比密码
        if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user, list);
    }

    @Override
    public ResponseResult register(PasswordUserDto userRegisterDto) {
        if (!StringUtils.hasText(userRegisterDto.getAccount())) {
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getType())) {
            throw new SystemException(AppHttpCodeEnum.TYPE_NOT_NULL);
        }
        if (!TYPE_MAP.keySet().contains(userRegisterDto.getType())){
            throw new SystemException(AppHttpCodeEnum.TYPE_ERROR);
        }
        if (!checkAccount(userRegisterDto)) {
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_EXIST);
        }
        //对密码加密
        String encodePassword = passwordEncoder.encode(userRegisterDto.getPassword());
        User user = BeanCopyUtils.copeBean(userRegisterDto, User.class);
        user.setPassword(encodePassword);
        //拼接账号作为用户名
        String nickName = TYPE_MAP.get(userRegisterDto.getType()) + "_" + userRegisterDto.getAccount();
        user.setNickName(nickName);
        //存入数据库
        userService.save(user);
        //配置角色表
        Long id = user.getId();
        UserRole userRole = new UserRole(id, ROLE_MAP.get(userRegisterDto.getType()));
        userRoleMapper.insert(userRole);
        return ResponseResult.okResult();
    }

    private boolean checkAccount(PasswordUserDto userRegisterDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, userRegisterDto.getAccount());
        queryWrapper.eq(User::getType, userRegisterDto.getType());
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            return true;
        }
        return false;
    }
}
