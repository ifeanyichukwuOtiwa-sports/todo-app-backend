package iwo.wintech.todoapp.security.config;

import iwo.wintech.todoapp.api.service.AuthenticationService;
import iwo.wintech.todoapp.security.auth.UserAccountConfigurer;
import iwo.wintech.todoapp.security.auth.UserAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TodoSecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http,
                                            final UserAccountConfigurer userAccountConfigurer,
                                            final UserAuthenticationProvider userAuthenticationProvider) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                r -> {
                    r.requestMatchers("/api/auth/register", "/api/auth/login").permitAll();
                    r.anyRequest().authenticated();
                }
        )
                .with(userAccountConfigurer, Customizer.withDefaults())
                .authenticationProvider(userAuthenticationProvider)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserAccountConfigurer userAccountConfigurer(final UserAuthenticationProvider userAuthenticationProvider) {
        return new UserAccountConfigurer(userAuthenticationProvider);
    }

    @Bean
    public UserAuthenticationProvider userAuthenticationProvider(final AuthenticationService authService) {
        return new UserAuthenticationProvider(authService);
    }
}
