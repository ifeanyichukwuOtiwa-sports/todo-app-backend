package iwo.wintech.todoapp.api.service;

import iwo.wintech.todoapp.api.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(final String username, final String password);
}
