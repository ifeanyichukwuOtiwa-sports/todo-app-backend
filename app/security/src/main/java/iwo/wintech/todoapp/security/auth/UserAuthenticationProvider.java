package iwo.wintech.todoapp.security.auth;

import iwo.wintech.todoapp.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationService authService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UserAuthentication authRequest = (UserAuthentication) authentication;
        final String token = authRequest.getToken();
        return UserAuthentication.authenticated(authService.authenticate(token));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UserAuthentication.class.isAssignableFrom(authentication);
    }
}
