package iwo.wintech.todoapp.rest.controller;

import iwo.wintech.todoapp.api.dto.CreateUserRequest;
import iwo.wintech.todoapp.api.dto.LoginRequest;
import iwo.wintech.todoapp.api.dto.LoginResponse;
import iwo.wintech.todoapp.api.service.AuthenticationService;
import iwo.wintech.todoapp.api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/auth")
class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public boolean registerUser(@RequestBody final CreateUserRequest request) {
        return userService.createUser(request);
    }


    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody final LoginRequest request) {
        return authenticationService.login(request.username(), request.password());
    }
}
