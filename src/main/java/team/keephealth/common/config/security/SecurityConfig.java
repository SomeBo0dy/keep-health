package team.keephealth.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.keephealth.xyj.modules.login.mail.MailAuthenticationSecurityConfig;
import team.keephealth.common.filter.JwtAuthenticationTokenFilter;
import team.keephealth.xyj.modules.login.password.PasswordAuthenticationSecurityConfig;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String[] ANON = {
            "/login/password",
            "/login/email",
            "/register",
            "/email/send/code/login"
    };
    private String[] AUTH = {
            "/logout",
            "/email/bind",
            "/send/code/{email}",
            "/upload/**",
            "/users/**",
            "/follow/**",
            "/shows/**",
            "/profiles/**",
            "/comment/show/**",
            "/tip/**",
            "/ai/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Lazy
    @Autowired
    private MailAuthenticationSecurityConfig mailAuthenticationSecurityConfig;
    @Lazy
    @Autowired
    private PasswordAuthenticationSecurityConfig passwordAuthenticationSecurityConfig;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/v2/api-docs-ext/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/doc.html");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //使用自定义的邮箱登录过滤器
                .apply(mailAuthenticationSecurityConfig).and()
                //使用自定义的账号密码过滤器
                .apply(passwordAuthenticationSecurityConfig).and()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                //允许匿名访问
                .antMatchers(ANON).anonymous()
                //需要权限认证
                .antMatchers(AUTH).authenticated()

                .antMatchers("/sys/**").hasRole("admin")
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

        http.logout().disable();

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //添加到过滤器链
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //允许跨域
        http.cors();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
