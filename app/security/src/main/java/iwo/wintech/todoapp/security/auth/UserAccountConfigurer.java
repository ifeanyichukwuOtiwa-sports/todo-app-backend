package iwo.wintech.todoapp.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@RequiredArgsConstructor
public class UserAccountConfigurer extends AbstractHttpConfigurer<UserAccountConfigurer, HttpSecurity> {
    private final UserAuthenticationProvider provider;

    @Override
    public void init(final HttpSecurity builder) {
        builder.authenticationProvider(
                provider
        );
    }

    @Override
    public void configure(final HttpSecurity builder) {
        final AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);

        builder.addFilterBefore(
                new UserAuthenticationFilter(manager),
                AuthorizationFilter.class
        );

    }
}
