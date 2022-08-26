package team.keephealth.xyj.modules.login.mail;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 身份令牌
 */
public class EmailCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object email;

    private Object e_code;

    private Object type;

    public EmailCodeAuthenticationToken(Object email, Object e_code, Object type, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.e_code = e_code;
        this.type = type;
        super.setAuthenticated(true);
    }

    public EmailCodeAuthenticationToken(String email, String e_code, String type) {
        super(null);
        this.email = email;
        this.e_code = e_code;
        this.type = type;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return e_code;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    public Object getType() {
        return type;
    }
}
