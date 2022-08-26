package team.keephealth.xyj.modules.keephealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.keephealth.common.enums.AppHttpCodeEnum;
import team.keephealth.common.exception.SystemException;
import team.keephealth.common.utils.MailUtils;
import team.keephealth.common.utils.RandomUtils;
import team.keephealth.common.utils.RedisCache;
import team.keephealth.common.utils.SecurityUtils;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.BindEmailDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.EmailLoginDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.LoginUser;
import team.keephealth.xyj.modules.keephealth.domain.entity.User;
import team.keephealth.xyj.modules.keephealth.service.MailService;
import team.keephealth.xyj.modules.keephealth.service.UserService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static team.keephealth.common.constants.BusinessConstants.CODE_SUBJECT;
import static team.keephealth.common.constants.BusinessConstants.CODE_TIMEOUT;
import static team.keephealth.common.constants.SystemConstants.EMAIL_NOT_BIND;
import static team.keephealth.common.constants.SystemConstants.TYPE_MAP;

@Data
@Slf4j
@Service
@ConfigurationProperties(prefix = "xyj")
public class MailServiceImpl implements MailService {

    private String mailFrom;
    @Autowired
    MailSender mailSender;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult sendEmailCode(String email) {
        String code = redisCache.getCacheObject(email);
        //从redis中获取验证码,若已存在则删去该验证码
        if (!StringUtils.hasText(code)) {
            redisCache.deleteObject(email);
        }
        code = RandomUtils.getSixBitRandom();
        String mess = MailUtils.initCodeMessage(code);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        System.out.println(mailFrom);
        MailUtils.setMessage(mailFrom, email, CODE_SUBJECT, mess, simpleMailMessage);
        mailSender.send(simpleMailMessage);
        log.info("邮箱验证码已发送");
        redisCache.setCacheObject(email, code, CODE_TIMEOUT, TimeUnit.SECONDS);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult bindEmail(BindEmailDto bindEmailDto) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        Long id = user.getId();
        if (!checkUserEmail(id)) {
            throw new SystemException(AppHttpCodeEnum.USER_EMAIL_BINDED);
        }
        //检查验证码
        checkCode(bindEmailDto);
        if (!checkBind(user, bindEmailDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_BINDED);
        }
        user.setEmail(bindEmailDto.getEmail());
        userService.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult sendLoginEmailCode(EmailLoginDto emailLoginDto) {
        String email = emailLoginDto.getEmail();
        String type = emailLoginDto.getType();
        if (!StringUtils.hasText(email)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(type)){
            throw new SystemException(AppHttpCodeEnum.TYPE_NOT_NULL);
        }
        if (!TYPE_MAP.keySet().contains(type)){
            throw new SystemException(AppHttpCodeEnum.TYPE_ERROR);
        }
        String key = email + "type:" + type;
        String code = redisCache.getCacheObject(key);
        //从redis中获取验证码,若已存在则删去该验证码
        if (!StringUtils.hasText(code)) {
            redisCache.deleteObject(key);
        }
        code = RandomUtils.getSixBitRandom();
        String mess = MailUtils.initCodeMessage(code);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        System.out.println(mailFrom);
        MailUtils.setMessage(mailFrom, email, CODE_SUBJECT, mess, simpleMailMessage);
        mailSender.send(simpleMailMessage);
        log.info("邮箱验证码已发送");
        redisCache.setCacheObject(key, code, CODE_TIMEOUT, TimeUnit.SECONDS);
        return ResponseResult.okResult();
    }

    private boolean checkUserEmail(Long id) {
        User user = userService.getById(id);
        return EMAIL_NOT_BIND.equals(user.getEmail());
    }

    private void checkCode(BindEmailDto bindEmailDto) {
        String email = bindEmailDto.getEmail();
        String e_code = bindEmailDto.getE_code();
        //验证邮箱验证码
        String curCode = redisCache.getCacheObject(email);
        if (!StringUtils.hasText(curCode)) {
            throw new BadCredentialsException("验证码已过期，请重新发送验证码");
        }
        if (!curCode.equals(e_code)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

    private boolean checkBind(User user, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.eq(User::getType, user.getType());
        User one = userService.getOne(queryWrapper);
        if (Objects.isNull(one)) {
            return true;
        }
        return false;
    }

}
