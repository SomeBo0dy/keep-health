package team.keephealth.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;

import static team.keephealth.common.constants.BusinessConstants.CODE_TIMEOUT;


public class MailUtils {
    private String myEmail;

    public static void setMessage(String mailFrom, String mailTo, String subject, String message, SimpleMailMessage mailMessage) {
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
    }


    public static String initCodeMessage(String code) {
        return "验证码： " + code + " ，有效时间为 " + CODE_TIMEOUT / 60 + " 分钟。";
    }
}
