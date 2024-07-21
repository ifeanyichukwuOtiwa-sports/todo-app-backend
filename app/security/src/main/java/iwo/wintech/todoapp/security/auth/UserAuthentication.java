package iwo.wintech.todoapp.security.auth;

import iwo.wintech.todoapp.persistence.dto.AuthenticateResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserAuthentication extends AbstractAuthenticationToken {


    private final String token;
    private final AuthenticateResponse principal;

    public UserAuthentication(final AuthenticateResponse principal) {
        super(getUserRoles(principal));
        this.token = null;
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public UserAuthentication(final String token) {
        super(AuthorityUtils.createAuthorityList("ROLE_user"));
        this.token = token;
        this.principal = AuthenticateResponse.unAuthenticated();
        super.setAuthenticated(false);
    }

    private static Collection<? extends GrantedAuthority> getUserRoles(final AuthenticateResponse principal) {
        final List<String> roles = principal.roles()
                .stream()
                .map(role -> "ROLE_" + role)
                .toList();
        return AuthorityUtils.createAuthorityList(
                roles.isEmpty()
                        ? List.of("ROLE_user")
                        : roles
        );
    }

    public static Authentication unAuthentication(final String token) {
        return new UserAuthentication(token);
    }

    public static Authentication authenticated(final AuthenticateResponse authResponse) {
        return new UserAuthentication(authResponse);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal.user();
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        throw new UnsupportedOperationException("Dont change the Auth Status \uD83D\uDD12");
    }
}
