package team.keephealth.common.handler.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.utils.WebUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = null;
        if (e instanceof BadCredentialsException) {
            //BadCredentialsException用户名密码错误
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            //InsufficientAuthenticationException请求未携带token
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }

        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
