package iwo.wintech.todoapp.security.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
public class UserAuthenticationFilter extends AuthenticationFilter {
    private static final String HEADER_NAME = "x-user-auth";
    private static final AuthenticationConverter AUTHENTICATION_CONVERTER = request -> {
        if (Collections.list(request.getHeaderNames()).contains(HEADER_NAME)) {
            return UserAuthentication.unAuthentication(request.getHeader(HEADER_NAME));
        }
        return null;
    };
    private static final AuthenticationFailureHandler FAILURE_HANDLER = (req, resp, ex) -> {
        log.info("Failed to authenticate");
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().write(ex.getMessage());
        resp.setContentType("text/plain;charset=utf8");
    };
    private static final AuthenticationSuccessHandler SUCCESS_HANDLER = (req, resp, ex) -> {
        log.info("Successfully authenticated");
        resp.setStatus(HttpServletResponse.SC_OK);
    };

    public UserAuthenticationFilter(final AuthenticationManager manager) {
        super(manager, AUTHENTICATION_CONVERTER);
        setFailureHandler(FAILURE_HANDLER);
        setSuccessHandler(SUCCESS_HANDLER);
    }
}
