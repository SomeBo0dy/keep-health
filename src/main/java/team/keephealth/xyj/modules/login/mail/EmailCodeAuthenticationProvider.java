package team.keephealth.xyj.modules.login.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import team.keephealth.common.utils.RedisCache;
import team.keephealth.xyj.modules.keephealth.domain.entity.LoginUser;
import team.keephealth.xyj.modules.keephealth.service.UserLoginService;

import java.util.Objects;

import static team.keephealth.common.constants.SystemConstants.STATUS_NORMAL;

/**
 * 邮件验证过滤器
 */
@Slf4j
@Component
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserLoginService loginService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }
        EmailCodeAuthenticationToken token = (EmailCodeAuthenticationToken) authentication;
        //获取邮箱
        String email = (String) authentication.getPrincipal();
        //获取验证码
        String e_code = (String) authentication.getCredentials();
        //获取登录类型
        String type = (String) token.getType();
        //验证邮箱验证码
        String key = email + "type:" + type;
        String curCode = redisCache.getCacheObject(key);

        if (!StringUtils.hasText(curCode)) {
            throw new BadCredentialsException("验证码已过期，请重新发送验证码");
        }
        if (!curCode.equals(e_code)) {
            throw new BadCredentialsException("验证码错误");
        }
        LoginUser loginUser = loginService.getByEmailAndType(email, type);
        if (Objects.isNull(loginUser)) {
            throw new BadCredentialsException("用户不存在，请注册");
        }
        if (!STATUS_NORMAL.equals(loginUser.getUser().getState())) {
            throw new BadCredentialsException("账号已被锁定");
        }
        //登录成功后，验证码失效
        redisCache.deleteObject(key);
        EmailCodeAuthenticationToken authenticationResult = new EmailCodeAuthenticationToken(loginUser, e_code, type, loginUser.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }


    //断是上面 authenticate 方法的 authentication 参数，是哪种类型
    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}