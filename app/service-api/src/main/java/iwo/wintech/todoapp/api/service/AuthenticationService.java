package iwo.wintech.todoapp.api.service;

import iwo.wintech.todoapp.api.dto.LoginResponse;
import iwo.wintech.todoapp.persistence.dto.AuthenticateResponse;

public interface AuthenticationService {
    LoginResponse login(final String username, final String password);
    AuthenticateResponse authenticate(final String token);
}
