package team.keephealth.xyj.modules.login.mail.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.keephealth.common.utils.BeanCopyUtils;
import team.keephealth.common.utils.JwtUtil;
import team.keephealth.common.utils.RedisCache;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.entity.LoginUser;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserInfoVo;
import team.keephealth.xyj.modules.keephealth.domain.vo.UserLoginVo;
import team.keephealth.xyj.modules.keephealth.mapper.RoleMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static team.keephealth.common.constants.SystemConstants.ROLE_MAP;

@Slf4j
@Component
public class MailAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        //把token和userInfo封装，返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copeBean(loginUser.getUser(), UserInfoVo.class);
        userInfoVo.setTypeName(roleMapper.selectById(ROLE_MAP.get(loginUser.getUser().getType())).getName());
        UserLoginVo vo = new UserLoginVo(jwt, userInfoVo);
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(ResponseResult.okResult(vo)));
        out.flush();
        out.close();
    }
}
