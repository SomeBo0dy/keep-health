package team.keephealth.xyj.modules.login.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import team.keephealth.xyj.modules.login.mail.handler.MailAuthFailureHandler;
import team.keephealth.xyj.modules.login.mail.handler.MailAuthSuccessHandler;

@Component
public class MailAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private MailAuthSuccessHandler mailAuthSuccessHandler;
    @Autowired
    private MailAuthFailureHandler mailAuthFailureHandler;
    @Autowired
    private EmailCodeAuthenticationProvider emailCodeAuthenticationProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        EmailCodeAuthenticationFilter emailCodeAuthenticationFilter = new EmailCodeAuthenticationFilter();
        emailCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        emailCodeAuthenticationFilter.setAuthenticationSuccessHandler(mailAuthSuccessHandler);
        emailCodeAuthenticationFilter.setAuthenticationFailureHandler(mailAuthFailureHandler);

        builder.authenticationProvider(emailCodeAuthenticationProvider);
        builder.addFilterAfter(emailCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
