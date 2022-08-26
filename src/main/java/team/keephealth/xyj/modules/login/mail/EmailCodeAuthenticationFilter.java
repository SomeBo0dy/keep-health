package team.keephealth.xyj.modules.login.mail;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import team.keephealth.common.utils.RedisCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EmailCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String DEFAULT_EMAIL_NAME = "email";
    private final String DEFAULT_EMAIL_CODE = "e_code";
    private final String TYPE = "type";

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    //Filter过滤的url
    public EmailCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/email", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("请求方式有误: " + request.getMethod());
        }
        //如果请求的参数格式不是json，直接异常
        if (!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("参数不是json：" + request.getMethod());
        }
        String email = "";
        String e_code = "";
        String type = "";
        try {
            Map<String, String> map = JSONObject.parseObject(request.getInputStream(), Map.class);
            email = map.get(DEFAULT_EMAIL_NAME);
            e_code = map.get(DEFAULT_EMAIL_CODE);
            type = map.get(TYPE);
        } catch (IOException e) {
            throw new AuthenticationServiceException("参数不正确：" + request.getMethod());
        }
        if (email == null) {
            throw new BadCredentialsException("邮箱参数缺失");
        }
        if (e_code == null) {
            throw new BadCredentialsException("邮箱验证码参数缺失");
        }
        if (type == null) {
            throw new BadCredentialsException("角色类型参数缺失");
        }
        email = email.trim();
        EmailCodeAuthenticationToken authenticationToken = new EmailCodeAuthenticationToken(email, e_code, type);
        setDetails(request, authenticationToken);
        //交给 AuthenticationManager 进行认证
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    public void setDetails(HttpServletRequest request, EmailCodeAuthenticationToken token) {
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}